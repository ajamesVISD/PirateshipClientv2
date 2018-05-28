package org.vashonsd.Output;

import java.util.concurrent.BlockingQueue;

public class ConsoleWriter implements Writer {

    private boolean running;
    private BlockingQueue<String> outboundStrings;

    @Override
    public void stop() {
        running = false;
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
            try {
                msg = outboundStrings.take();
                System.out.println(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
