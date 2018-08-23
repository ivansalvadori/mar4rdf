package br.ufsc.inf.lapesd.mar4rdf.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.AlgoAprioriTIDClose;
import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.Itemset;
import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.Itemsets;
import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.TransactionDatabase;

public class SPMFTest {

	@Test
	public void AprioriTest() throws IOException {
		AlgoAprioriTIDClose apriori = new AlgoAprioriTIDClose();

		String inputFilename = "input2.txt";
		double minsupp = 0.15;

		TransactionDatabase db = new TransactionDatabase();
		db.loadFile(inputFilename);

		apriori.setShowTransactionIdentifiers(true);
		Itemsets runAlgorithm = apriori.runAlgorithm(db, minsupp, null);
		List<List<Itemset>> levels = runAlgorithm.getLevels();
		// levels.remove(0);
		// levels.remove(1);

		for (List<Itemset> list : levels) {
			for (Itemset pattern : list) {
				pattern.toString();
				List<Integer> listOfItems = new ArrayList<>();
				int[] items = pattern.getItems();
				for (int i = 0; i < items.length; i++) {
					listOfItems.add(items[i]);
				}
				System.out.println(listOfItems + " " + pattern.getTransactionsIds());
			}
		}

		// runAlgorithm.printItemsets(1);
	}
}
