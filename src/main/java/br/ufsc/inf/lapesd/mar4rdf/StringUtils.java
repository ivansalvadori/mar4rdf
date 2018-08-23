package br.ufsc.inf.lapesd.mar4rdf;

import java.util.List;

public class StringUtils {

	public static String patternName(List<String> propertyNames) {
		String longestCommonPrefix = longestCommonPrefix(propertyNames);
		String patternName = "has";
		for (String string : propertyNames) {
			patternName = patternName + string + "And";
		}
		patternName = patternName.substring(0, patternName.length() - 3);
		patternName = patternName.replace(longestCommonPrefix, "");

		String s0 = propertyNames.get(0).replace(longestCommonPrefix, "");
		String s1 = propertyNames.get(1).replace(longestCommonPrefix, "");
		String longestSubstring = longestSubstring(s0, s1);

		if (!longestSubstring.isEmpty()) {
			patternName = longestCommonPrefix + "has" + longestSubstring;
		}

		return patternName;

	}

	public static String longestCommonPrefix(List<String> strings) {
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
