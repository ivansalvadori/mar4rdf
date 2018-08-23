package br.ufsc.inf.lapesd.mar4rdf;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;

public class RDFFileReader {

	private Charset rdfCharset = StandardCharsets.UTF_8;
	private Lang rdfLang = Lang.NTRIPLES;

	public Model read(File file) {
		String modelString = null;
		Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_LITE_MEM);
		try {
			modelString = new String(Files.readAllBytes(Paths.get(file.getPath())), this.rdfCharset);
		} catch (IOException e) {
			return model;
		}

		model.read(new StringReader(modelString), null, this.rdfLang.getName());
		return model;
	}

}
