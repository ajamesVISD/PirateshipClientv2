package org.vashonsd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by andy on 5/24/18.
 */
public class PirateshipClient implements Runnable {

    private RequestWriter requestWriter;
    private ResponseReader responseReader;

    private BlockingQueue<Message> responses = new LinkedBlockingDeque<>();


    /**
      * A PirateshipClient handles communications with the PubSub server.
      *
      * By working with the interfaces ResponseReader and RequestWriter and the abstract class Message, we decouple
      * the client from a specific implementation. We do specify that a PirateshipClient needs to be able to retrieve
      * messages (Requests and Responses) from whatever messaging service it uses, including pulling by topic.
      *
      * The specifics of how this happens are up to the implementation.
      *
      */
    public PirateshipClient() {

    }


    public RequestWriter getRequestWriter() {
        return requestWriter;
    }

    public void setRequestWriter(RequestWriter requestWriter) {
        this.requestWriter = requestWriter;
    }

    public ResponseReader getResponseReader() {
        return responseReader;
    }

    public void setResponseReader(ResponseReader responseReader) {
        this.responseReader = responseReader;
    }

//    public static void main(String[] args) throws InterruptedException, IOException {
//        Runnable r = new PubSubClient("pirateship-data", "requests-read", messages, credentials);
//        new Thread(r).start();
//        PubsubMessage msg;
//        while(true) {
//            msg = messages.take();
//            System.out.println(msg);
//        }
//    }

    @Override
    public void run() {

    }

    public static PirateshipClient newBuilder(CLIENT_IMPLEMENTATIONS c) {
        PirateshipClient pc = new PirateshipClient();

        switch (c) {
            case GOOGLE_PUBSUB:
                pc.setResponseReader(new PubsubSubscriber());
                pc.setRequestWriter(new PubsubPublisher());
                break;
        }
        return pc;
    }
}
