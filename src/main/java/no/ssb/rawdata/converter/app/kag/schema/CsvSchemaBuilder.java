package no.ssb.rawdata.converter.app.kag.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CsvSchemaBuilder {

    private String targetFieldname = "data";
    private String collectionFieldname = "elements";
    private String itemFieldname = "item";
    private List<FieldInfo> fields = new ArrayList<>();

    public CsvSchemaBuilder fields(Collection<FieldInfo> fields) {
        this.fields.addAll(fields);
        return this;
    }

    public CsvSchemaBuilder fields(List<String> fieldNames) {
        return fields(fieldNames.stream()
          .map(FieldInfo::optionalString)
          .collect(Collectors.toList()));
    }

    public CsvSchemaBuilder targetFieldname(String targetFieldname) {
        this.targetFieldname = targetFieldname;
        return this;
    }

    public CsvSchemaBuilder collectionFieldname(String collectionFieldname) {
        this.collectionFieldname = collectionFieldname;
        return this;
    }

    public CsvSchemaBuilder itemFieldname(String itemFieldname) {
        this.itemFieldname = itemFieldname;
        return this;
    }

    public CsvSchemaInfo build() {
        SchemaBuilder.FieldAssembler<Schema> fieldAssembler = SchemaBuilder.record(itemFieldname).fields();
        for (FieldInfo fieldInfo : fields) {
            String name = fieldInfo.getName();
            switch (fieldInfo.getDataType()) {
                case INT: fieldAssembler.optionalInt(name); break;
                case LONG: fieldAssembler.optionalLong(name); break;
                case BOOLEAN: fieldAssembler.optionalBoolean(name); break;
                default: fieldAssembler.optionalString(name);
            }
        }
        Schema itemsSchema = fieldAssembler.endRecord();

        Schema targetSchema = SchemaBuilder.record("root")
          .fields()
          .name(collectionFieldname).type().array().items(itemsSchema).noDefault()
          .endRecord();

        return new CsvSchemaInfo(targetFieldname, targetSchema, collectionFieldname, itemFieldname);
    }

}
