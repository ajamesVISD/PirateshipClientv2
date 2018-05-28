package org.vashonsd;

import org.vashonsd.Input.ConsoleReader;
import org.vashonsd.Output.ConsoleWriter;
import org.vashonsd.Request.RequestProducer;
import org.vashonsd.Response.ResponseConsumer;
import org.vashonsd.Services.DummyPubsub;

public class Main {

    public static void main(String[] args) {
        PirateshipClient client = PirateshipClient.fromBuilder()
                .withInputSource(new ConsoleReader())
                .withOutputDestination(new ConsoleWriter())
                .withRequestWriter(new RequestProducer())
                .withResponseConsumer(new ResponseConsumer())
                .withUsername("james.elliott")
                .build();
        client.setPubsubService(new DummyPubsub());
        client.run();
    }
}
