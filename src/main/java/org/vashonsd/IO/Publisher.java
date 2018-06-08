package org.vashonsd.IO;

import org.vashonsd.Message;
import org.vashonsd.IO.Services.Writer;

/**
 * A Publisher needs an Input (such as console) and a Writer to an external service (e.g., pubsub).
 *
 * The Input is run in its own thread and the Writer encapsulates its own thread.
 *
 * The Input is polled for new input, which is sent via the Writer to the external service.
 */
public class Publisher implements Runnable {

    private volatile boolean running;
    Writer writer;
    Input input;

    public Publisher(Writer s) {
        writer = s;
    }

    public void close() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        Message msg;
        while (running) {
            if ((msg = input.read()) != null) {
                writer.write(msg.getUuid(), msg);
            }
        }
    }

    public static Builder fromBuilder() {
        return new Builder();
    }


    public static final class Builder {
        Writer service;
        Input input;

        public Builder() {
        }

        public static Builder aRequestProducer() {
            return new Builder();
        }

        public Builder withWriter(Writer service) {
            this.service = service;
            return this;
        }

        public Builder withInput(Input r) {
            this.input = r;
            return this;
        }

        public Publisher build() {
            Publisher publisher = new Publisher(null);
            publisher.writer = this.service;
            publisher.input = this.input;
            return publisher;
        }
    }
}
