package no.ssb.rawdata.converter.app.kag;

import lombok.extern.slf4j.Slf4j;
import no.ssb.avro.convert.csv.CsvParserSettings;
import no.ssb.avro.convert.csv.CsvToRecords;
import no.ssb.avro.convert.csv.InconsistentCsvDataException;
import no.ssb.avro.convert.json.Json;
import no.ssb.rawdata.api.RawdataMessage;
import no.ssb.rawdata.converter.app.kag.schema.CsvSchemaInfo;
import no.ssb.rawdata.converter.core.AbstractRawdataConverter;
import no.ssb.rawdata.converter.core.ConversionResult;
import no.ssb.rawdata.converter.core.ValueInterceptorChain;
import no.ssb.rawdata.converter.core.schema.AggregateSchemaBuilder;
import no.ssb.rawdata.converter.core.util.RawdataMessageFacade;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static no.ssb.rawdata.converter.core.util.RawdataMessageUtil.posAndIdOf;

@Slf4j
public class KagRawdataConverter extends AbstractRawdataConverter {

    private final Schema metadataSchema;
    private final KagRawdataConverterConfig converterConfig;
    private final ValueInterceptorChain valueInterceptorChain;
    private final SourceInfo source;

    private static final String RAWDATA_ITEMNAME_ENTRY = "entry";
    private static final String FIELDNAME_METADATA = "metadata";

    public KagRawdataConverter(KagRawdataConverterConfig converterConfig) {
        this(converterConfig, new ValueInterceptorChain());
    }

    public KagRawdataConverter(KagRawdataConverterConfig converterConfig, ValueInterceptorChain valueInterceptorChain) {
        this.converterConfig = converterConfig;
        this.metadataSchema = readAvroSchema("schema/message-metadata.avsc");
        this.valueInterceptorChain = valueInterceptorChain;
        this.source = Sources.of(converterConfig.getSource());

        if (! converterConfig.getCsvSettings().isEmpty()) {
            log.info("Overridden CSV parser settings:\n{}", Json.prettyFrom(converterConfig.getCsvSettings()));
        }
    }

    @Override
    public Schema targetAvroSchema() {
        CsvSchemaInfo schemaInfo = source.getCsvSchemaInfo();
        AggregateSchemaBuilder builder = new AggregateSchemaBuilder("no.ssb.dapla.kilde.kag.rawdata")
          .schema(FIELDNAME_METADATA, metadataSchema)
          .schema(schemaInfo.getTargetFieldname(), schemaInfo.getTargetSchema());

        return builder.build();
    }

    @Override
    public boolean isConvertible(RawdataMessage rawdataMessage) {
        if (! rawdataMessage.keys().contains("entry")) {
            log.warn("Encountered rawdata message without KAG data (no 'entry' item). " +
              "Items in message: " + rawdataMessage.keys() + ". Skipping message " + posAndIdOf(rawdataMessage));
            return false;
        }

        return true;
    }

    @Override
    public ConversionResult convert(RawdataMessage rawdataMessage) {
        ConversionResult.ConversionResultBuilder resultBuilder = ConversionResult.builder(new GenericRecordBuilder(targetAvroSchema()));
        RawdataMessageFacade.print(rawdataMessage);
        // Add metadata about the message
        addMetadata(rawdataMessage, resultBuilder);

        // Convert the payload
        CsvSchemaInfo schemaInfo = source.getCsvSchemaInfo();
        convertCsvData(rawdataMessage, schemaInfo, resultBuilder);

        System.out.println(resultBuilder.build().getGenericRecord().toString());
        return resultBuilder.build();
    }

    void addMetadata(RawdataMessage rawdataMessage, ConversionResult.ConversionResultBuilder resultBuilder) {
        GenericRecordBuilder builder = new GenericRecordBuilder(metadataSchema);
        builder.set("ulid", rawdataMessage.ulid().toString());
        builder.set("dcPosition", rawdataMessage.position());
        builder.set("dcTimestamp", rawdataMessage.timestamp());
        resultBuilder.withRecord(FIELDNAME_METADATA, builder.build());
    }

    void convertCsvData(RawdataMessage rawdataMessage, CsvSchemaInfo csvSchema, ConversionResult.ConversionResultBuilder resultBuilder) {
        byte[] data = rawdataMessage.get(RAWDATA_ITEMNAME_ENTRY);
        CsvParserSettings csvParserSettings = new CsvParserSettings().configure(converterConfig.getCsvSettings());
        csvParserSettings.headers(csvSchema.getHeaders());

        try (CsvToRecords records = new CsvToRecords(new ByteArrayInputStream(data), csvSchema.getItemSchema(), csvParserSettings)
          .withValueInterceptor(valueInterceptorChain::intercept) ) {
            List<GenericRecord> items = new ArrayList<>();
            records.forEach(items::add);
            resultBuilder.appendCounter("totalLinesCount", items.size());
            GenericRecord itemRecord = new GenericRecordBuilder(csvSchema.getTargetSchema()).set(csvSchema.getCollectionFieldname(), items).build();
            resultBuilder.withRecord(csvSchema.getTargetFieldname(), itemRecord);
        }
        catch (InconsistentCsvDataException e) {
            log.warn("Encountered inconsistent csv data at " + posAndIdOf(rawdataMessage) + ". The data could not be converted.", e);
            resultBuilder.addFailure(e);
        }
        catch (Exception e) {
            throw new KagRawdataConverterException("Error converting KAG data at " + posAndIdOf(rawdataMessage), e);
        }
    }

    static class KagRawdataConverterException extends RuntimeException {
        KagRawdataConverterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
