package org.vashonsd.Input;

import org.vashonsd.Message;
import org.vashonsd.MessageBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class ConsoleReader implements Reader {

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private BlockingQueue<String> outboundRequests;
    private boolean running;

    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                outboundRequests.offer(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void manage(BlockingQueue<String> queue) {
        outboundRequests = queue;
    }

    @Override
    public void stop() {
        running = false;
    }

}
