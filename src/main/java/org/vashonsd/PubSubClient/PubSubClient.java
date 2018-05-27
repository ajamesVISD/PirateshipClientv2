package org.vashonsd.PubSubClient;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.util.concurrent.BlockingQueue;

/**
 * Created by andy on 5/23/18.
 */
public class PubSubClient implements Runnable {

    private String projectId;
    private String subscription;
    private boolean running=false;

    private Subscriber subscriber;

    private final BlockingQueue<PubsubMessage> messages;

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    class MessageReceiverExample implements MessageReceiver {

        @Override
        public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
            messages.offer(message);
            consumer.ack();
        }
    }

    public PubSubClient(String id, String subscription, BlockingQueue<PubsubMessage> queue, GoogleCredentials credentials) {
        projectId = id;
        this.subscription = subscription;
        messages = queue;
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscription);
        subscriber =
                Subscriber.newBuilder(subscriptionName, new MessageReceiverExample())
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
    }

    public void run() {
        try {
            // create a subscriber bound to the asynchronous message receiver
            subscriber.startAsync().awaitRunning();
            running = true;
            // Continue to listen to messages
            while(running) {
                messages.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
