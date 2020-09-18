package no.ssb.rawdata.converter.app.kag;

import no.ssb.rawdata.converter.test.message.RawdataMessageFixtures;
import no.ssb.rawdata.converter.test.message.RawdataMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static no.ssb.rawdata.converter.test.RawdataPublisher.filesystemConfig;
import static no.ssb.rawdata.converter.test.RawdataPublisher.postgresConfig;
import static no.ssb.rawdata.converter.test.RawdataPublisher.publishRawdataMessages;

public class RawdataPublisherTest {

    static RawdataMessageFixtures fixtures;

    @BeforeAll
    static void loadFixtures() {
        fixtures = RawdataMessageFixtures.init("kag-karakter-test");
    }

    @Test
    @Disabled
    void publishRawdataMessagesToLocalPostgres() {
        String topic = "kag-karakter-test";
        RawdataMessages messages = fixtures.rawdataMessages(topic);
        publishRawdataMessages(messages, postgresConfig(topic));
    }

    @Test
    @Disabled
    void publishRawdataMessagesToLocalAvroFile() {
        String topic = "kag-karakter-test";
        RawdataMessages messages = fixtures.rawdataMessages(topic);
        publishRawdataMessages(messages, filesystemConfig(topic, "./rawdata-store")
          .cleanupBefore(true)
          .cleanupAfter(true)
        );
    }

}
