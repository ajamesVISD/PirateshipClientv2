Workflow

The Client supplies the Reader with a blocking queue of Strings, `stringsFromInput`.

The Reader is, in a Thread, watching in input source, in this case stdin.

When stdin produces a String, the Reader puts it onto `stringsFromInput`.

The Client is running and, in its run method, polling `stringsFromInput`. When it finds one, it builds the String as a Message and puts it onto another blocking queue, `messagesToSend`.

The RequestProducer is watching `messagesToSend` in a running Thread. When a new Message enters the queue