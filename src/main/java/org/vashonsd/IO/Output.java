package org.vashonsd.IO;

import org.vashonsd.Message;

import java.util.concurrent.BlockingQueue;

public interface Output extends Runnable {

    public void stop();

    void write(Message m);

    public void observe(BlockingQueue<String> queue);
}
