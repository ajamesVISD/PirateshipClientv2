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
}