package org.wanghao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 
 * @author wanghao
 *
 *         {@link HashMap}
 */

/*
 * HashMap 是值传递
 */
public class TestForHashMap {

	public HashMap<String, Integer> testhashMap;

	public TestForHashMap() {
		this.testhashMap = new HashMap<String, Integer>();
		this.testhashMap.put("1", 1);
		this.testhashMap.put("300", 300);
		this.testhashMap.put("2", 2);
	}

	public void addHashMap(HashMap<String, Integer> map) {
		map.put("3", 3);
	}

	public static void main(String[] args) {
		TestForHashMap t = new TestForHashMap();
		System.out.println("******** Before");
		for (Entry<String, Integer> entry : t.testhashMap.entrySet())
			System.out.println(entry.getKey() + " " + entry.getValue());
		System.out.println("******** After");
		t.addHashMap(t.testhashMap);
		for (Entry<String, Integer> entry : t.testhashMap.entrySet())
			System.out.println(entry.getKey() + " " + entry.getValue());

		// buffer writer
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/wanghao/test.txt")));
			writer.write("1" + "\t" + "1");
			writer.newLine();
			writer.write("2" + "\t" + "2");
			writer.newLine();
			writer.write("3" + "\t" + "3");
			writer.write("4" + "\t" + "4");
			writer.close();
		} catch (Exception e) {
			System.err.println("BufferWriter wrong");
		}
	}
}
