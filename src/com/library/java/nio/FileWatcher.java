package com.library.java.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.List;


/*
java.nio.file package has a file change notification API, called the Watch Service.

A watch service watches registered objects for changes and the resulting events. 
For example, a file manager application may use a watch service to monitor a directory 
for changes, so that it can update its display of the list of files when files are created, renamed or deleted.

The watch service usage overview steps:

Register a directory with the watch service.
Specify the type of events of interest in the directory, like file creation or deletion.
When the watch service detects an event, it is queued (to the watch service) - and a 
consumer process (like an application's thread) can handle the events as required.
 */

public class FileWatcher {
	
	private static final String watchedDir = "c:/temp/";

	public static void main(String[] args) throws IOException {
		new FileWatcher().doProcess();
	}

	private void doProcess() throws IOException {
		
		// (1) Create a new watch service
		WatchService watchService = FileSystems.getDefault().newWatchService();
		System.out.println("Watch service example 1:");
		
		// (2) Get the directory to be monitored
		Path dir = Paths.get(watchedDir);
		
		// (3) Register the directory to be monitored with the watch service
		WatchKey watchKey = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		System.out.println("Registered dir: " + dir.toString());
		System.out.println("    Watch key (valid): " + watchKey.isValid());
		
		
		// (4) Get and process the events that occur
		INFINITE_WHILE_LOOP: while (true) {
			
			// (4a) Wait for the watch key to be signaled of events
			try {
				
				System.out.println("Waiting for watch key to be signaled...");
				watchService.take();
				
			} catch (InterruptedException ex) {
				
				ex.printStackTrace();
				break INFINITE_WHILE_LOOP;
				
			}
			
			// (4b) Get and process the events for the watch key
			List<WatchEvent<?>> eventList = watchKey.pollEvents();
			System.out.println("Process the pending events for the watch key: " + eventList.size());
			
			EVENT_FOR_LOOP: for (WatchEvent<?> genericEvent : eventList) {
				
				// Retrieve and process the event kind
				if (genericEvent.kind() == OVERFLOW) {
					System.out.println("Overflow event");
					continue EVENT_FOR_LOOP; // next event
				}
				// else, genericEvent.kind() is WatchEvent.Kind<Path>
				// values: ENTRY_CREATE...
				System.out.println("Path event kind: " + genericEvent.kind());
				
				// Retrieve the file name associated with the event
				Path filePath = (Path) genericEvent.context();
				System.out.println("    File: " + filePath.toAbsolutePath().toString());
				
			} // end EVENT_FOR_LOOP (for a watch key)
			
			
			// (4c) Reset the watch key
			boolean validKey = watchKey.reset();
			System.out.println("Watch key reset.");
			
			if (!validKey) {
				
				System.out.println("Invalid watch key, close the watch service");
				break INFINITE_WHILE_LOOP;
				
			}
		} // end, INFINITE_WHILE_LOOP
		
		// (5) Close the watch service
		watchService.close();
		System.out.println("Watch service closed.");
		
	} // doProcess()
} // FileWatcher