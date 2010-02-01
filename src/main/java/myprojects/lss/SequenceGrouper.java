package myprojects.lss;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

/*
 * Creates a sequence given a base name and rest of the list of names
 */
public class SequenceGrouper {
	
	static 	SequenceMatcherPredicate predicate;

	public SequenceGrouper(int sequenceThreshold) {
		predicate = new SequenceMatcherPredicate(sequenceThreshold);
		
	}
		
	public Collection<MatchedResult> match(String base, List<String> matching) {
		Set<MatchedResult> sequences = Sets.newLinkedHashSet();
		
		for(String matched : matching) {
			if(predicate.isMatch(base, matched)){
				sequences.add(new MatchedResult(base, predicate.sequenceNumber(base)));
				sequences.add(new MatchedResult(matched, predicate.sequenceNumber(matched)));
				base = matched;
			}
		}
		return Collections.unmodifiableSet(sequences);
	}
	 

}
