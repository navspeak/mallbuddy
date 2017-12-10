package com.nav.csv.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class CSVUtil {
	private static Logger logger = Logger.getLogger(CSVUtil.class); 
	/**
	 * Checks if a string is valid file path
	 * @param path
	 * @return absolute file path if the input is valid
	 *         else return null
	 */
	public static String checkPath(String path){
		if (path == null) return null;
		try {
			File file = new File(path);
			if (file.exists() == true) {
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			logger.warn(path + "doesn't exist. Moving on...");
			// return null
		}
		return null;
	}
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> readFromCSV(String fileName) throws IOException{
		Path filePath = Paths.get(fileName);
		List<String[]> elements = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(filePath)) {
			String line = "";
			while ((line = reader.readLine()) != null){
				elements.add(line.split(","));
			}
		}
		return elements;
	}
	
}
