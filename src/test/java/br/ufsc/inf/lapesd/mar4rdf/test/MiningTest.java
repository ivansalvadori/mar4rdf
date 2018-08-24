package br.ufsc.inf.lapesd.mar4rdf.test;

import java.io.IOException;
import java.util.List;

import org.apache.jena.riot.Lang;
import org.junit.Test;

import br.ufsc.inf.lapesd.mar4rdf.Mining;
import br.ufsc.inf.lapesd.mar4rdf.Pattern;
import br.ufsc.inf.lapesd.mar4rdf.RDFPatternWriter;

public class MiningTest {

	@Test
	public void minigTest() throws IOException {
		Mining mining = new Mining();
		List<Pattern> patterns = mining.findPatterns("D:\\Desenvolvimento\\eclipse-workspace\\csv2rdf\\rdf\\output_0a17b506-5c84-4301-af9b-e6c269c5f69e.ntriples", Lang.NTRIPLES);
		RDFPatternWriter rdfPatternWriter = new RDFPatternWriter();
		rdfPatternWriter.setResourceDomain("http://pagamentos.gov.br/");
		rdfPatternWriter.write(patterns, "D:\\Desenvolvimento\\eclipse-workspace\\csv2rdf\\rdf\\output_0a17b506-5c84-4301-af9b-e6c269c5f69e.ntriples", Lang.NTRIPLES);
		System.out.println(patterns);
	}
}
