package org.wanghao;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

public class test {

	public static void main(String[] args) {

		System.out.println("This is the test Class");
		try {
			File file = new File("src/main/resources/Data/AimedPaper.txt");
			System.out.println(file.exists());
			String line = "";
			// while ((line = bufferreader.readLine()) != null) {
			// System.out.println(line);
			// }
		} catch (

		Exception e)

		{
			System.out.println("the buffer reader wrong");
		}

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println("The i = " + i + random.nextDouble());
		}

		System.out.println("***************");
		LinkedList<String> link = new LinkedList<String>();
		link.add("0");
		link.add("1");
		link.add("2");
		link.add("3");
		link.add("4");
		for (int i = 0; i < link.size(); i++)
			System.out.println(link.get(i));
	}

}
