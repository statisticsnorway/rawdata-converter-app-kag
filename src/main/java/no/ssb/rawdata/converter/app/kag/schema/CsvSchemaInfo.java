package no.ssb.rawdata.converter.app.kag.schema;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.avro.Schema;

import java.util.List;
import java.util.stream.Collectors;

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
@RequiredArgsConstructor
public class CsvSchemaInfo {
    private final String targetFieldname;
    private final Schema targetSchema;
    private final String collectionFieldname;
    private final String itemFieldname;

    public Schema getItemSchema() {
        return targetSchema.getField(collectionFieldname).schema().getElementType();
    }

    public List<String> getHeaders() {
        return getItemSchema().getFields().stream()
          .map(f -> f.name())
          .collect(Collectors.toList());
    }
}