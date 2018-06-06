package org.vashonsd.IO;

import org.vashonsd.Message;

import java.util.concurrent.BlockingQueue;

public class ConsoleOutput implements Output {

    private volatile boolean running;
    private BlockingQueue<String> outboundStrings;

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void write(Message m) {
        System.out.println(m.getBody());
    }

    @Override
    public void observe(BlockingQueue<String> queue) {
        outboundStrings = queue;
    }

    @Override
    public void run(){
        running = true;
        String msg;
        while(running) {
            if((msg = outboundStrings.poll()) != null) {
                System.out.println(msg);
            }
        }
    }
}
