package org.wanghao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 
 * @author wanghao
 * @date 3.13
 * 
 *       the Graph class
 */

public class Graph {

	public LinkedList<Vertex> vertexList;
	public int amountVex;
	public int amountEdge;
	public double totalWeight;
	public HashMap<Integer, Double> Id_OutWeight;
	public HashMap<Integer, Double> Id_InWeight;

	public LinkedList<Double> PageRankValue;

	public String graphFilename;
	Random random;
	public double Error = 0.000000001;

	public Graph(String graphfile, String nodeNamefile) {

		this.amountEdge = 0;
		this.amountVex = 0;
		this.totalWeight = 0;
		this.vertexList = new LinkedList<Vertex>();
		this.Id_InWeight = new HashMap<Integer, Double>();
		this.Id_OutWeight = new HashMap<Integer, Double>();
		this.PageRankValue = new LinkedList<Double>();
		this.random = new Random();
		this.graphFilename = graphfile;

		readGraph();
		GetUserName(nodeNamefile);

	}

	public Graph() {
		this.amountVex = 0;
		this.amountEdge = 0;
		this.totalWeight = 0;
		this.vertexList = new LinkedList<Vertex>();
		this.Id_OutWeight = new HashMap<Integer, Double>();
		this.Id_InWeight = new HashMap<Integer, Double>();
		random = new Random();
		this.PageRankValue = new LinkedList<Double>();
	}

	// get the author name from the "list.txt"
	public void GetUserName(String filename) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\t");
				if (tokens.length == 2) {
					int id = Integer.valueOf(tokens[1]);
					this.vertexList.get(id).name = tokens[0];
				}
			}
			reader.close();
			System.out.println("********** Get the user name Over !");
		} catch (Exception e) {
			System.err.println("Read the list.txt wrong");
		}
	}

	// remove the edge from A to B
	public void RemoveEdge(int idA, int idB) {

		if (!this.vertexList.get(idA).edges.containsKey(idB) || !this.vertexList.get(idB).redges.containsKey(idA))
			System.err.println("This Edge from " + idA + "to" + idB + "does not exist");

		if (this.vertexList.get(idB).edges.containsKey(idA) || this.vertexList.get(idA).redges.containsKey(idB))
			System.err.println("This Edge from " + idB + "to" + idA + "does not exist");

		double weight = this.vertexList.get(idA).edges.get(idB).weight;

		// remove idA->idB edge
		this.vertexList.get(idA).outdegree--;
		this.vertexList.get(idA).outweight -= weight;
		this.vertexList.get(idB).indegree--;
		this.vertexList.get(idB).inweight -= weight;

		this.vertexList.get(idA).edges.remove(idB);
		this.vertexList.get(idB).redges.remove(idA);

		// remove idB->idA edge
		this.vertexList.get(idB).outdegree--;
		this.vertexList.get(idB).outweight -= weight;
		this.vertexList.get(idA).indegree--;
		this.vertexList.get(idA).inweight -= weight;

		this.vertexList.get(idB).edges.remove(idA);
		this.vertexList.get(idA).redges.remove(idB);

	}

	// clone the graph
	@Override
	public Graph clone() {

		return null;
	}

	// create the edge from A to B
	private void CreateEdge(int idA, int idB, double weight) {

		this.vertexList.get(idA).outdegree++;
		this.vertexList.get(idA).outweight += weight;
		this.vertexList.get(idB).indegree++;
		this.vertexList.get(idB).inweight += weight;

		double tranP = 0.1;
		// out edge
		if (this.vertexList.get(idA).edges.containsKey(idB)) {
			this.vertexList.get(idA).edges.get(idB).weight += weight;
		} else {
			this.vertexList.get(idA).edges.put(idB, new Edge(idB, weight, tranP));
		}
		// in edge
		if (this.vertexList.get(idB).redges.containsKey(idA)) {
			this.vertexList.get(idB).redges.get(idA).weight += weight;
		} else {
			this.vertexList.get(idB).redges.put(idA, new Edge(idA, weight, tranP));
		}

	}

	// read the graph from the graphfile
	private void readGraph() {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(this.graphFilename)));
			String line = "";
			// LinkedList<LinkedList<String>> lineList = new
			// Linke/dList<LinkedList<String>>();
			int MaxId = 0;

			// ******************* 1. find the max Id vertex
			while ((line = reader.readLine()) != null) {

				String[] tokens = line.split("\t");

				if (tokens.length < 2 || tokens.length > 3)
					continue;

				int IdA = Integer.valueOf(tokens[0]);
				int IdB = Integer.valueOf(tokens[1]);

				if (MaxId < IdA)
					MaxId = IdA;
				if (MaxId < IdB)
					MaxId = IdB;
			}
			reader.close();
			System.out.println("************* Find the Max Id -> " + MaxId);

			// ********************** 2. create the graph vertex
			for (int i = 0; i <= MaxId; i++) {
				this.vertexList.add(new Vertex());
			}
			System.out.println("************* Create the vertex over");

			// ********************** 3. create the graph edge
			// also create the graph
			// read the file again
			BufferedReader reader2 = new BufferedReader(new FileReader(new File(this.graphFilename)));
			while ((line = reader2.readLine()) != null) {

				String[] tokens = line.split("\t");
				if (tokens.length < 2 || tokens.length > 3)
					continue;

				int idA = Integer.valueOf(tokens[0]);
				int idB = Integer.valueOf(tokens[1]);
				double weight;
				if (tokens.length == 3)
					weight = Double.valueOf(tokens[2]);
				else
					weight = 1;

				CreateEdge(idA, idB, weight);
				CreateEdge(idB, idA, weight);

				this.amountEdge++;
			}
			reader2.close();
			this.amountVex = this.vertexList.size();
			System.out.println("*******************Create the edge over");

			// *****4. get the transmission probability between the vertex
			// P(idA->idB) = EdgeWeight(idA->idB)/idB.inWeight
			for (int i = 0; i < this.amountVex; i++) {
				for (Entry<Integer, Edge> entry : this.vertexList.get(i).edges.entrySet()) {
					entry.getValue().tranP = entry.getValue().weight / this.vertexList.get(entry.getKey()).inweight;
					this.vertexList.get(entry.getKey()).redges.get(i).tranP = entry.getValue().tranP;
				}
			}
			System.out.println("*********** Get the tranPosibility Over");
			// ********5 . count the vertices and edges
			for (int i = 0; i < this.amountVex; i++) {
				this.Id_InWeight.put(i, this.vertexList.get(i).inweight);
				this.Id_OutWeight.put(i, this.vertexList.get(i).outweight);
				if (this.vertexList.get(i).edges.size() != 0)
					this.totalWeight += this.vertexList.get(i).outweight;
			}
			System.out.println("************ Count the vertices and edges Over!");

		} catch (Exception e) {
			System.out.println("Network IO Exception");
		}
	}

	/************************************************************************
	 * Vertex of graph class
	 * 
	 */
	public class Vertex {

		public int outdegree;
		public int indegree;
		public double outweight;
		public double inweight;
		public String name;
		public HashMap<Integer, Edge> edges;
		public HashMap<Integer, Edge> redges;

		public Vertex() {
			this.outdegree = 0;
			this.indegree = 0;
			this.outweight = 0.0;
			this.inweight = 0.0;
			this.name = "";
			edges = new HashMap<Integer, Edge>();
			redges = new HashMap<Integer, Edge>();
		}

		public Vertex(int outdegree, int indegree, double outweight, double inweight) {
			this.outdegree = outdegree;
			this.indegree = indegree;
			this.outweight = outweight;
			this.inweight = inweight;
			this.name = "";
			edges = new HashMap<Integer, Edge>();
			redges = new HashMap<Integer, Edge>();
		}

		// clone the vertex
		@Override
		public Vertex clone() {

			Vertex v = new Vertex();
			v.outdegree = this.outdegree;
			v.indegree = this.indegree;
			v.outweight = this.outweight;
			v.inweight = this.inweight;
			v.name = this.name;

			v.edges = new HashMap<Integer, Edge>();
			for (Entry<Integer, Edge> entry : this.edges.entrySet())
				v.edges.put(entry.getKey(), entry.getValue());

			v.redges = new HashMap<Integer, Edge>();
			for (Entry<Integer, Edge> entry : this.redges.entrySet())
				v.redges.put(entry.getKey(), entry.getValue());

			return v;
		}

	}

	/*******************************************************************
	 * Edge of graph class
	 * 
	 */
	public class Edge {

		public int id;
		public double weight;
		public double tranP;

		public Edge() {

			this.id = 0;
			this.weight = 0;
		}

		public Edge(int ID, double weight, double transmissionProbability) {

			this.id = ID;
			this.weight = weight;
			this.tranP = transmissionProbability;
		}

		@Override
		public Edge clone() {

			Edge e = new Edge();
			e.id = this.id;
			e.weight = this.weight;
			e.tranP = this.tranP;

			return e;
		}
	}

	/**
	 * Queue Node Node Queue class
	 */
	public class QueueNode {

		public int id;
		public int Level;

		public QueueNode(int id, int level) {
			this.id = id;
			this.Level = level;
		}
	}

	public static void main(String[] args) {
		System.out.println("This is the Graph");
		System.out.println("***************Test for the graph class ******************************");
		try {
			String graphfile = "/home/wanghao/Data/All/Network.txt";
			String NodeNameFile = "/home/wanghao/Data/All/list.txt";
			Graph g = new Graph(graphfile, NodeNameFile);

			System.out.println("************Read Over*************");

		} catch (Exception e) {
			System.out.println("Graph class read file error!");
		}

	}
}
