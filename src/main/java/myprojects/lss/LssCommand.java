package myprojects.lss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

/*
 * Wires all the concerns together
 */
public class LssCommand {

	private final String[] fileNames;
	private SequenceGrouper sequenceGrouper;
	HashMultimap<String, MatchedResult> map = HashMultimap.create();

	public LssCommand(String[] fileNames) {
		Arrays.sort(fileNames);
		this.fileNames = fileNames;
		sequenceGrouper = new SequenceGrouper(5);
	}

	public HashMultimap<String, MatchedResult> execute() {
		LinkedList<String> workingSet = Lists.newLinkedList();
		for(int i = 0; i < fileNames.length; i ++) {
			workingSet.add(fileNames[i]);
		}
		while(workingSet.size() > 0){
			String base = workingSet.get(0);
			workingSet.remove(base);
			Collection<MatchedResult> matches = sequenceGrouper.match(base, workingSet);
			for(MatchedResult each: matches) {
				map.put(base, each);
				workingSet.remove(each.getName());
			}
			if(matches.isEmpty())
				map.put(base, null);
		}
		
		return map;
		
	}

	public Iterable<Integer> missingSequences() {
		ArrayList<Integer> missingSequence = Lists.newArrayList();
		Collection<MatchedResult> values = map.values();
		for(MatchedResult each: values) {
			if(each != null)				
				missingSequence.add(each.getSequenceNumber());
		}
		Collections.sort(missingSequence);
		System.out.println(missingSequence);
		return new  MissingSequenceFinder(5).missingNumbers(missingSequence);
	}

}
