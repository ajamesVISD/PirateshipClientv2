package org.vashonsd.IO.Services;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.vashonsd.Message;

import java.io.IOException;

public class GooglePubSubWriter implements Writer {

    private Publisher publisher;

    public GooglePubSubWriter(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void write(String uuid, Message msg) {
        PubsubMessage pmsg = PubsubMessage
                .newBuilder()
                .setData(ByteString.copyFromUtf8(msg.getBody()))
                .putAttributes("uuid", uuid)
                .build();
        publisher.publish(pmsg);
    }

    public static GooglePubSubWriter.Builder fromBuilder() {
        return new GooglePubSubWriter.Builder();
    }

    public static class Builder {

        GoogleCredentialsBuilder credentialsBuilder;
        String project;
        String role;
        String topic;

        public Builder() {
            credentialsBuilder = new GoogleCredentialsBuilder();
            role = "pirateship-requests-publisher";
            topic = "requests";
        }

        public Builder withProject(String str) {
            this.project = str;
            return this;
        }

        public GooglePubSubWriter.Builder withRole(String str) {
            role = str;
            return this;
        }

        public GooglePubSubWriter.Builder withTopic(String str) {
            topic = str;
            return this;
        }

        public GooglePubSubWriter build() {
            GoogleCredentials creds = credentialsBuilder.withRole(role)
                    .build();
            ProjectTopicName projectTopicName = ProjectTopicName.of("pirateship-requests", "requests");
            Publisher pub = null;
            try {
                pub = Publisher.newBuilder(projectTopicName)
                        .setCredentialsProvider(FixedCredentialsProvider.create(creds))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GooglePubSubWriter(pub);
        }
    }
}
