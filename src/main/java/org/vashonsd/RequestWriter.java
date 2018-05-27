package org.vashonsd;

/**
 * Created by andy on 5/26/18.
 */
public interface RequestWriter {

    /**
     * This blocks until the server accepts it. Returns boolean for its acceptance.
     * @param m
     * @return
     */
    boolean offer(Message m);

    void close();
}
