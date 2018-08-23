package br.ufsc.inf.lapesd.mar4rdf;

import java.util.List;

public class Pattern {

	private List<String> listOfPropertyName;
	private int numberOfTransactions;
	private String longestCommonName;

	public Pattern(List<String> listOfPropertyName, int numberOfTransactions, String longestCommonName) {
		super();
		this.listOfPropertyName = listOfPropertyName;
		this.numberOfTransactions = numberOfTransactions;
		this.longestCommonName = longestCommonName;
	}

	public List<String> getListOfPropertyName() {
		return listOfPropertyName;
	}

	public int getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public String getLongestCommonName() {
		return longestCommonName;
	}

	@Override
	public String toString() {
		return this.longestCommonName + " -> " + listOfPropertyName.toString() + " -> " + numberOfTransactions;
	}

}
