package org.wanghao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class test {

	public static void main(String[] args) {

		System.out.println("This is the test Class");
		try {
			File file = new File("src/main/resources/Data/AimedPaper.txt");
			System.out.println(file.exists());
			BufferedReader bufferreader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = bufferreader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println("the buffer reader wrong");
		}
	}

}
