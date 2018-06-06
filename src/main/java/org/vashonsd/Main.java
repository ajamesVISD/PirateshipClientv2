package org.vashonsd;

import org.vashonsd.IO.ConsoleInput;
import org.vashonsd.IO.ConsoleOutput;
import org.vashonsd.Services.GooglePubSubReader;
import org.vashonsd.Services.GooglePubSubWriter;
import org.vashonsd.Services.Reader;
import org.vashonsd.Services.Writer;

import java.io.IOException;
import java.util.Properties;

public class Main {

    private static Properties properties;

    public static void main(String[] args) {
        properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("client.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //And now the portion of the code that is bound to Google PubSub.
        //We can more fully encapsulate this later
        Reader reader = GooglePubSubReader.fromBuilder()
                .withRole(properties.getProperty("response-role"))
                .withProjectId(properties.getProperty("response-project"))
                .withSubscription(properties.getProperty("username"))
                .build();
        Writer writer = GooglePubSubWriter.fromBuilder()
                .withProject(properties.getProperty("requests-project"))
                .withRole(properties.getProperty("requests-role"))
                .withTopic(properties.getProperty("requests-topic"))
                .build();

        PirateshipClient client = PirateshipClient.fromBuilder()
                .withInput(new ConsoleInput())
                .withOutput(new ConsoleOutput())
                .withReader(reader)
                .withWriter(writer)
                .withProperties(properties)
                .build();
        client.run();
    }
}
