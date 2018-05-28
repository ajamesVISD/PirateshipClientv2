package org.vashonsd.Services;

import org.vashonsd.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DummyPubsub implements PubsubService {

    Map<String, BlockingQueue<Message>> store = new HashMap<String, BlockingQueue<Message>>();

    /**
     * We are going to simulate a backend engine, observing Requests and publishing them out as Responses.
     *
     * For this, we will need to read the username field of the Message and use that to create a topic.
     * @param msg
     */
    @Override
    public void publish(Message msg) {
        String topic = msg.getUsername();
        if(!store.containsKey(topic)) {
            store.put(topic, new LinkedBlockingQueue<Message>());
        }
        BlockingQueue<Message> thisQueue = store.get(topic);
        thisQueue.add(msg);
    }

    /**
     * This is some very stupid behavior. If the topic does not exist in the map, create it and wait.
     * Still, this will obviate race conditions with publish.
     *
     * @param topic
     * @return
     */
    @Override
    public Message pull(String topic) {
        if(!store.containsKey(topic)) {
            store.put(topic, new LinkedBlockingQueue<Message>());
        }
        try {
            Message msg = store.get(topic).take();
            msg.setBody(msg.getBody() + "!!! Get hype!!!");
            return msg;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
