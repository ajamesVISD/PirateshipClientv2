package org.vashonsd;

import org.vashonsd.IO.ConsoleInput;
import org.vashonsd.IO.ConsoleOutput;
import org.vashonsd.IO.Services.GooglePubSubReader;
import org.vashonsd.IO.Services.GooglePubSubWriter;
import org.vashonsd.IO.Services.Reader;
import org.vashonsd.IO.Services.Writer;

import java.io.IOException;
import java.util.Properties;


public class Main {

    private static Client client;

    public static void main(String[] args) {
        client = Client.fromBuilder()
                .withInput(new ConsoleInput())
                .withOutput(new ConsoleOutput())
                .withGooglePubSub()
                .build();
        client.run();
    }
}
