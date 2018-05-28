package org.vashonsd.Response;

import org.vashonsd.Message;
import org.vashonsd.Services.DummyPubsub;
import org.vashonsd.Services.PubsubService;

import java.util.concurrent.BlockingQueue;

public class ResponseConsumer implements Consumer {

    private String topic;
    private BlockingQueue<Message> queue;
    private boolean running;
    private PubsubService service;

    public ResponseConsumer() {
    }

    @Override
    public void setTopic(String str) {
        topic = str;
    }

    @Override
    public void manage(BlockingQueue<Message> q) {
        queue = q;
    }

    @Override
    public void setPubsubService(PubsubService service) {
        this.service = service;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            Message msg = service.pull(topic);
            try {
                queue.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
