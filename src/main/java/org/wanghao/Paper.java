package org.wanghao;

import java.util.HashSet;
import java.util.LinkedList;

public class Paper {

	// decription: the Paper decription
	// title: the paper title
	// year : the paper year
	// conf: the paper's conf
	// domain: the paper's domain
	// imfactor: the paper imfactor
	// citation: the paper's citation
	// authorNamelist: the paper's authors name
	// auhtorSets: the paper's author

	public String decription;
	public String title;
	public int year;
	public String conf;
	public String domain;
	public double imfactor;
	public int citation;
	public LinkedList<String> authorNameList;
	public HashSet<Author> authorSets;

	public Paper(String descriptionLine) {

		authorNameList = new LinkedList<String>();
		authorSets = new HashSet<Author>();
		this.decription = descriptionLine;

		String[] lineList = descriptionLine.split("\n");
		for (int i = 0; i < lineList.length; i++) {

			String line = lineList[i];

			if (line.startsWith("<inproceedings") || line.startsWith("<articles")) {
				this.conf = Domain.GetTypeOfConfOrJour(true, line);
				this.domain = Domain.GetDomainOfConfOrJour(this.conf);
			} else if (line.startsWith("<title")) {

				this.title = null;
			} else if (line.startsWith("year")) {

				this.year = 0;
			} else if (line.startsWith("<author")) {

				this.citation = 1;
			}

		}

		this.citation = 1;
		this.imfactor = 3;

	}

	public void addAuthor(Author author) {

		if (!this.authorSets.contains(author))
			this.authorSets.add(author);
	}

	public static void main(String[] args) {

		System.out.println("This is the Paper class");
	}

}
