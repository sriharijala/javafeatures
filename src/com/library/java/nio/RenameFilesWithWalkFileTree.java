package com.library.java.nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/*
 * This example uses overloaded Files.walkFileTree methods that provide an 
 * easy way to traverse a file/directory tree and process files and directories 
 * along the way.
 * It renames files if they have spaces in their name and changes any uppercase letters
 * to lowercase.
*/

public class RenameFilesWithWalkFileTree {

	public static void main(String[] args) throws IOException {

		Path start = Paths.get("c:/temp");
		
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			
			@Override
			public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
				
				String fileName = filePath.getFileName().toString();
				String dirPath = filePath.getParent().toString();

				if (fileName.contains(" ")) {

					String newFileName = fileName.trim().toLowerCase().replace(" ", "");
					Path newFilePath = Paths.get(dirPath, newFileName);

					Files.move(filePath, newFilePath);

					System.out.format("\nRenamed %s to %s", filePath.toString(),  newFilePath.toString());
				}

				return FileVisitResult.CONTINUE;
			}
			
		});
	}
}
