package br.ufsc.inf.lapesd.mar4rdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

public class RDFPatternWriter {

	private String resourceDomain = "";

	public void write(List<Pattern> patterns, String rdfFilepath, Lang lang) throws FileNotFoundException {
		Model model = new RDFFileReader().read(new File(rdfFilepath), lang);
		List<Resource> subjects = model.listSubjects().toList();
		for (Resource subject : subjects) {
			for (Pattern pattern : patterns) {
				List<String> values = new ArrayList<>();
				Map<Property, List<String>> mapPropertyValues = new HashMap<>();
				for (Property property : pattern.getListOfProperties()) {
					List<Statement> stmts = subject.listProperties(property).toList();
					mapPropertyValues.put(property, new ArrayList<>());
					for (Statement stmt : stmts) {
						values.add(stmt.getObject().asLiteral().toString());
						mapPropertyValues.get(property).add(stmt.getObject().asLiteral().toString());
						model.remove(stmt);
					}
				}
				Resource newResource = model.createResource(generateResourceURI(values), ResourceFactory.createResource(pattern.getTypeURI()));
				Set<Property> keySet = mapPropertyValues.keySet();
				for (Property property : keySet) {
					List<String> objects = mapPropertyValues.get(property);
					for (String object : objects) {
						newResource.addProperty(property, object);
					}
				}
				subject.addProperty(ResourceFactory.createProperty(pattern.getPropertyURI()), newResource);
			}
		}
		RDFDataMgr.write(new FileOutputStream(new File(rdfFilepath + "_withPatterns")), model, lang);
	}

	public void write(List<Pattern> patterns, String rdfFilepath, Lang lang, Model targetModel) throws FileNotFoundException {
		Model model = new RDFFileReader().read(new File(rdfFilepath), lang);
		List<Resource> subjects = model.listSubjects().toList();
		for (Resource subject : subjects) {
			for (Pattern pattern : patterns) {
				List<String> values = new ArrayList<>();
				Map<Property, List<String>> mapPropertyValues = new HashMap<>();
				for (Property property : pattern.getListOfProperties()) {
					List<Statement> stmts = subject.listProperties(property).toList();
					mapPropertyValues.put(property, new ArrayList<>());
					for (Statement stmt : stmts) {
						values.add(stmt.getObject().asLiteral().toString());
						mapPropertyValues.get(property).add(stmt.getObject().asLiteral().toString());
						model.remove(stmt);
					}
				}
				Resource newResource = model.createResource(generateResourceURI(values), ResourceFactory.createResource(pattern.getTypeURI()));
				Set<Property> keySet = mapPropertyValues.keySet();
				for (Property property : keySet) {
					List<String> objects = mapPropertyValues.get(property);
					for (String object : objects) {
						newResource.addProperty(property, object);
					}
				}
				subject.addProperty(ResourceFactory.createProperty(pattern.getPropertyURI()), newResource);
			}
		}
		targetModel.add(model);
	}

	private String generateResourceURI(List<String> keys) {
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key);
		}
		String sha2 = sha2(sb.toString());
		URI uri = null;
		try {
			uri = new URI(this.resourceDomain + "/" + sha2);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri.toString();
	}

	private String sha2(String input) {
		String sha2 = null;
		try {
			MessageDigest msdDigest = MessageDigest.getInstance("SHA-256");
			msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
			sha2 = DatatypeConverter.printHexBinary(msdDigest.digest());
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			System.out.println("SHA-256 error");
		}
		return sha2;
	}

	public void setResourceDomain(String resourceDomain) {
		if (resourceDomain.endsWith("/")) {
			resourceDomain = resourceDomain.substring(0, resourceDomain.length() - 1);
		}
		this.resourceDomain = resourceDomain;
	}
}
