package org.vashonsd;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

/**
 * Created by andy on 5/26/18.
 */
public class PubsubSubscriber implements ResponseReader {

    private GoogleCredentials credentials;
    private String topic;
    private String subscription;
    ProjectSubscriptionName subscriptionName;

    MessageBuilder messageBuilder;

    private Subscriber subscriber;

    boolean authenticated;
    boolean running;

    private BlockingQueue<Message> messages;

    class MessageReceiverExample implements MessageReceiver {

        @Override
        public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
            Message msg = PubSubMessageAdapter.adapt(message);
            messages.offer(msg);
            consumer.ack();
        }
    }

    public PubsubSubscriber() {
        InputStream serviceAccountStream = null;
        try {
            serviceAccountStream = Files.newInputStream(Paths.get("src/main/resources/pirateship-data-owner.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        authenticated = false;
        running = false;
        topic = "";
        subscription = "";

        messageBuilder = new MessageBuilder();
    }

    private void setSubscription(String topic, String subscription) {
        this.topic = topic;
        this.subscription = subscription;
        subscriptionName = ProjectSubscriptionName.of(this.topic, this.subscription);
        subscriber = Subscriber.newBuilder(subscriptionName, new MessageReceiverExample())
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();
    }

    @Override
    public Message take() {
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

    @Override
    public void setTopic(String str) {
        topic = str;
    }

    @Override
    public void setQueue(BlockingQueue<Message> q) {
        messages = q;
    }

    @Override
    public void close() {

    }
}
