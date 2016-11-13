package com.library.java.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/*
Advantage and Disadvantage of Memory Mapped file

Possibly main advantage of Memory Mapped IO is performance, which is important to build high frequency
 electronic trading system. Memory Mapped Files are way faster than standard file access via normal IO. 
 Another big advantage of memory mapped IO is that it allows you to load potentially larger file which 
 is not otherwise accessible. Experiments shows that memory mapped IO performs better with large files. 
 Though it has disadvantage in terms of increasing number of page faults. Since operating system only 
 loads a portion of file into memory if a page requested is not present in memory than it would 
 in page fault Most of major operating system like Windows platform, UNIX, Solaris and other UNIX like operating 
 system supports memory mapped IO and with 64 bit architecture you can map almost any file into memory 
 and access it directly using Java programming language. Another advantages is that the file can be shared, 
 giving you shared memory between processes and can be more than 10x lower latency than using a Socket over loopback.

*/

	
public class MemoryMappedFileDemo {

	private static int count = 10485760; //10 MB

    public static void main(String[] args) throws Exception {

        RandomAccessFile memoryMappedFile = new RandomAccessFile("largeFile.txt", "rw");

        //Mapping a file into memory

        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);

        //Writing into Memory Mapped File
        for (int i = 0; i < count; i++) {
            out.put((byte) 'A');
        }

        System.out.println("Writing to Memory Mapped File is completed");

        //reading from memory file in Java
        for (int i = 0; i < 10 ; i++) {
            System.out.print((char) out.get(i));
        }
        
        System.out.println();
        System.out.println("Reading from Memory Mapped File is completed");
        
        
        memoryMappedFile.writeUTF("Srihari");
        
        memoryMappedFile.close();

    }

}
