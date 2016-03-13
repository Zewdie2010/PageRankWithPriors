package org.wanghao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 
 * @author wanghao
 * @date 3.11
 * 
 *       Author Class
 */

public class Author {

	// authorName: the name of author
	// position: the author position,eg:Professor
	// phone: Phone
	// homePage: url of the author
	// Hindex: author hindex
	public String authorName;
	public String position;
	public String phone;
	public String homePage;
	public int Hindex;

	// Domain_Weight: different domain of the author has the different weight
	// Domain_CollaboratorNum: different domain of the author has the different
	// collaborator nums
	// CollaboratorName_Weight: the weight of the collaborator person
	// Conf_Count: the nums of the author's paper
	// PaperName_Citation: the author's paper citation
	// NeighboorDomain_Weight: neighboor's different domain weight
	public HashMap<String, Double> Domain_Weight;
	public HashMap<String, Double> Domain_CollaboratorNum;
	public HashMap<String, Double> CollaboratorName_Weight;
	public HashMap<String, Integer> Conf_Count;
	public HashMap<String, Integer> PaperName_Citation;
	public HashMap<String, Double> NeighboorDomain_Weight;

	// totalWeight: the author the total weight
	// totalNeighboorWeight； Neighboor weight
	// numofCollaborators： the nums of the collaborators
	// authorPapers：the paper of the author
	public double totalWeight;
	public double totalNeighboorWeight;
	public int numofCollaborators;
	public HashSet<Paper> authorPapers;

	public Author(String descriptline) {

		Domain_Weight = new HashMap<String, Double>();
		Domain_CollaboratorNum = new HashMap<String, Double>();
		CollaboratorName_Weight = new HashMap<String, Double>();
		Conf_Count = new HashMap<String, Integer>();
		PaperName_Citation = new HashMap<String, Integer>();
		NeighboorDomain_Weight = new HashMap<String, Double>();
		authorPapers = new HashSet<Paper>();

		String[] splitArrays = descriptline.split("\t");
		this.authorName = splitArrays[0];
		this.position = splitArrays[1].toLowerCase();
		this.phone = splitArrays[2];
		this.homePage = splitArrays[3];
		this.Hindex = Integer.parseInt(splitArrays[4]);

		this.totalWeight = 0;
		this.totalNeighboorWeight = 0;
		this.numofCollaborators = 0;
	}

	/*
	 * add the paper to the author
	 */
	public void addPaper(Paper paper) {

		authorPapers.add(paper);
		if (!this.Domain_Weight.containsKey(paper.domain))
			this.Domain_Weight.put(paper.domain, 0.0);

		double subImfactor = 0;
		if (paper.authorNameList.size() == 1)
			subImfactor = 0;
		else
			subImfactor = 2 * paper.imfactor * (paper.authorNameList.size() * (paper.authorNameList.size() - 1));

		// add the domain weight and the total weight
		Double newDomainWeight = this.Domain_Weight.get(paper.domain) + subImfactor * (paper.authorNameList.size() - 1);
		this.Domain_Weight.replace(paper.domain, newDomainWeight);
		this.totalWeight += subImfactor * (paper.authorNameList.size() - 1);

		for (String authorname : paper.authorNameList) {

			if (authorname.equals(this.authorName))
				continue;
			if (!this.CollaboratorName_Weight.containsKey(authorname))
				this.CollaboratorName_Weight.put(authorname, 0.0);

			Double newCollaboratorNameWeight = this.CollaboratorName_Weight.get(authorname) + subImfactor;
			this.CollaboratorName_Weight.replace(authorname, newCollaboratorNameWeight);
		}

		// add the conf paper count
		if (!this.Conf_Count.containsKey(paper.conf))
			this.Conf_Count.put(paper.conf, 0);
		int oldNums = this.Conf_Count.get(paper.conf);
		this.Conf_Count.replace(paper.conf, oldNums++);

		// add the paper citation
		this.PaperName_Citation.put(paper.title, paper.citation);
	}

	/*
	 * get the conf weight
	 */
	public double GetConfWeight(String conf) {

		double weight = 0;
		for (Paper paper : authorPapers) {
			if (paper.conf.equals(conf))
				if (paper.authorNameList.size() != 1)
					weight += paper.imfactor / (0.5 * paper.authorNameList.size() * (paper.authorNameList.size() - 1));
		}

		return this.totalWeight == 0 ? 0 : (weight / this.totalWeight);
	}

	// get the author domain weight
	public double GetDomainWeight(String domain) {

		if (domain.equals("Total"))
			return this.totalWeight;
		if (this.Domain_Weight.containsKey(domain))
			return this.Domain_Weight.get(domain);

		return 0;
	}

	// according to the level get the weight
	public double GetSubSWeight(String domain, int level) {

		if (level == 0)
			return GetDomainWeight(domain);
		if (level == 1)
			return GetConfWeight(domain);

		return 0;
	}

	// get the author neighboor domain weight
	public double GetNeighboorDomainWeight(String domain) {

		if (domain.equals("Total"))
			return this.totalWeight;
		if (this.NeighboorDomain_Weight.containsKey(domain))
			return this.NeighboorDomain_Weight.get(domain);

		return 0;
	}

	// get the numbers of the collaboraters
	public double GetNumOfCollaboraters() {

		this.numofCollaborators = this.CollaboratorName_Weight.size();

		return this.numofCollaborators;
	}

	public static void main(String[] args) {

		System.out.println("Author");

		String testString = "Jochem Vonk			http://is.tm.tue.nl/staff/jvonk/	7";
		String[] splitArray = testString.split("\t");
		int i = 0;
		for (String s : splitArray)
			System.out.println(i++ + "  " + s);

		System.out.println("The Result Is".toLowerCase());

		HashSet<String> sets = new HashSet<String>();
		sets.add("first");
		sets.add("Second");
		sets.add("Three");
		for (String s : sets)
			System.out.println("The sets the element is  " + s);

		LinkedList<String> linkList = new LinkedList<String>();
		linkList.add("1");
		linkList.add("2");
		System.out.println("The Linked List the length is " + linkList.size());

		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		hashmap.put("1", 1);
		hashmap.replace("1", 3);
		System.out.println(hashmap.get("1"));
	}

}
