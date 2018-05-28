package org.vashonsd.Services;

import org.vashonsd.Message;

/**
 * A PubsubService represents any service that can write to and pull from a pub/sub service at a high level of abstraction.
 *
 * Issues of authentication, translation of Message to service-specific format, and subscriptions should be handled
 * within the service.
 */
public interface PubsubService {

    void publish(Message msg);

    /**
     * A blocking method for retrieving messages from the pub/sub service. That is, waits until a message appears.
     *
     * @param topic
     * @return A Message in internal format, with a username, timestamp, and body.
     */
    Message pull(String topic);

}
