package org.vashonsd.IO;

import org.vashonsd.Message;
import org.vashonsd.IO.Services.Reader;

/**
 * Subscriber pulls messages from the remote Writer sends them to Output.
 *
 * A plausible architecture could use a queue as a go-between for asynchronous operation.
 * As written, it send directly.
 */
public class Subscriber implements Runnable {

    private String topic;
    private boolean running;
    private Reader reader;
    private String uuid;
    private Output output;

    public Subscriber(String uuid) {
        this.uuid = uuid;
    }

    public void setTopic(String str) {
        topic = str;
    }

    public void setReader(Reader r) {
        this.reader = r;
    }

    public void setOutput(Output w) {
        this.output = w;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        Message msg;
        while(running) {
            if((msg = reader.read()) != null) {
//                System.out.println("The Subscriber sees: " + msg.toString());
                output.write(msg);
            }
        }
    }

    public static Builder fromBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String topic;
        private boolean running;
        private Reader reader;
        private String uuid;
        private Output output;

        private Builder() {
        }

        public static Builder aResponseConsumer() {
            return new Builder();
        }

        public Builder withTopic(String topic) {
            this.topic = topic;
            return this;
        }
        public Builder withReader(Reader r) {
            this.reader = r;
            return this;
        }

        public Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder withOutput(Output w) {
            this.output = w;
            return this;
        }

        public Subscriber build() {
            Subscriber subscriber = new Subscriber(uuid);
            subscriber.setTopic(topic);
            subscriber.reader = this.reader;
            subscriber.output = this.output;
            return subscriber;
        }
    }
}
