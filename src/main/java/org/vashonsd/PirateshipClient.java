package org.vashonsd;

import org.vashonsd.IO.Input;
import org.vashonsd.IO.Output;
import org.vashonsd.IO.Publisher;
import org.vashonsd.IO.Subscriber;
import org.vashonsd.Services.Reader;
import org.vashonsd.Services.Writer;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by andy on 5/24/18.
 */
public class PirateshipClient implements Runnable {

    private final Input input;
    private Publisher publisher;
    private Subscriber subscriber;
    private Output output;

    private Properties properties;

    private String uuid;
    private volatile boolean running;

    /**
      * A PirateshipClient handles communications with the PubSub server.
      * It also collects input from the user and returns the result to the user, for whatever I/O the user has chosen.
      *
      * The specifics of how this happens are up to the implementation.
      *
      */
    public PirateshipClient(Input input,
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

    //    runnable.terminate();
    //    thread.join();
    @Override
    public void run() {
        running = true;
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.execute(publisher);
        pool.execute(subscriber);

        while(running) {

        }
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        subscriber.setTopic(uuid);
    }

    public static PirateshipClient.Builder fromBuilder() {
        return new PirateshipClient.Builder();
    }

    public static final class Builder {
        private Input input;
        private Output output;
        private String uuid;
        private Writer writer;
        private Reader reader;
        private Properties properties;

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

        public PirateshipClient build() {
            PirateshipClient pc = new PirateshipClient(input, output, writer, reader, properties);
            return pc;
        }
    }
}
