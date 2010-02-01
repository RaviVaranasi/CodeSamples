package myprojects.lss;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Holds the algorithm in determining if a pair of names have numeric sequential matches.
 * Assumes the padding to be same
 */
public class SequenceMatcherPredicate {

	private final int sequenceThreshold;
	private Map<String, Integer> sequenceMap = new HashMap<String, Integer>();

	public SequenceMatcherPredicate(int sequenceThreshold) {
		this.sequenceThreshold = sequenceThreshold;
	}

	public boolean isMatch(String first, String second) {
		if (first.length() != second.length())
			return false;

		Pattern numberPattern = compile("[0-9]+");
		Matcher firstMatcher = numberPattern.matcher(first);
		Matcher secondMatcher = numberPattern.matcher(second);
		int i = 0;
		while (firstMatcher.find() && secondMatcher.find()) {
			try {
				Integer firstSequence = parseInt(firstMatcher.group());
				Integer nextSequence = parseInt(secondMatcher.group());
				if (firstSequence != nextSequence
						&& (nextSequence - firstSequence <= sequenceThreshold)) {
					sequenceMap.put(first, firstSequence);
					sequenceMap.put(second, nextSequence);

					return true;
				}
				i++;

			} catch (NumberFormatException nex) {
				return false;
			}

		}
		return false;
	}

	public Integer sequenceNumber(String matched) {
		return sequenceMap.get(matched);
	}
	
	

}
