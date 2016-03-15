package org.wanghao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

/**
 * 
 * @author wanghao
 * @date 3.11
 * 
 *       Main Function
 */

public class Main {

	public static String filePath = "Data/";
	public static String totalFolder = "All/";

	// function
	// public static Func<String,boolean> isType = Domain.IsAimedConfOrJour;
	// public static Func<boolean,String,String> getType =
	// Domain.GetTypeOfAimedConfOrJour;
	public static AuthorCollection AC;
	public static PaperCollection PC;
	public static Graph g;
	public static int K = 50; // top k numbers
	public static int SeedNum = 10;
	public static int THETA = 100;
	public static int IterMontCarlo = 2000;
	public static int year = 100;

	public double PageRankSearch = 0;
	public double WPageRankSearch = 0;
	public double SameSearch = 0;
	public double PrioriSearch = 0;
	public double RandomSearch = 0;

	public static void main(String[] args) {

		System.out.println("********* Try to create the Graph");
		try {
			String graphfile = "/home/wanghao/Data/All/Network.txt";
			String NodeNameFile = "/home/wanghao/Data/All/list.txt";
			g = new Graph(graphfile, NodeNameFile);
		} catch (Exception e) {
			System.err.println("create the graph error");
		}
		System.out.println("********** Successfully create the Graph");

	}

	/**
	 * Topic Sensitive PageRank
	 * 
	 * @param domain
	 * @param OutputFilePath
	 * @return
	 */
	public static LinkedList<Integer> GetAuthorityPageRank(String domain, String OutputFilePath) {

		LinkedList<Double> lambdaVector = new LinkedList<Double>();
		LinkedList<Double> thetaVector = new LinkedList<Double>();
		LinkedList<Integer> SeedID = new LinkedList<Integer>();
		try {

			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(new File(OutputFilePath)));
			String name = "";
			Author authorA;

			for (int i = 0; i < g.vertexList.size(); i++) {
				lambdaVector.add(0.176);
			}
			InfluenceMatrix IF = new InfluenceMatrix(g, lambdaVector);

			// get the domain user
			HashSet<String> userSet = new HashSet<String>();
			for (Entry<String, Author> entry : AC.Name_Author.entrySet()) {
				if (entry.getValue().GetSubsWeight(domain, 0) != 0)
					userSet.add(entry.getKey());
			}
			// get the domain vector et
			LinkedList<Boolean> Et = new LinkedList<Boolean>();
			for (int i = 0; i < g.vertexList.size(); i++)
				Et.add(false);
			for (String s : userSet) {
				int id = PC.AuthorName_ID.get(s);
				Et.set(id, true);
			}

			// Get the result by the influenceMatrix function
			LinkedList<Double> result = IF.GetPotential(Et);

			HashMap<Integer, Double> ID_Potential = new HashMap<Integer, Double>();
			for (int i = 0; i < result.size(); i++) {
				if (Et.get(i))
					ID_Potential.put(i, result.get(i));
			}
			bufferWriter.write(domain + "\t Users" + userSet.size() + "\n");
			bufferWriter.newLine();

			int SearchNodeCount = 0;
			double totalHindex = 0;
			LinkedList<String> authorNameVector = new LinkedList<String>();
			LinkedList<Double> authorHindexVector = new LinkedList<Double>();
			LinkedList<Double> authorAuthorityVector = new LinkedList<Double>();

			List<Entry<Integer, Double>> SortedPotential = new ArrayList<Entry<Integer, Double>>(
					ID_Potential.entrySet());
			// sort by the value
			Collections.sort(SortedPotential, new Comparator<Entry<Integer, Double>>() {

				public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
					return (int) (o2.getValue() - o1.getValue());
				}
			});

			for (int i = 0; i < SortedPotential.size(); i++) {
				int s = SortedPotential.get(i).getKey();
				SeedID.add(s);
				String nameAuthor = PC.ID_AuthorName.get(s);
				authorA = AC.Name_Author.get(nameAuthor);
				authorNameVector.add(nameAuthor);
				authorHindexVector.add((double) authorA.Hindex);
				authorAuthorityVector.add(SortedPotential.get(i).getValue());
				/**
				 * 
				 */
				if (++SearchNodeCount == K)
					break;
			}

			System.out.println("********** Topic Sensitive PageRank Result " + domain + " : " + "\t");
			double AvgH = 0;
			for (int j = 0; j < K; j++) {
				AvgH += authorHindexVector.get(j);
			}
			System.out.println((AvgH / K) + "\t");
			bufferWriter.close();
		} catch (IOException e) {
			System.err.println("**** create the file bufferedWriter wrong");
			e.printStackTrace();
		}
		return SeedID;
	}
}
