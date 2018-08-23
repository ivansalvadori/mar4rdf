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
		List<Pattern> patterns = mining.findPatterns("input.rdf", Lang.NTRIPLES);
		RDFPatternWriter rdfPatternWriter = new RDFPatternWriter();
		rdfPatternWriter.setResourceDomain("http://pagamentos.gov.br/");
		rdfPatternWriter.write(patterns, "input.rdf", Lang.NTRIPLES);
		System.out.println(patterns);
	}
}
