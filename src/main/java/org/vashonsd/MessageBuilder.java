package org.vashonsd;

import java.util.HashMap;
import java.util.Map;

/**
 * This class automates create a message with the given properties. For instance, we do not want to set the username
 * property of the Message every time we send it. Instead, we could create a MessageBuilder with its defaults set,
 * then call builder.write("The body of the message.").
 */
public class MessageBuilder {
    Map<String, String> defaults;
    String defaultUser;

    public Message build(String text) {
        Message msg = new Message();
        defaults = new HashMap<String, String>();
        for(String k : defaults.keySet()) {
            msg.put(k, defaults.get(k));
        }
        msg.setUsername(defaultUser);
        msg.setBody(text);
        return msg;
    }

    public void setUsername(String username) {
        defaultUser = username;
    }

    public MessageBuilder withUsername(String str) {
        setUsername(str);
        return this;
    }
}
