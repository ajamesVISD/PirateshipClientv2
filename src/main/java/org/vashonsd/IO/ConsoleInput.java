package org.vashonsd.IO;

import org.vashonsd.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConsoleInput implements Input {

    private final InputStream sysIn = System.in;
    private final BufferedReader in = new BufferedReader(new InputStreamReader(sysIn));
    private boolean running;
    private String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Message read() {
        String str;
        try {
            if(sysIn.available() > 0) {
                str = in.readLine();
                return Message.fromBuilder()
                        .withUuid(uuid)
                        .withBody(str)
                        .build();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
