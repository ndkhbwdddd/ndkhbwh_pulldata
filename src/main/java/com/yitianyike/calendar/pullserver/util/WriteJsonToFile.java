package com.yitianyike.calendar.pullserver.util;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJsonToFile {

	public static void writeJsonToFile(String json, String filePath) {
		FileWriter writer;
		try {
			writer = new FileWriter(filePath);
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
