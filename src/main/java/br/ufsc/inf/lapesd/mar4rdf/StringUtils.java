package br.ufsc.inf.lapesd.mar4rdf;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Property;

public class StringUtils {

	public static String createPropertyName(List<Property> properties) {
		List<String> propertyURIs = new ArrayList<>();
		for (Property property : properties) {
			propertyURIs.add(property.getURI());
		}

		String longestCommonPrefix = longestCommonPrefix(propertyURIs);
		String patternName = "has";
		for (String string : propertyURIs) {
			patternName = patternName + string + "And";
		}
		patternName = patternName.substring(0, patternName.length() - 3);
		patternName = patternName.replace(longestCommonPrefix, "");

		String s0 = propertyURIs.get(0).replace(longestCommonPrefix, "");
		String s1 = propertyURIs.get(1).replace(longestCommonPrefix, "");
		String longestSubstring = longestSubstring(s0, s1);

		if (!longestSubstring.isEmpty() && longestSubstring.length() >= 3) {
			patternName = longestCommonPrefix + "has" + longestSubstring;
		}

		return patternName;

	}

	public static String createInversePropertyName(List<Property> properties) {
		List<String> propertyURIs = new ArrayList<>();
		for (Property property : properties) {
			propertyURIs.add(property.getURI());
		}

		String longestCommonPrefix = longestCommonPrefix(propertyURIs);
		String patternName = "is";
		for (String string : propertyURIs) {
			patternName = patternName + string + "And";
		}
		patternName = patternName.substring(0, patternName.length() - 3);
		patternName = patternName.replace(longestCommonPrefix, "");

		String s0 = propertyURIs.get(0).replace(longestCommonPrefix, "");
		String s1 = propertyURIs.get(1).replace(longestCommonPrefix, "");
		String longestSubstring = longestSubstring(s0, s1);

		if (!longestSubstring.isEmpty() && longestSubstring.length() >= 3) {
			patternName = longestCommonPrefix + "is" + longestSubstring;
		}

		return patternName + "Of";
	}

	public static String createTypeSufix(List<Property> properties) {
		List<String> propertyURIs = new ArrayList<>();
		for (Property property : properties) {
			propertyURIs.add(property.getURI());
		}

		String longestCommonPrefix = longestCommonPrefix(propertyURIs);
		String typeSufix = "";
		for (String string : propertyURIs) {
			typeSufix = typeSufix + string + "And";
		}
		typeSufix = typeSufix.substring(0, typeSufix.length() - 3);
		typeSufix = typeSufix.replace(longestCommonPrefix, "");

		String s0 = propertyURIs.get(0).replace(longestCommonPrefix, "");
		String s1 = propertyURIs.get(1).replace(longestCommonPrefix, "");
		String longestSubstring = longestSubstring(s0, s1);

		if (!longestSubstring.isEmpty() && longestSubstring.length() >= 3) {
			typeSufix = longestSubstring;
		}

		return typeSufix;
	}

	public static String createTypeURI(List<Property> properties) {
		List<String> propertyURIs = new ArrayList<>();
		for (Property property : properties) {
			propertyURIs.add(property.getURI());
		}

		String longestCommonPrefix = longestCommonPrefix(propertyURIs);
		String typeSufix = "";
		for (String string : propertyURIs) {
			typeSufix = typeSufix + string + "And";
		}
		typeSufix = typeSufix.substring(0, typeSufix.length() - 3);
		typeSufix = typeSufix.replace(longestCommonPrefix, "");

		String s0 = propertyURIs.get(0).replace(longestCommonPrefix, "");
		String s1 = propertyURIs.get(1).replace(longestCommonPrefix, "");
		String longestSubstring = longestSubstring(s0, s1);

		if (!longestSubstring.isEmpty() && longestSubstring.length() >= 3) {
			typeSufix = longestCommonPrefix + longestSubstring;
		}
		return typeSufix;
	}

	private static String longestCommonPrefix(List<String> strings) {
		if (strings.size() == 0) {
			return ""; // Or maybe return null?
		}

		for (int prefixLen = 0; prefixLen < strings.get(0).length(); prefixLen++) {
			char c = strings.get(0).charAt(prefixLen);
			for (int i = 1; i < strings.size(); i++) {
				if (prefixLen >= strings.get(i).length() || strings.get(i).charAt(prefixLen) != c) {
					// Mismatch found
					return strings.get(i).substring(0, prefixLen);
				}
			}
		}
		return strings.get(0);
	}

	public static String longestSubstring(String str1, String str2) {

		StringBuilder sb = new StringBuilder();
		if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty()) {
			return "";
		}

		// java initializes them already with 0
		int[][] num = new int[str1.length()][str2.length()];
		int maxlen = 0;
		int lastSubsBegin = 0;

		for (int i = 0; i < str1.length(); i++) {
			for (int j = 0; j < str2.length(); j++) {
				if (str1.charAt(i) == str2.charAt(j)) {
					if ((i == 0) || (j == 0)) {
						num[i][j] = 1;
					} else {
						num[i][j] = 1 + num[i - 1][j - 1];
					}

					if (num[i][j] > maxlen) {
						maxlen = num[i][j];
						// generate substring from str1 => i
						int thisSubsBegin = i - num[i][j] + 1;
						if (lastSubsBegin == thisSubsBegin) {
							// if the current LCS is the same as the last time this block ran
							sb.append(str1.charAt(i));
						} else {
							// this block resets the string builder if a different LCS is found
							lastSubsBegin = thisSubsBegin;
							sb = new StringBuilder();
							sb.append(str1.substring(lastSubsBegin, i + 1));
						}
					}
				}
			}
		}
		return sb.toString();
	}

}
