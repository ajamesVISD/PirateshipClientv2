package org.vashonsd.IO;

import org.vashonsd.Message;

public interface Input {

    void setUuid(String str);

    public Message read();
}
