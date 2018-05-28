package org.vashonsd.Request;

import org.vashonsd.Message;
import org.vashonsd.Services.PubsubService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by andy on 5/26/18.
 */
public interface Producer extends Runnable {

    void observe(BlockingQueue<Message> outboundMessages);

    void close();

    void setPubsubService(PubsubService service);
}
