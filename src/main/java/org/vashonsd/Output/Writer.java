package org.vashonsd.Output;

import java.util.concurrent.BlockingQueue;

public interface Writer extends Runnable {

    public void stop();

    public void observe(BlockingQueue<String> queue);
}
