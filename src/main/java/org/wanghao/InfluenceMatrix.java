package org.wanghao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 
 * @author wanghao
 * @date 3.14
 * 
 *       Get the Influence .
 */

public class InfluenceMatrix {

	// Id_PInfluenceVector : each node id -> id influence Vector
	// Id_Omega : id -> Omega
	// g : the network structure
	// lambaVector : each node -> different lamba
	// IteratorsTime : the nums of iterator
	public HashMap<Integer, LinkedList<Double>> Id_PInfluenceVector;
	public HashMap<Integer, Double> Id_Omega;
	public Graph g;
	public LinkedList<Double> lambaVector;
	public static final int IteratorsTimes = 30;

	public InfluenceMatrix(Graph graph, LinkedList<Double> lambdaVector) {

		this.Id_PInfluenceVector = new HashMap<Integer, LinkedList<Double>>();
		this.Id_Omega = new HashMap<Integer, Double>();
		this.g = graph;
		this.lambaVector = lambdaVector;
	}

	// get the potential of the authors where the author'item in domain is 1 in
	// et
	public LinkedList<Double> GetPotential(LinkedList<Boolean> Et) {

		LinkedList<Double> Potential = new LinkedList<Double>();

		// init the potential of the each node
		for (int i = 0; i < this.g.amountVex; i++) {
			Potential.add(0.0);
		}

		int iteration = 0;
		// can be modified
		while (iteration < IteratorsTimes) {
			for (int j = 0; j < this.g.amountVex; j++) {

				if (Et.get(j)) {

				} else {

				}
			}
			iteration++;
		}

		return Potential;
	}

	public double GetAuthority(int node, LinkedList<Boolean> Et) {
		return 0.0;
	}

	// get the authority of the nodes in Sprime, where the priors are in
	// thetaVector and the Et is target node where the item is 1
	public double GetSetAuthoriry(HashSet<Integer> Sprime, LinkedList<Boolean> Et, LinkedList<Double> thetaVector) {
		return 0.0;
	}

	public void ComputePInfluenceVector(int node) {

	}

	private double GetFluenceFromOutNeighbor(LinkedList<Double> potential, int node) {

		return 0.0;
	}

	private double GetFluenceFromInNeighbor(LinkedList<Double> pInfluenceVector, int node) {
		return 0.0;
	}

	public static void main(String[] args) {
		System.out.println("********* This is the InfluenceMatrix class");
	}

}
