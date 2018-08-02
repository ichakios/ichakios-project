package com.ichakios;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReader
{

	public static void main(String[] args)
	{
		try
		{
			readFileUsing_fileOutputStream();
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

	private static void readFileUsing_fileOutputStream()
	throws IOException 
	{
		System.out.println("start reading a file by FileOutputStream");
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
