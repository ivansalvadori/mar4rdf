package br.ufsc.inf.lapesd.mar4rdf.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import br.ufsc.inf.lapesd.mar4rdf.Mining;
import br.ufsc.inf.lapesd.mar4rdf.Pattern;

public class MiningTest {

	@Test
	public void AprioriTest() throws IOException {
		Mining mining = new Mining();
		List<Pattern> patterns = mining.findPatterns();
		System.out.println(patterns);

	}
}
