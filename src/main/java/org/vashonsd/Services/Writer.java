package org.vashonsd.Services;

import org.vashonsd.Message;

/**
 * A Writer represents any service that can write to and pull from a pub/sub service at a high level of abstraction.
 *
 * Issues of authentication, translation of Message to service-specific format, and subscriptions should be handled
 * within the service.
 */
public interface Writer {

    void write(String topic, Message msg);
}
