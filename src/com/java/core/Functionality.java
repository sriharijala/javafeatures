package com.java.core;

import java.util.Date;

import org.junit.Test;

public class Functionality {
	
	@Test
	public void addShutdownHook() {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				System.out.println("Shutting down JVM at time: " + new Date());
			}
		});
	}

}
