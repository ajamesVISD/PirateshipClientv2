The core of this structure is two Runnable classes, each of which would run in a thread.

One thread collects lines of text from an InputStream and pushes them to the `requests` topic of the pirateship-data PubSub project.

The other thread runs a Client that is configured to listen, via one subscription, to the messages published to a single topic. The topic and subscription should both match the uuid.

The Client needs to be supplied with:

-- Credentials. These could be read from a .json file associated with a Service Account, either via an environmental variable or from the filesystem directly. 