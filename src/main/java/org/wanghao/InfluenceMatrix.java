package org.wanghao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.wanghao.Graph.Edge;

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
		// can be modified, computed by the gassie-saider
		while (iteration < IteratorsTimes) {
			for (int j = 0; j < this.g.amountVex; j++) {
				if (Et.get(j)) {
					Potential.set(j, 1 / (1 + this.lambaVector.get(j)) * (1 + GetFluenceFromOutNeighbor(Potential, j)));
				} else {
					Potential.set(j, 1 / (1 + this.lambaVector.get(j)) * (0 + GetFluenceFromInNeighbor(Potential, j)));
				}
			}
			iteration++;
		}

		return Potential;
	}

	public double GetAuthority(int node, LinkedList<Boolean> Et) {

		if (!this.Id_PInfluenceVector.containsKey(node))
			ComputePInfluenceVector(node);

		LinkedList<Double> Pi = this.Id_PInfluenceVector.get(node);

		double authority = 0.0;
		for (int i = 0; i < Pi.size(); i++) {
			if (Et.get(i))
				authority += Pi.get(i) / Pi.get(node);
		}

		return authority;
	}

	// get the authority of the nodes in Sprime, where the priors are in
	// thetaVector and the Et is target node where the item is 1
	public double GetSetAuthoriry(HashSet<Integer> Sprime, LinkedList<Boolean> Et, LinkedList<Double> thetaVector) {

		return 0.0;
	}

	// get the Influence vector of node i -> eg: pi
	public void ComputePInfluenceVector(int node) {

		LinkedList<Double> PInfluenceVector = new LinkedList<Double>();
		for (int i = 0; i < this.g.amountVex; i++)
			PInfluenceVector.add(0.0);

		int iter = 0;
		while (iter < IteratorsTimes) {

			for (int j = 0; j < this.g.amountVex; j++) {
				PInfluenceVector.set(j, 1 / (1 + lambaVector.get(j))
						* (j == node ? 1 : 0 + GetFluenceFromInNeighbor(PInfluenceVector, j)));
			}
			iter++;
		}

		this.Id_PInfluenceVector.put(node, PInfluenceVector);

		double omega = 0;
		for (int i = 0; i < PInfluenceVector.size(); i++) {
			omega += PInfluenceVector.get(i);
		}
		this.Id_Omega.put(node, omega);
	}

	// get the influence from the out neighbor
	private double GetFluenceFromOutNeighbor(LinkedList<Double> potential, int node) {

		double result = 0.0;
		Edge edge = null;
		int outneighbor = 0;
		double tranP = 0.0;

		for (Entry<Integer, Edge> entry : this.g.vertexList.get(node).edges.entrySet()) {

			outneighbor = entry.getKey();
			edge = entry.getValue();
			tranP = edge.tranP;
			result += tranP * potential.get(outneighbor);
		}

		return result;
	}

	// for the linear influence model, get the influence from the in neighor
	private double GetFluenceFromInNeighbor(LinkedList<Double> pInfluenceVector, int node) {

		double result = 0.0;
		Edge edge = null;
		int inneighbor = 0;
		double tranP = 0.0;

		for (Entry<Integer, Edge> entry : this.g.vertexList.get(node).redges.entrySet()) {

			inneighbor = entry.getKey();
			edge = entry.getValue();
			tranP = edge.tranP;
			result += tranP * pInfluenceVector.get(inneighbor);
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println("********* This is the InfluenceMatrix class");
	}

}
