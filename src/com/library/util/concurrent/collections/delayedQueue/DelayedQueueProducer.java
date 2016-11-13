package com.library.util.concurrent.collections.delayedQueue;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayedQueueProducer {
	
    // Creates an instance of blocking queue using the DelayQueue.
    private BlockingQueue queue;

	private final Random random = new Random();
	
	public DelayedQueueProducer(BlockingQueue queue) {
		super();
		this.queue = queue;
	}
	
	private Thread producerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                  
                    // Put some Delayed object into the DelayQueue.
                    int delay = random.nextInt(10000);
                    DelayedObject object = new DelayedObject(
                            UUID.randomUUID().toString(), delay);

                    System.out.printf("Put object = %s%n", object);
                    queue.put(object);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }, "Producer Thread");
	
	public void start(){
		this.producerThread.start();
	}

}
