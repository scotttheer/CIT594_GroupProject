package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger {
	
	private PrintWriter printer;

	private Logger() {}
	
	private static Logger instance = new Logger();
	
	public static Logger getInstance() {
		return instance;
	}
	
	//Initialize logger PrintWriter with log file
	public void init(File f, String[] args) {
		try {
			printer = new PrintWriter(f);
			printer.print(System.currentTimeMillis());
			for(int i=0; i<args.length; i++) {
				printer.print(" " + args[i]);
			}
			printer.println();
			printer.flush();
		}catch(FileNotFoundException e) {
			System.out.println("Logging File Not Found!");
		}
	}
	
	//Print opened file name to log file
	public void logOpenedFiles(File f) {
		printer.println(System.currentTimeMillis() + " file: " + f.getName());
		printer.flush();
	}
	
	//Print opened file name to log file
	public void logUserSelection(int sel) {
		printer.println(System.currentTimeMillis() + " selection: " + sel);
		printer.flush();
	}
	
	//Print ZIP code for steps 3, 4, or 5
	public void logUserZip(String zip) {
		printer.println(System.currentTimeMillis() + " ZIP Code: " + zip);
		printer.flush();
	}
}

