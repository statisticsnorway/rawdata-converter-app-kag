package no.ssb.rawdata.converter.app.kag;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.MapFormat;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("rawdata.converter.app.kag")
@Data
public class KagRawdataConverterConfig {

    /**
     * The source name such as "karakter", "resultat", ...
     */
    private String source;

    /**
     * Optional csv parser settings overrides.
     * E.g. allowing to explicitly specify the delimiter character
     */
    @MapFormat(transformation = MapFormat.MapTransformation.FLAT)
    private Map<String, Object> csvSettings = new HashMap<>();

}
