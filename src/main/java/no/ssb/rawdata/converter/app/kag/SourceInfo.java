package no.ssb.rawdata.converter.app.kag;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import no.ssb.rawdata.converter.app.kag.schema.CsvSchemaBuilder;
import no.ssb.rawdata.converter.app.kag.schema.CsvSchemaInfo;

@Builder
@Getter
public class SourceInfo {
    @NonNull
    private String sourceName;

    @NonNull
    private CsvSchemaInfo csvSchemaInfo;

    public static class SourceInfoBuilder {

        public SourceInfoBuilder csvSchemaInfo(CsvSchemaInfo csvSchemaInfo) {
            this.csvSchemaInfo = csvSchemaInfo;
            return this;
        }

        public SourceInfoBuilder csvSchemaInfo(CsvSchemaBuilder csvSchemaInfoBuilder) {
            return this.csvSchemaInfo(csvSchemaInfoBuilder.build());
        }

    }

}
