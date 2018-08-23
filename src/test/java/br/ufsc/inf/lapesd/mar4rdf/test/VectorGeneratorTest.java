package br.ufsc.inf.lapesd.mar4rdf.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.junit.Test;

import br.ufsc.inf.lapesd.mar4rdf.RDFFileReader;
import br.ufsc.inf.lapesd.mar4rdf.VectorGenerator;

public class VectorGeneratorTest {

	@Test
	public void VectorGenerationTest() throws IOException {
		VectorGenerator vg = new VectorGenerator();
		Model model = new RDFFileReader().read(new File("input.rdf"));
		List<List<Integer>> matrix = vg.generate(model);
		System.out.println(matrix);

	}
}
