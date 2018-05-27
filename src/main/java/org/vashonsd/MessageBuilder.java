package org.vashonsd;

import java.util.Map;

/**
 * This class automates create a message with the given properties. For instance, we do not want to set the username
 * property of the Message every time we send it. Instead, we could create a MessageBuilder with its defaults set,
 * then call builder.write("The body of the message.").
 */
public class MessageBuilder {
    Map<String, String> defaults;

    public Message write(String text) {
        Message msg = new Message();
        for(String k : defaults.keySet()) {
            msg.put(k, defaults.get(k));
        }
        msg.setBody(text);
        return msg;
    }

    public void setUsername(String username) {
        defaults.put("username", username);
    }
}
