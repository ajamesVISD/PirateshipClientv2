package org.vashonsd;

import org.vashonsd.Input.Reader;
import org.vashonsd.Output.Writer;
import org.vashonsd.Request.Producer;
import org.vashonsd.Response.Consumer;

public class PirateshipClientBuilder {
    private Reader reader;
    private Writer outputWriter;
    private Consumer responseConsumer;
    private Producer requestWriter;
    private String username;

    public PirateshipClientBuilder withInputSource(Reader r) {
        reader = r;
        return this;
    }

    public PirateshipClientBuilder withOutputDestination(Writer w) {
        outputWriter = w;
        return this;
    }

    public PirateshipClientBuilder withRequestWriter(Producer w) {
        requestWriter = w;
        return this;
    }

    public PirateshipClientBuilder withResponseConsumer(Consumer r) {
        this.responseConsumer = r;
        return this;
    }

    public PirateshipClientBuilder withUsername(String str) {
        username = str;
        return this;
    }

    public PirateshipClient build() {
        PirateshipClient pc = new PirateshipClient(reader, outputWriter, responseConsumer, requestWriter);
        pc.setUsername(username);
        return pc;
    }
}
