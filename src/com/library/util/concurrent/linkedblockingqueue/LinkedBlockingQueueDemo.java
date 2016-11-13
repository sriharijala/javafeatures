package com.library.util.concurrent.linkedblockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueDemo {

	public static void main(String[] args) {
    	
        LinkedBlockingQueue queue = new LinkedBlockingQueue(10);
        
        Producer producer = new Producer(queue);
        ObservingConsumer obsConsumer = new ObservingConsumer(queue, producer);
        RemovingConsumer remConsumer = new RemovingConsumer(queue, producer);
        
        Thread producerThread = new Thread(producer);
        Thread obsConsumerThread = new Thread(obsConsumer);
        Thread remConsumerThread = new Thread(remConsumer);
        
        producerThread.start();
        obsConsumerThread.start();
        remConsumerThread.start();
    }
}
