package br.ufsc.inf.lapesd.mar4rdf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

public class VectorGenerator {

	private Map<String, Integer> mapStringNumber = new HashMap<>();
	private Map<Integer, String> mapStringNumberInvertedIndex = new HashMap<>();
	private Integer currentVectorNumber = 0;
	private Map<Integer, Resource> mapVectorIDResource = new HashMap<>();
	private Integer currentVectorID = 0;

	public List<List<Integer>> generate(Model model) throws FileNotFoundException, IOException {
		List<List<Integer>> matrix = new ArrayList<>();

		Set<Property> predicates = this.loadAllPredicates(model);

		List<Resource> listOfSubjects = model.listSubjects().toList();
		for (Resource subject : listOfSubjects) {
			List<Integer> vector = new ArrayList<>();

			for (Property predicate : predicates) {
				Statement stmt = subject.getProperty(predicate);
				RDFNode object = stmt.getObject();
				if (object.isLiteral()) {
					String objectValue = object.asLiteral().toString();
					Integer number = this.mapStringNumber.get(objectValue);
					if (number == null) {
						this.mapStringNumber.put(objectValue, currentVectorNumber);
						this.mapStringNumberInvertedIndex.put(currentVectorNumber, objectValue);
						currentVectorNumber++;
					}
					vector.add(this.mapStringNumber.get(objectValue));
				}

			}

			matrix.add(vector);
			this.mapVectorIDResource.put(this.currentVectorID++, subject);
		}

		return matrix;
	}

	private Set<Property> loadAllPredicates(Model model) {
		Set<Property> predicates = new HashSet<>();
		List<Resource> listOfSubjects = model.listSubjects().toList();
		for (Resource subject : listOfSubjects) {
			List<Statement> list = subject.listProperties().toList();
			for (Statement statement : list) {
				Property predicate = statement.getPredicate();
				predicates.add(predicate);
			}
		}
		return predicates;
	}

	public String getValueFromVectorNumber(Integer vectorNumber) {
		return this.mapStringNumberInvertedIndex.get(vectorNumber);
	}

	public Property getpropertyFromValue(Integer resourceID, String value) {
		Resource resource = this.mapVectorIDResource.get(resourceID);
		List<Statement> list = resource.listProperties().toList();
		for (Statement statement : list) {
			RDFNode object = statement.getObject();
			if (object.isLiteral() && object.asLiteral().toString().equalsIgnoreCase(value)) {
				return statement.getPredicate();
			}
		}
		return null;
	}

	public Property getpropertyFromVectorNumber(Integer resourceID, Integer vectorNumber) {
		String value = this.mapStringNumberInvertedIndex.get(vectorNumber);
		Resource resource = this.mapVectorIDResource.get(resourceID);
		List<Statement> list = resource.listProperties().toList();
		for (Statement statement : list) {
			RDFNode object = statement.getObject();
			if (object.isLiteral() && object.asLiteral().toString().equalsIgnoreCase(value)) {
				return statement.getPredicate();
			}
		}
		return null;
	}
}
