package org.vashonsd.Examples;

// Imports the Google Cloud client library

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.ProjectTopicName;

public class CreateTopicExample {

    /**
     * Create a topic.
     *
     * @param args topicId
     * @throws Exception exception thrown if operation is unsuccessful
     */
    public static void main(String... args) throws Exception {

        // Your Google Cloud Platform project ID
        String projectId = ServiceOptions.getDefaultProjectId();
        ProjectName project = ProjectName.of(projectId);

        // Your topic ID, eg. "my-topic"
        String topicId = args[0];

//        System.out.println(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));

        // Create a new topic
        ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
        try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
            System.out.println("reached the creation of the topic");
            System.out.println(topicAdminClient.listTopics(project));
//            System.out.printf("Topic %s:%s created.\n", topic.getProject(), topic.getTopic());
        } catch (ApiException e) {
            System.out.println(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            System.out.println(e.getStatusCode().getCode());
            System.out.print(e.isRetryable());
        }


    }
}