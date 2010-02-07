package myprojects.lss;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang.StringUtils.substringAfter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

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
		String originalFirst = first;
		String originalSecond = second;

		Pattern numberPattern = compile("[0-9]+");
		Matcher firstMatcher = numberPattern.matcher(first);
		Matcher secondMatcher = numberPattern.matcher(second);
		boolean match = false;
		while (firstMatcher.find() && secondMatcher.find()) {
			try {
				Integer firstSequence = parseInt(firstMatcher.group());
				Integer nextSequence = parseInt(secondMatcher.group());
				if(firstSequence.equals(nextSequence)) {
					first = substringAfter(first, firstSequence + "");
					second = substringAfter(second, nextSequence + "");

					continue;
					
				}
				String remainingFirst = substringAfter(first, firstSequence + "");
				String remainingSecond = substringAfter(second, nextSequence + "");
				
				if ((remainingFirst.equals(remainingSecond))) {
					sequenceMap.put(originalFirst, firstSequence);
					sequenceMap.put(originalSecond, nextSequence);
					return true;
				} else
					return false;

			} catch (NumberFormatException nex) {
				return false;
			}

		}
		return match;
	}

	public Integer sequenceNumber(String matched) {
		return sequenceMap.get(matched);
	}
	
	

}
