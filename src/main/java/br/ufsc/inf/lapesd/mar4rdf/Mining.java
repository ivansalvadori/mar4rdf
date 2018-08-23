package br.ufsc.inf.lapesd.mar4rdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;

import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.AlgoAprioriTIDClose;
import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.Itemset;
import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.Itemsets;
import br.ufsc.inf.lapesd.mar4rdf.aprioriTIDClose.TransactionDatabase;

public class Mining {
	private VectorGenerator vectorGenerator = new VectorGenerator();

	public List<Pattern> findPatterns() throws IOException {
		List<Pattern> listOfPatterns = new ArrayList<>();

		AlgoAprioriTIDClose apriori = new AlgoAprioriTIDClose();
		apriori.setShowTransactionIdentifiers(true);
		Model model = new RDFFileReader().read(new File("input.rdf"));

		double treshhold = 0.000;
		double currentminsupp = 1.0;
		while (currentminsupp >= treshhold) {
			List<List<Integer>> matrix = vectorGenerator.generate(model);

			TransactionDatabase db = new TransactionDatabase();
			for (List<Integer> vector : matrix) {
				db.addTransaction(vector);
			}

			Itemsets assocRules = apriori.runAlgorithm(db, currentminsupp, null);
			List<Pattern> patterns = this.processAssocRules(assocRules, model);
			listOfPatterns.addAll(patterns);
			currentminsupp = currentminsupp - 0.001;
		}
		return listOfPatterns;
	}

	private List<Pattern> processAssocRules(Itemsets itemsets, Model model) {
		List<Pattern> listOfPatterns = new ArrayList<>();
		List<List<Itemset>> levels = itemsets.getLevels();
		for (List<Itemset> list : levels) {
			for (Itemset pattern : list) {
				pattern.toString();
				List<String> properties = new ArrayList<>();
				int[] items = pattern.getItems();
				for (int i = 0; i < items.length; i++) {
					int vectorNumber = items[i];
					// TODO: Verify if the property is the same in all transactions
					Integer firstTransaction = new ArrayList<>((pattern.getTransactionsIds())).get(0);
					Property propertyName = vectorGenerator.getpropertyFromVectorNumber(firstTransaction, vectorNumber);
					properties.add(propertyName.toString());
				}

				if (properties.size() > 1) {
					listOfPatterns.add(new Pattern(properties, pattern.getTransactionsIds().size(), StringUtils.patternName(properties)));
				}
				this.removeFromModel(model, properties);
			}
		}

		return listOfPatterns;
	}

	private void removeFromModel(Model model, List<String> patternList) {
		List<Statement> stmts = model.listStatements().toList();
		for (Statement statement : stmts) {
			if (patternList.contains(statement.getPredicate().getURI())) {
				model.remove(statement);
			}
		}
	}

}
