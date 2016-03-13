package org.wanghao;

import java.util.HashMap;
import java.util.LinkedList;

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

	}

	// get the paper infomation for all the author
	public void GetPaperInfoForEachAuthor(PaperCollection PC) {

	}

	// get the number of co-authored paper numbers
	public int GetNumberOfCoAuthoredPapers(LinkedList<String> oneAuthorSet) {
		return 0;
	}

	// get the number of the co-neighbors
	public int GetNumberOfCoNeighbors(LinkedList<String> OneAuthorSet) {

		return 0;
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

		return 0;
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

	}

	public static void main(String[] args) {
		System.out.println("This is the Author Collection class");
	}

}
