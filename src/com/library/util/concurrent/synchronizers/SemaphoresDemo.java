package com.library.util.concurrent.synchronizers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
SemaphoreDemo drives the application by creating executors and having them execute a 
runnable that repeatedly acquires string item resources from a pool (implemented by Pool)
and then returns them.
*/
public class SemaphoresDemo {
	
	public static void main(String... args) {
		
		final Pool pool = new Pool();
		
		Runnable itemAccess = new Runnable() {
			
			@Override
			public void run() {
				
				String name = Thread.currentThread().getName();
				
				try {
					//keep getting an item and putting back an item
					while(true){
						
						String item =  pool.getItem();
						
						System.out.printf("%s aquiring %s %n", name, item);
						
						//sleep some time
						Thread.sleep(1000 + (int)Math.random()*100);
						
						System.out.printf("%s putting back %s %n", name, item);
						
						pool.putItem(item);

					}	
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}; //Runnable end
		
		ExecutorService es = Executors.newFixedThreadPool(Pool.POOL_SIZE);
		
		for(int i=0; i < Pool.POOL_SIZE; i++) {
			es.execute(itemAccess);
		}
		
	}	
}

/*
Pool provides String getItem() and void putItem(String item) methods for obtaining and 
returning resources. Before obtaining an item in getItem(), a thread must acquire a permit
from the semaphore, guaranteeing that an item is available for use. When the thread has 
finished with the item, it calls putItem(String), which returns the item to the pool and
then returns a permit to the semaphore, which lets another thread acquire that item.

No synchronization lock is held when acquire() is called because that would prevent an item from
being returned to the pool. However, String getNextAvailableItem() and boolean markAsUnused(String item) 
are synchronized to maintain pool consistency. 
(The semaphore encapsulates the synchronization needed to restrict access to the pool separately 
from the synchronization needed to maintain pool consistency.)

*/
final class Pool {
	
	public static final int POOL_SIZE = 5;
	
	private Semaphore available = new Semaphore(POOL_SIZE);
	private String[] items;
	private boolean[] used;
	
	public Pool() {

		items = new String[POOL_SIZE];
		used  = new boolean[POOL_SIZE];
		
		for (int i = 0; i < POOL_SIZE; i++) {
			items[i] = "ITEM" + (i+1);
			used[i] = false;
		}
 	
	}
	
	public String getItem() throws InterruptedException {
		
		available.acquire();
		return getNextAvailableItem();
		
	}
	
	private synchronized String getNextAvailableItem() {
		
		for (int i = 0; i < POOL_SIZE; i++) {
			
			if(!used[i]) {
				
				used[i] =  true;
				return items[i];
			}
		}
		
		return null; //hope it will never reach
	}
	
	public void putItem(String item) {
		
		if(markAsUnused(item))
			available.release();
	}

	private boolean markAsUnused(String item) {
		
		for (int i = 0; i < POOL_SIZE; i++) {
			
			if(items[i] == item) {

				if(used[i]) {
					//put the item back and return success
					used[i] =  false;
					return true;
					
				} else
					return false;
				
			} 
		}
		
		return false;
	}
	
}