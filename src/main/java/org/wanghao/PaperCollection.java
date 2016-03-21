package org.wanghao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;

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

		this.Name_Paper = new HashMap<String, Paper>();
		this.Paper_Cite = new HashMap<String, Double>();
		this.type_CiteSum = new HashMap<String, Double>();
		this.type_PaperSum = new HashMap<String, Double>();
		this.AuthorName_ID = new HashMap<String, Integer>();

		ReadPaperCiteFile(paperCiteFile); // paperCite.txt
		ReadDBLPFile(dblpFile);// AimedPaper.txt
	}

	// generate network to the file "network.txt"
	public void GenerateNetwork(String networkfile) {

		HashMap<Integer, HashMap<Integer, Double>> ID_NeighborWeight = new HashMap<Integer, HashMap<Integer, Double>>();
		int id = 0;

		for (Entry<String, Paper> entry : this.Name_Paper.entrySet()) {

			try {
				Paper paper = entry.getValue();
				for (String authorname : paper.authorNameList) {
					if (!this.AuthorName_ID.containsKey(authorname)) {
						this.AuthorName_ID.put(authorname, id);
						this.ID_AuthorName.put(id++, authorname);
					}
				}
				GetEdgeAndWeight(paper, ID_NeighborWeight);
			} catch (Exception e) {
				continue;
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(networkfile)));
			for (Entry<Integer, HashMap<Integer, Double>> entry : ID_NeighborWeight.entrySet()) {
				for (Entry<Integer, Double> subentry : entry.getValue().entrySet()) {
					writer.write(entry.getKey() + "\t" + subentry.getKey() + "\t" + subentry.getValue());
					writer.newLine();
				}
			}
			writer.close();

			//
			BufferedWriter listWriter = new BufferedWriter(new FileWriter(new File("/home/wanghao/new/list.txt")));
			for (Entry<String, Integer> entry : this.AuthorName_ID.entrySet()) {
				listWriter.write(entry.getKey() + "\t" + entry.getValue());
				listWriter.newLine();
			}
			listWriter.close();
		} catch (Exception e) {
			System.err.println("Write the network file Wrong");
		}

	}

	// get the author info of the paper
	public void getAuthorInfoForPaper(AuthorCollection AC) {

		for (Entry<String, Paper> entry : this.Name_Paper.entrySet()) {
			Paper paper = entry.getValue();
			for (String name : paper.authorNameList) {
				try {
					Author author = AC.Name_Author.get(name);
					paper.addAuthor(author);
				} catch (Exception e) {
					continue;
				}
			}
		}

	}

	// get the edge and the weight
	private void GetEdgeAndWeight(Paper paper, HashMap<Integer, HashMap<Integer, Double>> ID_NeighborWeight) {

		for (int i = 0; i < paper.authorNameList.size(); i++) {
			String name = paper.authorNameList.get(i);
			int IdA = this.AuthorName_ID.get(name);

			if (!ID_NeighborWeight.containsKey(IdA))
				ID_NeighborWeight.put(IdA, new HashMap<Integer, Double>());

			for (int j = i + 1; j < paper.authorNameList.size(); j++) {
				name = paper.authorNameList.get(j);
				int IdB = this.AuthorName_ID.get(name);
				if (!ID_NeighborWeight.containsKey(IdB))
					ID_NeighborWeight.get(IdA).put(IdB, 0.0);
				double newWeight = ID_NeighborWeight.get(IdA).get(IdB)
						+ 2 * paper.imfactor / (paper.authorNameList.size() * (paper.authorNameList.size() - 1));
				ID_NeighborWeight.get(IdA).replace(IdB, newWeight);
			}

		}

	}

	// get the paper imfactor
	private double GetImfactor(Paper paper) {
		// the same imfactor for all the paper
		return 3;
	}

	// read the paper cite file,to get the paper citation
	private void ReadPaperCiteFile(String paperCiteFile) {

		try {
			BufferedReader bufferReader = new BufferedReader(
					new FileReader(new File("/home/wanghao/Data/All/paperCite.txt")));
			String line = "";
			String paper = "";
			while ((line = bufferReader.readLine()) != null) {
				String[] tokens = line.split("\t");
				paper = tokens[0].toLowerCase().trim();
				if (paper.charAt(paper.length() - 1) == '.')
					paper = paper.substring(0, paper.length() - 1);
				Double cite = Double.valueOf(tokens[1]);
				if (!this.Paper_Cite.containsKey(paper))
					this.Paper_Cite.put(paper, cite);
			}
		} catch (Exception e) {

			System.out.println("Reader the paper cite file Wrong");
		}

	}

	// read the aimedPaper.txt
	private void ReadDBLPFile(String dblpfile) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(dblpfile)));
			String line = "";
			String paperDescripLine = "";
			boolean confFlag = false;

			while ((line = reader.readLine()) != null) {

				if ((line.startsWith("<inproceedings") || line.startsWith("<article"))
						&& Domain.IsAimedConfOrJour(line)) {
					confFlag = true;
					paperDescripLine = "";
				}
				if (confFlag == true)
					paperDescripLine += line + "\n";
				if (line.equals("</inproceedings>") || line.equals("</article>")) {
					if (confFlag) {
						Paper paper = new Paper(paperDescripLine);
						this.Name_Paper.put(paper.title, paper);
						confFlag = false;
					}
				}
			}

			for (Entry<String, Paper> entry : this.Name_Paper.entrySet()) {

				Paper paper = entry.getValue();
				String type = paper.conf;
				if (!this.type_PaperSum.containsKey(type))
					this.type_PaperSum.put(type, 0.0);
				double oldsum = this.type_PaperSum.get(type);
				this.type_PaperSum.replace(type, oldsum + 1);

				if (!this.type_CiteSum.containsKey(type))
					this.type_CiteSum.put(type, 0.0);
				double newCitesum = this.type_CiteSum.get(type) + this.Paper_Cite.get(paper.title);
				this.type_CiteSum.replace(type, newCitesum);

			}

			for (Entry<String, Paper> entry : this.Name_Paper.entrySet()) {
				try {
					entry.getValue().imfactor = GetImfactor(entry.getValue());
					double temp = this.Paper_Cite.get(entry.getKey());
					entry.getValue().citation = (int) temp;
				} catch (Exception e) {
					entry.getValue().imfactor = 3;
					entry.getValue().citation = 1;
				}
			}
		} catch (Exception e) {
			System.err.println("read the db lp file wrong");
		}
	}

	public static void main(String[] args) {

		System.out.println("This is the paper collection class");
		try {
			BufferedReader bufferReader = new BufferedReader(
					new FileReader(new File("/home/wanghao/Data/All/paperCite.txt")));
			String line = "";
			String paper = "";
			int count = 0;
			while ((line = bufferReader.readLine()) != null && count < 10) {
				String[] tokens = line.split("\t");
				paper = tokens[0].toLowerCase().trim();
				if (paper.charAt(paper.length() - 1) == '.')
					paper = paper.substring(0, paper.length() - 1);
				count++;
				System.out.println(paper);
			}
		} catch (Exception e) {
			System.err.println("The wrong read the paper cite ");
		}

	}

}
