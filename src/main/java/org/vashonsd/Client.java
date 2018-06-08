package org.vashonsd;

import org.vashonsd.IO.Input;
import org.vashonsd.IO.Output;
import org.vashonsd.IO.Publisher;
import org.vashonsd.IO.Services.GooglePubSubReader;
import org.vashonsd.IO.Services.GooglePubSubWriter;
import org.vashonsd.IO.Subscriber;
import org.vashonsd.IO.Services.Reader;
import org.vashonsd.IO.Services.Writer;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * The Client has a Publisher, which pulls Strings from an external Input (such as the console) and pushes Messages to an external service.
 * It also has a Subscriber, which pulls Messages from an external service and pushes them as strings to a external Output..
 *
 * The Client runs the Publisher and Subscriber, each in its own thread.
 *
 * As a Runnable, the Client could be run in its own thread, or it can just be told to run(), at which point it blocks.
 *
 * If the Client runs in its own thread, it could be shut down by calling client.stop().
 */
public class Client implements Runnable {

    private final Input input;
    private Publisher publisher;
    private Subscriber subscriber;
    private Output output;

    private Properties properties;

    private String uuid;
    private volatile boolean running;

    /**
      * The Client's Input is the user's point of entry, such as a console or Java graphic interface.
     *  The Writer is the external service to which the client's Message gets sent. Think of Google PubSub, Firebase, or a simple REST request, etc.
     *  The Reader pulls from an external service, blocking in its thread as it watches. This allows for asynchronous delivery of messages.
     *  The Client's Output is the result being sent to the user, such as the console, SMS, Chrome extensions, whatever.
      *
     *  The Client's constructor will build the Subscriber and Publisher from these elements.
      */
    public Client(Input input,
                  Output output,
                  Writer writer,
                  Reader reader,
                  Properties properties) {
        this.properties = properties;

        this.output = output;
        this.input = input;
        this.input.setUuid(properties.getProperty("username"));

        this.subscriber = Subscriber.fromBuilder()
            .withReader(reader)
            .withOutput(output)
            .withUuid(properties.getProperty("username"))
            .build();
        this.publisher = Publisher.fromBuilder()
                .withInput(input)
                .withWriter(writer)
                .build();
    }

    @Override
    public void run() {
        running = true;
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.execute(publisher);
        pool.execute(subscriber);

        while(running) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        subscriber.setTopic(uuid);
    }

    public static Builder fromBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Input input;
        private Output output;
        private String uuid;
        private Writer writer;
        private Reader reader;
        private Properties properties;

        public Builder() {
            try {
                properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("client.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Builder withInput(Input r) {
            input = r;
            return this;
        }

        public Builder withOutput(Output w) {
            output = w;
            return this;
        }

        public Builder withWriter(Writer w) {
            this.writer = w;
            return this;
        }

        public Builder withReader(Reader r) {
            this.reader = r;
            return this;
        }

        public Builder withGooglePubSub() {
            reader = GooglePubSubReader.fromBuilder()
                    .withRole(properties.getProperty("response-role"))
                    .withProjectId(properties.getProperty("response-project"))
                    .withSubscription(properties.getProperty("username"))
                    .build();
            writer = GooglePubSubWriter.fromBuilder()
                    .withProject(properties.getProperty("requests-project"))
                    .withRole(properties.getProperty("requests-role"))
                    .withTopic(properties.getProperty("requests-topic"))
                    .build();
            return this;
        }

        public Builder withUuid(String str) {
            uuid = str;
            return this;
        }

        public Builder withRandomUsername() {
            uuid = UUID.randomUUID().toString();
            return this;
        }

        public Builder withProperties(Properties p) {
            this.properties = p;
            return this;
        }

        public Client build() {
            Client pc = new Client(input, output, writer, reader, properties);
            return pc;
        }
    }
}
