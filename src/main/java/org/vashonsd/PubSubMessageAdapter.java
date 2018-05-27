package org.vashonsd;

import com.google.pubsub.v1.PubsubMessage;

import java.util.Map;

/**
 * Created by andy on 5/26/18.
 */
public class PubSubMessageAdapter {

    public static Message adapt(PubsubMessage message) {
        Message msg = new Message();
        Map<String,String> attrs = message.getAttributesMap();
        msg.setBody(attrs.remove("data"));
        msg.setTimestamp(attrs.remove("publishTime"));
        for(String k : attrs.keySet()) {
            msg.put(k, attrs.get(k));
        }
        return msg;
    }

}
