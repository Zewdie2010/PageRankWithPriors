package org.wanghao;

import java.util.HashMap;

/**
 * 
 * @author wanghao
 * @date 3.13
 * 
 *       Get the paper collection
 */

public class PaperCollection {

	public HashMap<String, Double> type_CiteSum;
	public HashMap<String, Double> type_PaperSum;
	public HashMap<String, Double> Paper_Cite;
	public HashMap<String, Paper> Name_Paper;
	public HashMap<String, Integer> AuthorName_ID;
	public HashMap<Integer, String> ID_AuthorName;

	// ****** public Func<String,bool> IsType
	// ****** public Func<bool,string,string> GetType;

	// PaperCollection(Func<String,bool> isType,Func<bool,String,String>
	// getType,String paperCiteFIle,String dblpFile)

	// init
	public PaperCollection(String paperCiteFile, String dblpFile) {

	}

	// generate network to the file "network.txt"
	public void GenerateNetwork(String network) {

	}

	// get the author info of the paper
	public void getAuthorInfoForPaper(AuthorCollection AC) {

	}

	// get the edge and the weight
	private void GetEdgeAndWeight(Paper paper, HashMap<Integer, HashMap<Integer, Double>> ID_NeighborWeight) {

	}

	// get the paper imfactor
	private double GetImfactor(Paper paper) {
		// the same imfactor for all the paper
		return 3;
	}

	// read the paper cite file,to get the paper citation
	private void ReadPaperCiteFile(String paperCiteFile) {

	}

	// read the aimedPaper.txt
	private void ReadDBLPFile(String dblpfile) {

	}

	public static void main(String[] args) {

		System.out.println("This is the paper collection class");
	}

}
