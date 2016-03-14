package org.wanghao;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import Jama.Matrix;

public class test {

	public static void main(String[] args) {

		System.out.println("This is the test Class");

		System.out.println("************ Test for the file exist");
		try {
			File file = new File("src/main/resources/Data/AimedPaper.txt");
			System.out.println(file.exists());
			String line = "";
			// while ((line = bufferreader.readLine()) != null) {
			// System.out.println(line);
			// }
		} catch (Exception e) {
			System.out.println("the buffer reader wrong");
		}

		System.out.println("*************** Test for the random class");
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println("The i = " + i + random.nextDouble());
		}

		System.out.println("*************** Test for the linkedList order");
		LinkedList<String> link = new LinkedList<String>();
		link.add("0");
		link.add("1");
		link.add("2");
		link.add("3");
		link.add("4");
		System.out.println("The LinkedList size is " + link.size());
		for (int i = 0; i < link.size(); i++)
			System.out.println(link.get(i));

		System.out.println("************** Test for the Jama class");
		double[][] array = { { 1., 2., 3 }, { 4., 5., 6. }, { 7., 8., 10. } };
		Matrix A = new Matrix(array);
		A.print(4, 1);
		Matrix b = Matrix.random(3, 1);
		Matrix x = A.solve(b);
		double[][] result = x.getArray();
	}
}