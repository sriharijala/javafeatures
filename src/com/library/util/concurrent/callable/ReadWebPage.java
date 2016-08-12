package com.library.util.concurrent.callable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ReadWebPage {
	
	public static void main(String... args) {
		
		ExecutorService ex = Executors.newSingleThreadExecutor();
		
		Callable<List<String>> readWebPage;
		
		readWebPage = new Callable<List<String>>() {
			
			

			@Override
			public List<String> call() throws Exception {
			
				List<String> lines = new ArrayList<>();
				
				//create URL object
				URL url = new URL(args[0]);
				
				//Open HTTP connection
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				
				//get input stream reader
				InputStreamReader isr;
				isr = new InputStreamReader(con.getInputStream());
				
				//get buffer reader
				BufferedReader br = new BufferedReader(isr);
				
				//read each line
				String line;
				while((line = br.readLine()) != null) {
					lines.add(line);
				}
				
				return lines;
			}
		};
		
		long startTime = System.currentTimeMillis();
		
		Future<List<String>> futureRespone =  ex.submit(readWebPage);
		
		try {
			
			//get the response, set timeout
			List<String> lines = futureRespone.get(10, TimeUnit.SECONDS);
			
			//print response.
			for(String line : lines) 
				 System.out.println(line);
			
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			
			e.printStackTrace();
			
		} finally {
			
			//critical to shutdown the executor 
			ex.shutdown();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Performance improvement (ms): " + (endTime - startTime));
		
	}

}
