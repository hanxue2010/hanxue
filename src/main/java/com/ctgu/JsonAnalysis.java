package com.ctgu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.sf.json.JSONObject;

public class JsonAnalysis {
	void jsonAnalysis() throws IOException {
		File file = new File("src/main/resources/fil.json");
		//FileInputStream fileInputStream = new FileInputStream(file);
		//BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);		
		String s = null;
		String s2;
		s2=bufferedReader.readLine();
			s+=s2;
		
			JSONObject jso = new JSONObject();
			
		
		System.out.println(s);
	}
}
