package org.vashonsd;

import org.vashonsd.Input.Reader;
import org.vashonsd.Output.Writer;
import org.vashonsd.Request.Producer;
import org.vashonsd.Response.Consumer;
import org.vashonsd.Services.PubsubService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by andy on 5/24/18.
 */
public class PirateshipClient implements Runnable {

    private Producer requestProducer;
    private Consumer responseConsumer;

    private org.vashonsd.Output.Writer outputWriter;
    private org.vashonsd.Input.Reader inputReader;

    private final BlockingQueue<Message> responses = new LinkedBlockingDeque<>();
    private final BlockingQueue<Message> requests = new LinkedBlockingDeque<>();

    private final BlockingQueue<String> inStrings = new LinkedBlockingDeque<>();
    private final BlockingQueue<String> outStrings = new LinkedBlockingDeque<>();

    private String username;
    private boolean running;
    private MessageBuilder messageBuilder;

    /**
      * A PirateshipClient handles communications with the PubSub server.
      * It also collects input from the user and returns the result to the user, for whatever I/O the user has chosen.
      *
      * The specifics of how this happens are up to the implementation.
      *
      */
    public PirateshipClient(Reader inputReader,
                            Writer outputWriter,
                            Consumer responseConsumer,
                            Producer requestProducer) {
        this.inputReader = inputReader;
        inputReader.manage(inStrings);
        this.outputWriter = outputWriter;
        outputWriter.observe(outStrings);

        this.responseConsumer = responseConsumer;
        responseConsumer.manage(responses);
        this.requestProducer = requestProducer;
        requestProducer.observe(requests);

        messageBuilder = new MessageBuilder();
    }

    public static PirateshipClientBuilder fromBuilder() {
        return new PirateshipClientBuilder();
    }

    public Producer getRequestProducer() {
        return requestProducer;
    }

    public void setRequestProducer(Producer requestProducer) {
        this.requestProducer = requestProducer;
    }

    public Consumer getResponseConsumer() {
        return responseConsumer;
    }

    public void setResponseConsumer(Consumer responseConsumer) {
        this.responseConsumer = responseConsumer;
    }
    //    runnable.terminate();
    //    thread.join();
    @Override
    public void run() {
        running = true;
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.execute(requestProducer);
        pool.execute(responseConsumer);
        pool.execute(outputWriter);
        pool.execute(inputReader);

        String input;
        Message resp;
        try {
            outStrings.put("Starting...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(running) {
            //Check the inputStrings to see if something has come in from our system in.
            if((input = inStrings.poll()) != null) {
                try {
                    requests.put(messageBuilder.build(input));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if((resp = responses.poll()) != null) {
                try {
                    outStrings.put(resp.getBody());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setPubsubService(PubsubService service) {
        requestProducer.setPubsubService(service);
        responseConsumer.setPubsubService(service);
    }

    public void setUsername(String username) {
        this.username = username;
        messageBuilder.setUsername(username);
        responseConsumer.setTopic(username);
    }
}
