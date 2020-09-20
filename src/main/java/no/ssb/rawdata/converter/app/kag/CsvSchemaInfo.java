package no.ssb.rawdata.converter.app.kag;

import lombok.Builder;
import lombok.Data;
import org.apache.avro.Schema;

import java.util.List;

/**
 * Wraps information about a Avro Schema specific for holding a set of lines from a csv file
 *
 * The .avsc file is expected to be similar to:
 * <pre>
   {
    "type": "record",
    "name": "root",
    "fields": [
      {
        "name": "<collection field name>",
        "type": {
          "type": "array",
          "items": {
            "type": "record",
            "name": "<item field name>",
            "fields": [
              {
                "name": "col1",
                "type": [ "null", "string" ]
              },
              , (...)
            ]
          }
        }
      }
    ]
  }
  </pre>
 *
 */
@Data
@Builder
class CsvSchemaInfo {
    private String targetFieldname;
    private Schema targetSchema;
    private String collectionFieldname;
    private String itemFieldname;

    public Schema getItemSchema() {
        return targetSchema.getField(collectionFieldname).schema().getElementType();
    }

    // TODO: Calculate this from schema
    public List<String> getHeaders() {
        return List.of(
          "FilID",
          "RadID",
          "RadNr",
          "Fødselsnummer",
          "Skoleår",
          "Skolenummer",
          "Programområdekode",
          "Fagkode",
          "Fagstatus",
          "KarakterHalvårsvurdering1",
          "KarakterHalvårsvurdering2",
          "KarakterStandpunkt",
          "KarakterSkriftligEksamen",
          "KarakterMuntligEksamen",
          "KarakterAnnen",
          "Skoleår2",
          "Skolenummer2",
          "ErLinjaAktiv",
          "Elevtimer",
          "ForrigeFagstatus",
          "FagmerknadKode",
          "Karakterstatus"
        );
    }
}