package org.vashonsd.Response;

import org.vashonsd.Message;
import org.vashonsd.Services.PubsubService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by andy on 5/26/18.
 */
public interface Consumer extends Runnable {

    void setTopic(String str);

    void manage(BlockingQueue<Message> q);

    void setPubsubService(PubsubService service);

    /**
     * Call this to clean up the ResponseReader's act.
     */
    void stop();
}