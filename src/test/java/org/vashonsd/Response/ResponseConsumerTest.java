package org.vashonsd.Response;

public class ResponseConsumerTest {

//    Subscriber testConsumer;
//    Subscriber consumer;
//
//    @Before
//    public void setUp() throws Exception {
//        mockResponsesAsMessages = new LinkedBlockingDeque<>();
//        responsesAsMessages = new LinkedBlockingDeque<>();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void run() {
//        testConsumer.run();
//    }
//
//    @Test
//    public void testWithMock() {
//        Thread thr = new Thread(testConsumer);
//        thr.start();
//        Message msg;
//        while(true) {
//            if((msg = mockResponsesAsMessages.poll()) != null) {
//                System.out.println(msg.getBody());
//            }
//        }
//    }
//
//    @Test
//    public void testWithGoogle() {
//        Thread thr = new Thread(consumer);
//        thr.start();
//        Message msg;
//        while(true) {
//            if((msg = responsesAsMessages.poll()) != null) {
//                System.out.println(msg.getBody());
//            }
//        }
//    }
//    @Test
//    public void testThreadedRun() throws InterruptedException {
//        BlockingQueue<Message> testMessages = new LinkedBlockingDeque<>();
//        testConsumer.manage(testMessages);
//        Thread testThread = new Thread(testConsumer);
//        testThread.start();
//        boolean running = true;
//        while(running) {
//            Message resp = testMessages.take();
//            System.out.println(resp);
//        }
//    }
}