package org.vashonsd.IO.Services;


import org.vashonsd.Message;

public interface Reader {

    /**
     * Poll the external messaging service, fetch a message, and return it formatted as a Request.
     * @return
     */
    Message read();

    /**
     * Use this to cause threads to stop gracefully.
     */
    void stop();
}
