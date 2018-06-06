package org.vashonsd;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 5/26/18.
 */
public class Message {

    Map<String, String> valueStore;
    String body;
    Timestamp timestamp;
    String uuid;

    boolean isAuthenticated;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Message() {
        valueStore = new HashMap<String, String>();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void put(String k, String v) {
        valueStore.put(k,v);
    }

    public String get(String k) {
        return valueStore.get(k);
    }

    public String getBody() {
        return body;
    }

    public void setTimestamp(String str) {
        timestamp = Timestamp.valueOf(str);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static Builder fromBuilder() {
        return new Builder();
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    /**
     * This class automates create a message with the given properties. For instance, we do not want to set the uuid
     * property of the Message every time we send it. Instead, we could create a Builder with its defaults set,
     * then call builder.write("The body of the message.").
     */
    public static final class Builder {
        Map<String, String> defaults;
        String defaultUuid;
        String body;
        private boolean isAuthenticated;

        public Message build() {
            Message msg = new Message();
            defaults = new HashMap<String, String>();
            for(String k : defaults.keySet()) {
                msg.put(k, defaults.get(k));
            }
            msg.setUuid(defaultUuid);
            msg.isAuthenticated = isAuthenticated;
            msg.setBody(body);
            return msg;
        }

        public void setUuid(String str) {
            this.defaultUuid = str;
        }

        public Builder withUuid(String str) {
            defaultUuid = str;
            return this;
        }

        public Builder withBody(String str) {
            body = str;
            return this;
        }

        public Builder setAuthentication(boolean a) {
            isAuthenticated = true;
            return this;
        }
    }
}
