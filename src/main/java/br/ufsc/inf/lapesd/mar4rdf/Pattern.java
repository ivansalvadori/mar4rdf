package br.ufsc.inf.lapesd.mar4rdf;

import java.util.List;

import org.apache.jena.rdf.model.Property;

public class Pattern {

	private List<Property> listOfProperties;
	private int numberOfTransactions;

	public Pattern(List<Property> listOfProperties, int numberOfTransactions) {
		super();
		this.listOfProperties = listOfProperties;
		this.numberOfTransactions = numberOfTransactions;
	}

	public List<Property> getListOfProperties() {
		return listOfProperties;
	}

	public int getNumberOfTransactions() {
		return numberOfTransactions;
	}

	@Override
	public String toString() {
		String PaternFormat = "%s -> %s (%s transactions)";
		return String.format(PaternFormat, getPropertyURI(), listOfProperties, numberOfTransactions);

	}

	public String getPropertyURI() {
		String propertyName = StringUtils.createPropertyName(this.getListOfProperties());
		return propertyName;
	}

	public String getTypeURI() {
		String uri = StringUtils.createTypeURI(this.getListOfProperties());
		return uri;
	}

}
