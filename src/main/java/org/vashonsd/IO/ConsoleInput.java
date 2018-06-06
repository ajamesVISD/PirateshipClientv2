package org.vashonsd.IO;

import org.vashonsd.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConsoleInput implements Input {

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private BlockingQueue<String> stringsFromInput;
    private boolean running;
    private String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ConsoleInput() {
        stringsFromInput = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                stringsFromInput.offer(in.readLine());
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
    public Message read() {
        String str;
        if((str = stringsFromInput.poll()) != null) {
            return Message.fromBuilder()
                    .withUuid(uuid)
                    .withBody(str)
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void stop() {
        running = false;
    }

}
