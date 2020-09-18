package no.ssb.rawdata.converter.app.kag;

import lombok.extern.slf4j.Slf4j;
import no.ssb.avro.convert.json.Json;
import no.ssb.rawdata.api.RawdataMessage;
import no.ssb.rawdata.converter.core.AbstractRawdataConverter;
import no.ssb.rawdata.converter.core.ConversionResult;
import no.ssb.rawdata.converter.core.ValueInterceptorChain;
import no.ssb.rawdata.converter.core.schema.AggregateSchemaBuilder;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecordBuilder;

@Slf4j
public class KagRawdataConverter extends AbstractRawdataConverter {

    private final Schema aggregateSchema;
    private final Schema metadataSchema;
    private final Schema karakterSchema;
    private final KagRawdataConverterConfig converterConfig;
    private final ValueInterceptorChain valueInterceptorChain;

    private static final String RAWDATA_ITEMNAME_KARAKTERER = "entry";
    private static final String FIELDNAME_METADATA = "metadata";
    private static final String FIELDNAME_KARAKTERER = "karakterer";

    public KagRawdataConverter(KagRawdataConverterConfig converterConfig) {
        this(converterConfig, new ValueInterceptorChain());
    }

    public KagRawdataConverter(KagRawdataConverterConfig converterConfig, ValueInterceptorChain valueInterceptorChain) {
        this.converterConfig = converterConfig;
        this.metadataSchema = readAvroSchema("schema/message-metadata.avsc");
        this.karakterSchema = readAvroSchema(converterConfig.getSchemaFileKarakter());
        aggregateSchema = new AggregateSchemaBuilder("no.ssb.dapla.kilde.kag.rawdata")
          .schema(FIELDNAME_METADATA, metadataSchema)
          .schema(FIELDNAME_KARAKTERER, karakterSchema)
          .build();
        this.valueInterceptorChain = valueInterceptorChain;

        if (! converterConfig.getCsvSettings().isEmpty()) {
            log.info("Overridden CSV parser settings:\n{}", Json.prettyFrom(converterConfig.getCsvSettings()));
        }
    }

    @Override
    public Schema targetAvroSchema() {
        return aggregateSchema;
    }

    @Override
    public boolean isConvertible(RawdataMessage rawdataMessage) {
        return true;
    }

    @Override
    public ConversionResult convert(RawdataMessage rawdataMessage) {
        ConversionResult.ConversionResultBuilder resultBuilder = ConversionResult.builder(new GenericRecordBuilder(aggregateSchema));

        // Add metadata about the message
        addMetadata(rawdataMessage, resultBuilder);

        convertCsvData(rawdataMessage, resultBuilder);

        return resultBuilder.build();
    }

    void addMetadata(RawdataMessage rawdataMessage, ConversionResult.ConversionResultBuilder resultBuilder) {
        GenericRecordBuilder builder = new GenericRecordBuilder(metadataSchema);
        builder.set("ulid", rawdataMessage.ulid().toString());
        builder.set("dcPosition", rawdataMessage.position());
        builder.set("dcTimestamp", rawdataMessage.timestamp());
        resultBuilder.withRecord(FIELDNAME_METADATA, builder.build());
    }

    void convertCsvData(RawdataMessage rawdataMessage, ConversionResult.ConversionResultBuilder resultBuilder) {
        byte[] data = rawdataMessage.get(RAWDATA_ITEMNAME_KARAKTERER);
    }

    static class KagRawdataConverterException extends RuntimeException {
        KagRawdataConverterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
