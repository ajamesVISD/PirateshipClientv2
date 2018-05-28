package org.vashonsd.Services;

import org.vashonsd.Message;

public class GooglePubSubService implements PubsubService {

    @Override
    public void publish(Message msg) {

    }

    @Override
    public Message pull(String topic) {
        return null;
    }
}
