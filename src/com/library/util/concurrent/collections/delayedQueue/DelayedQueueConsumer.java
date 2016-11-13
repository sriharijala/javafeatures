package com.library.util.concurrent.collections.delayedQueue;

import java.util.concurrent.BlockingQueue;

public class DelayedQueueConsumer {
	
	private String name;
	
    private BlockingQueue queue;

	public DelayedQueueConsumer(String name, BlockingQueue queue) {
		super();
		this.name = name;
		this.queue = queue;
	}
	
	private Thread consumerThread =  new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    // Take elements out from the DelayQueue object.
                    DelayedObject object = (DelayedObject) queue.take();
                    System.out.printf("[%s] - Take object = %s%n",
                            Thread.currentThread().getName(), object);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
	
	public void start(){
		this.consumerThread.setName(name);
		this.consumerThread.start();
	}

}
