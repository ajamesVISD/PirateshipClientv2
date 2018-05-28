package org.vashonsd.Input;

import org.vashonsd.Message;
import org.vashonsd.MessageBuilder;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public interface Reader extends Runnable {

    public void manage(BlockingQueue<String> queue);

    public void stop();
}
