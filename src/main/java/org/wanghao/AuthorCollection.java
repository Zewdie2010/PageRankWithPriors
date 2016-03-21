package org.wanghao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * 
 * @author wanghao
 * @date 3.13
 * 
 *       the author collection class
 */

public class AuthorCollection {

	// val Information
	public HashMap<String, Author> Name_Author;

	public AuthorCollection(String authorDescripfile) {

		Name_Author = new HashMap<String, Author>();

		ReadAuthorDescrip(authorDescripfile); // author.txt
	}

	// get the paper infomation for all the author
	public void GetPaperInfoForEachAuthor(PaperCollection PC) {

		for (Entry<String, Paper> entry : PC.Name_Paper.entrySet()) {
			Paper paper = entry.getValue();
			for (int i = 0; i < paper.authorNameList.size(); i++) {
				try {
					String authorname = paper.authorNameList.get(i);
					Author author = this.Name_Author.get(authorname);
					author.addPaper(paper);
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

	// get the number of co-authored paper numbers
	public int GetNumberOfCoAuthoredPapers(LinkedList<String> oneAuthorSet) {

		int CoAuthor = 0;
		HashMap<String, Integer> Paper_CoAuthor = new HashMap<String, Integer>();
		for (int i = 0; i < oneAuthorSet.size(); i++) {
			Author author = this.Name_Author.get(oneAuthorSet.get(i));
			for (Entry<String, Integer> papercite : author.PaperName_Citation.entrySet()) {
				if (!Paper_CoAuthor.containsKey(papercite.getKey()))
					Paper_CoAuthor.put(papercite.getKey(), 1);
				else {
					int newNums = Paper_CoAuthor.get(papercite.getKey()) + 1;
					Paper_CoAuthor.replace(papercite.getKey(), newNums);
				}
			}
		}

		for (Entry<String, Integer> paper_Coauthor : Paper_CoAuthor.entrySet()) {
			if (paper_Coauthor.getValue() == oneAuthorSet.size())
				CoAuthor++;
		}
		return CoAuthor;
	}

	// get the number of the co-neighbors
	public int GetNumberOfCoNeighbors(LinkedList<String> OneAuthorSet) {

		int coNeighbors = 0;
		HashMap<String, Integer> ID_CoNeighbor = new HashMap<String, Integer>();
		for (int i = 0; i < OneAuthorSet.size(); i++) {
			Author author = this.Name_Author.get(OneAuthorSet.get(i));
			for (Entry<String, Double> neighborNum : author.CollaboratorName_Weight.entrySet()) {
				if (!ID_CoNeighbor.containsKey(neighborNum.getKey()))
					ID_CoNeighbor.put(neighborNum.getKey(), 1);
				else {
					int newNums = ID_CoNeighbor.get(neighborNum.getKey()) + 1;
					ID_CoNeighbor.replace(neighborNum.getKey(), newNums);
				}
			}
		}

		for (Entry<String, Integer> neighorNums : ID_CoNeighbor.entrySet()) {
			if (neighorNums.getValue() == OneAuthorSet.size())
				coNeighbors++;
		}

		return coNeighbors;
	}

	/*********************************************
	 * Get the Hindex Functions
	 * 
	 */
	// get the Hindex of a author
	public int GetHindexOfOneAuthor(String author) {

		return 0;
	}

	// get the Hindex of the author sets
	public int GetHindexOfOneAuthorSets(LinkedList<String> author) {
		return 0;
	}

	// according to the paper citation to get the Hindex
	int GetHindex(HashMap<String, Integer> Paper_Cite) {

		int Hindex = 0;
		for (; Hindex <= Paper_Cite.size(); Hindex++) {
			int count = 0;
			for (Entry<String, Integer> papercite : Paper_Cite.entrySet()) {
				if (papercite.getValue() >= Hindex)
					count++;
				if (count >= Hindex)
					break;
			}
			if (count >= Hindex)
				continue;
			else {
				Hindex = Hindex - 1;
				break;
			}
		}

		return Hindex;
	}

	/*******************
	 * get the neighboorDomain weight and domain collaborator nums
	 * 
	 * @param authorDescripfile
	 */
	// compute each author's SelfSelfNeighboorDomain_Weight and
	// DomainCollaborator_num
	public void computeSelfNeighoorDomainWeightAndNumOfDomainCollaboratorsForAuthor() {

	}

	// read the author description file
	private void ReadAuthorDescrip(String authorDescripfile) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(authorDescripfile)));
			String line = "";
			while ((line = reader.readLine()) != null) {
				Author author = new Author(line);
				this.Name_Author.put(author.authorName, author);
			}
		} catch (Exception e) {
			System.err.println("Read the author description Wrong");
		}
	}

	public static void main(String[] args) {
		System.out.println("This is the Author Collection class");
	}

}
