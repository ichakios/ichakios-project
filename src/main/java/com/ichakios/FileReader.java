package com.ichakios;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader
{

	public static void main(String[] args)
	{
		try
		{
			writingToAFileUsing_fileOutputStream();
			writingToAFileUsing_bufferWriter();
			writingToAFileUsing_fileWriter();
			writingToAFileUsing_dataOutputStream();
			writingToAFileUsing_randomAccessFile();
			writingToAFileUsing_fileChannel();
			writingToAFileUsing_java7();
			
			lock_file();
		}
		catch (Exception e)
		{
			if(FileNotFoundException.class.isAssignableFrom(e.getClass())) {
				System.out.println("file doens not exist: "+e.getMessage());
			}
			else {
				e.printStackTrace();
			}
		}

	}

	private static void lock_file() throws IOException
	{
		System.out.println("lock a file");
		String fileName = "myfile-java7.txt";
		RandomAccessFile stream = new RandomAccessFile(fileName, "rw");
	    FileChannel channel = stream.getChannel();
	    FileLock lock = null;
	    try {
	        lock = channel.tryLock();
	    } catch (final OverlappingFileLockException e) {
	        stream.close();
	        channel.close();
	    }
	    stream.writeChars("locking");
	    lock.release();
	    stream.close();
	    channel.close();
		
	}

	private static void writingToAFileUsing_java7() throws IOException
	{
		String fileName = "myfile-java7.txt";
		String str = "Hello java7";
		 
	    Path path = Paths.get(fileName);
	    byte[] strToBytes = str.getBytes();
	 
	    Files.write(path, strToBytes);
	    String read = Files.readAllLines(path).get(0);
	    
	    if(str.equals(read)) {
			System.out.println("file with java7 is created successfully....");
		}
	}

	private static void writingToAFileUsing_fileChannel() 
	throws IOException
	{
		String fileName = "myfile-FileChannel.txt";
		RandomAccessFile stream = new RandomAccessFile(fileName, "rw");
	    FileChannel channel = stream.getChannel();
	    String value = "Hello FileChannel";
	    byte[] strBytes = value.getBytes();
	    ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
	    buffer.put(strBytes);
	    buffer.flip();
	    channel.write(buffer);
	    stream.close();
	    channel.close();
	}

	private static void writingToAFileUsing_randomAccessFile()
	throws IOException
	{
		String fileName = "myfile-RandomAccessFile.txt";
		String value = "Hello RandomAccessFile";
		RandomAccessFile writer = new RandomAccessFile(fileName, "rw");
		writer.seek(1);
		writer.writeBytes(value);
		writer.close();

	}

	private static void writingToAFileUsing_dataOutputStream() 
			throws IOException
	{
		String fileName = "myfile-DataOutputStream.txt";
		String value = "Hello DataOutputStream";
		FileOutputStream fos = new FileOutputStream(fileName);
		DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
		outStream.writeUTF(value);
		outStream.close();

		// verify the results
		String result;
		FileInputStream fis = new FileInputStream(fileName);
		DataInputStream reader = new DataInputStream(fis);
		result = reader.readUTF();
		reader.close();
		if(value.equals(result)) {
			System.out.println("file with DataOutputStream is created successfully....");
		}
	}
	
	private static void writingToAFileUsing_fileWriter() 
	throws Exception
	{
		String fileName = "myfile-FileWriter.txt";
		System.out.println("start writing to a file by FileWriter");
		String str = "this is my file text FileWriter";
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(str);
		printWriter.print("\n");
		printWriter.printf("my name is %s and and i have %d $ in my pocket", "ichakios", 1500);
		printWriter.close();
		System.out.println("file is created successfully by the BufferedWriter....");
	}

	private static void writingToAFileUsing_bufferWriter()
	throws IOException
	{
		String fileName = "myfile-BufferedWriter.txt";
		System.out.println("start writing to a file by BufferedWriter");
		String str = "this is my file text BufferedWriter";
	    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    writer.write(str);
	    writer.close();
	    
	    // we can append a string to an existing file.
	    str = "!Appended text";
	    writer = new BufferedWriter(new FileWriter(fileName, true));
	    writer.append(' ');
	    writer.append(str);
	    writer.close();
	    System.out.println("file is created successfully by the BufferedWriter....");
	}

	@SuppressWarnings("deprecation")
	private static void writingToAFileUsing_fileOutputStream()
	throws IOException 
	{
		System.out.println("start  writing to a file by FileOutputStream");
		String str = "this is my file text";
		String fileName = "myfile.txt";
	    FileOutputStream outputStream = new FileOutputStream(fileName);
	    byte[] strToBytes = str.getBytes();
	    outputStream.write(strToBytes);
	    outputStream.close();
	    
	 // verify the results
	    String result;
	    FileInputStream fis = new FileInputStream(fileName);
	    DataInputStream reader = new DataInputStream(fis);
	    //this method is deprecated as for the JDK 1.1, we should read the file via byfferreader instead.
	    result = reader.readLine();
	    reader.close();
	 
	    if(str.equals(result)) {
	    	System.out.println("file is created successfully....");
	    }
		
	}

}
