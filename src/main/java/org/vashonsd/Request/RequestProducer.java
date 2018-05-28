package org.vashonsd.Request;

import org.vashonsd.Message;
import org.vashonsd.Services.PubsubService;

import java.util.concurrent.BlockingQueue;

public class RequestProducer implements Producer {

    private BlockingQueue<Message> outboundMessages;
    private boolean running;
    PubsubService service;

    @Override
    public void observe(BlockingQueue<Message> outboundMessages) {
        this.outboundMessages = outboundMessages;
    }

    @Override
    public void close() {
        running = false;
    }

    @Override
    public void setPubsubService(PubsubService service) {
        this.service = service;
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                Message msg = outboundMessages.take();
                service.publish(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
