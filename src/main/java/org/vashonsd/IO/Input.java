package org.vashonsd.IO;

import org.vashonsd.Message;

public interface Input extends Runnable {

    void setUuid(String str);

    public Message read();

    public void stop();
}
