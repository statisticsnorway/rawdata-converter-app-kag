package no.ssb.rawdata.converter.app.kag;

import no.ssb.rawdata.converter.core.ConversionResult;
import no.ssb.rawdata.converter.test.message.RawdataMessageFixtures;
import no.ssb.rawdata.converter.test.message.RawdataMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class KagRawdataConverterTest {

    static RawdataMessageFixtures fixtures;

    @BeforeAll
    static void loadFixtures() {
        fixtures = RawdataMessageFixtures.init("kag-karakter-test");
    }

    @Disabled
    @Test
    void shouldConvertRawdataMessages() {
        RawdataMessages messages = fixtures.rawdataMessages("kag-karakter-test");
        KagRawdataConverterConfig config = new KagRawdataConverterConfig();
        config.setSource("karakter");
        KagRawdataConverter converter = new KagRawdataConverter(config);
        ConversionResult result = converter.convert(messages.index().get("position-3"));
        System.out.println(result.getGenericRecord().toString());
    }

}
