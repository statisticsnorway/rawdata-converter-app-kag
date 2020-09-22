package no.ssb.rawdata.converter.app.kag;

import no.ssb.rawdata.converter.app.kag.schema.CsvSchemaBuilder;
import no.ssb.rawdata.converter.app.kag.schema.CsvSchemaInfo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CsvSchemaBuilderTest {

    @Test
    void shouldBuildCsvAvroSchema() {
        CsvSchemaInfo schemaInfo = new CsvSchemaBuilder()
          .collectionFieldname("karakterer")
          .itemFieldname("linje")
          .fields(Arrays.asList("FilID", "RadID", "RadNr"))
          .build();

        System.out.println(schemaInfo.getTargetSchema().toString());
    }

    @Test
    void sourceIsKarakter_shouldBuildCsvAvroSchema() {
        CsvSchemaInfo schemaInfo = Sources.of("karakter").getCsvSchemaInfo();
        System.out.println(schemaInfo.getHeaders());
        System.out.println(schemaInfo.getTargetSchema().toString());
    }

}