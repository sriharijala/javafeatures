package com.library.consumer.producer;


/*
 * launches the producer and consumer threads.
 */
public class ProducerConsumer {
	
    public static void main(String[] args) {
    	
        Drop drop = new Drop();
      
        (new Thread(new Producer(drop))).start();
        
        
        (new Thread(new Consumer(drop))).start();
    }
}
