package no.ssb.rawdata.converter.app.kag;

import no.ssb.dapla.dataset.api.Type;
import no.ssb.rawdata.api.RawdataConsumer;
import no.ssb.rawdata.converter.core.RawdataConverter;
import no.ssb.rawdata.converter.core.RawdataConverterConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * The rawdata used in this test can be generated by using the {@link RawdataPublisherTest}
 */
public class KarakterConverterJobITest extends ConverterJobIntegrationTestBase {

    @Test
    @Disabled
    public void testConverterJobExecutor() {
        RawdataConverterConfig rawdataConverterConfig = new RawdataConverterConfig();
        rawdataConverterConfig.setActiveByDefault(true);
        rawdataConverterConfig.setDryrun(false);
        rawdataConverterConfig.setDebug(true);
        rawdataConverterConfig.setTopic("kag-karakter-test");
        rawdataConverterConfig.setStorageType("local");
        rawdataConverterConfig.setStorageRoot(uriStringOf("../localenv/datastore"));
        rawdataConverterConfig.setStoragePath("/kilde/kag/karakter/raadata/test");
        rawdataConverterConfig.setStorageVersion(1598553650000L);
        rawdataConverterConfig.setInitialPosition("FIRST");
        rawdataConverterConfig.setWindowMaxRecords(1000);
        rawdataConverterConfig.setSchemaMetricsEnabled(false);
        rawdataConverterConfig.setDatasetType(Type.BOUNDED);

        KagRawdataConverterConfig kagRawdataConverterConfig = new KagRawdataConverterConfig();
        kagRawdataConverterConfig.setSchemaFileKarakter("schema/karakter.avsc");
        kagRawdataConverterConfig.getCsvSettings().put("delimiters", "|");
        RawdataConverter converter = new KagRawdataConverter(kagRawdataConverterConfig);

        RawdataConsumer rawdataConsumer = rawdataConsumerFactory.rawdataConsumer(rawdataConverterConfig);
        jobScheduler.start(rawdataConverterConfig, converter, rawdataConsumer);
    }

}