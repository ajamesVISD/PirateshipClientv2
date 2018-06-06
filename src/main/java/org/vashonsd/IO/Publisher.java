package org.vashonsd.IO;

import org.vashonsd.Message;
import org.vashonsd.Services.Writer;

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
        Thread readerThread = new Thread(input);
        readerThread.start();
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
