package org.vashonsd;

/**
 * Created by andy on 5/26/18.
 */
public class PubsubPublisher implements RequestWriter {
    @Override
    public boolean offer(Message m) {
        return false;
    }

    @Override
    public void close() {

    }
}
