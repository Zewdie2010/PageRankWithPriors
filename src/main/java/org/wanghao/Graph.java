package org.wanghao;

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

	public Graph() {

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
	}
}
