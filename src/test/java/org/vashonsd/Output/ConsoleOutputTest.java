package org.vashonsd.Output;

import org.junit.Test;
import org.vashonsd.IO.ConsoleOutput;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConsoleOutputTest {
    BlockingQueue<String> stringsToOutput = new LinkedBlockingDeque<>();

    class Emitter implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                stringsToOutput.offer("String #" + i);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void run() {
        ConsoleOutput writer = new ConsoleOutput();
        writer.observe(stringsToOutput);
        Thread emitter = new Thread(new Emitter());
        emitter.start();

        writer.run();
    }
}