package org.vashonsd;

import java.util.concurrent.BlockingQueue;

/**
 * Created by andy on 5/26/18.
 */
public interface ResponseReader {

    /**
     * This method blocks, and it should. It should wait on its messaging service until a message comes through.
     * @return
     */
    Message take();

    void setTopic(String str);

    void setQueue(BlockingQueue<Message> q);

    /**
     * Call this to clean up the ResponseReader's act.
     */
    void close();
}