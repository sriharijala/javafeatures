package com.library.concurrent.fork.join;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/*
 * This uses the asynchronous methods provided by the ForkJoinPool and ForkJoinTask classes for the 
 * management of tasks. You are going to implement a program that will search for files with a determined extension inside a folder and 
 * its sub-folders. The ForkJoinTask class you�re going to implement will process the content of a folder. For each sub-folder inside that folder,
 * it will send a new task to the ForkJoinPool class in an asynchronous way. For each file inside that folder, the task will check 
 * the extension of the file and add it to the result list if it proceeds.
 */

public class FolderProcessor extends RecursiveTask<List<String>> {
	
	private static final long serialVersionUID = 1L;
	
	// This attribute will store the full path of the folder this task is going
	// to process.
	private final String path;
	
	// This attribute will store the name of the extension of the files this
	// task is going to look for.
	private final String extension;

	// Implement the constructor of the class to initialize its attributes
	public FolderProcessor(String path, String extension) {
		this.path = path;
		this.extension = extension;
	}

	// Implement the compute() method. As you parameterized the RecursiveTask
	// class with the List<String> type,
	// this method has to return an object of that type.
	@Override
	protected List<String> compute() {
		
		// List to store the names of the files stored in the folder.
		List<String> list = new ArrayList<String>();
		
		// FolderProcessor tasks to store the subtasks that are going to process
		// the subfolders stored in the folder
		List<FolderProcessor> tasks = new ArrayList<FolderProcessor>();
		
		// Get the content of the folder.
		File file = new File(path);
		File content[] = file.listFiles();
		
		// For each element in the folder, if there is a subfolder, create a new
		// FolderProcessor object
		// and execute it asynchronously using the fork() method.
		if (content != null) {
			
			for (int i = 0; i < content.length; i++) {
				
				if (content[i].isDirectory()) {
					
					FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
					task.fork();
					tasks.add(task);
				}
				
				// Otherwise, compare the extension of the file with the
				// extension you are looking for using the checkFile() method
				// and, if they are equal, store the full path of the file in
				// the list of strings declared earlier.
				else {
					if (checkFile(content[i].getName())) {
						list.add(content[i].getAbsolutePath());
					}
				}
			}
		}
		
		
		// If the list of the FolderProcessor subtasks has more than 50
		// elements,
		// write a message to the console to indicate this circumstance.
		if (tasks.size() > 50) {
			System.out.printf("%s: %d tasks ran.%n", file.getAbsolutePath(), tasks.size());
		}
		
		// add to the list of files the results returned by the subtasks
		// launched by this task.
		addResultsFromTasks(list, tasks);
		
		// Return the list of strings
		return list;
	}

	// For each task stored in the list of tasks, call the join() method that
	// will wait for its finalization and then will return the result of the
	// task.
	// Add that result to the list of strings using the addAll() method.
	private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
		
		for (FolderProcessor item : tasks) {
			list.addAll(item.join());
		}
	}

	// This method compares if the name of a file passed as a parameter ends
	// with the extension you are looking for.
	private boolean checkFile(String name) {
		return name.endsWith(extension);
	}
	
	public static void main(String[] args) 
	   {
	      //Create ForkJoinPool using the default constructor.
	      ForkJoinPool pool = new ForkJoinPool();
	      
	      //Create three FolderProcessor tasks. Initialize each one with a different folder path.
	      FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
	      FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
	      FolderProcessor documents = new FolderProcessor("C:\\Documents And Settings", "log");
	      
	      //Execute the three tasks in the pool using the execute() method.
	      pool.execute(system);
	      pool.execute(apps);
	      pool.execute(documents);
	      
	      //Write to the console information about the status of the pool every second 
	      //until the three tasks have finished their execution.
	      do
	      {
	         System.out.printf("******************************************%n");
	         System.out.printf("Main: Parallelism: %d%n", pool.getParallelism());
	         System.out.printf("Main: Active Threads: %d%n", pool.getActiveThreadCount());
	         System.out.printf("Main: Task Count: %d%n", pool.getQueuedTaskCount());
	         System.out.printf("Main: Steal Count: %d%n", pool.getStealCount());
	         System.out.printf("******************************************%n");
	         try
	         {
	            TimeUnit.SECONDS.sleep(1);
	         } catch (InterruptedException e)
	         {
	        	 System.out.print(e);
	         }
	      } while ((!system.isDone()) || (!apps.isDone()) || (!documents.isDone()));
	      
	      
	      //Shut down ForkJoinPool using the shutdown() method.
	      pool.shutdown();
	      
	      //Write the number of results generated by each task to the console.
	      List<String> results;
	      
	      results = system.join();
	      System.out.printf("System: %d files found.%n", results.size());
	      
	      results = apps.join();
	      System.out.printf("Apps: %d files found.%n", results.size());
	      
	      results = documents.join();
	      System.out.printf("Documents: %d files found.%n", results.size());
	   }
}
