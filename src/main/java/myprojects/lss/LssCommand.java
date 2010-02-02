package myprojects.lss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

/*
 * Wires all the concerns together
 */
public class LssCommand {

	private static final String SINGLE_SPACE = " ";
	private static final char NEWLINE = '\n';
	private final String[] fileNames;
	private SequenceGrouper sequenceGrouper;
	private MissingSequenceFinder missingSequenceFinder;

	public LssCommand(String[] fileNames) {
		Arrays.sort(fileNames);
		this.fileNames = fileNames;
		sequenceGrouper = new SequenceGrouper(5);
		missingSequenceFinder = new  MissingSequenceFinder(5);
	}

	public HashMultimap<String, MatchedResult> execute() {
		HashMultimap<String, MatchedResult> map = HashMultimap.create();
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

	public Collection<Integer> missingSequences(HashMultimap<String, MatchedResult> map) {
		ArrayList<Integer> missingSequence = Lists.newArrayList();
		Collection<MatchedResult> values = map.values();
		for(MatchedResult each: values) {
			if(each != null)				
				missingSequence.add(each.getSequenceNumber());
		}
		Collections.sort(missingSequence);
		return missingSequenceFinder.missingNumbers(missingSequence);
	}
	
	public String toString(HashMultimap<String, MatchedResult> map){
		StringBuffer formattedString = new StringBuffer();
		for(String key: map.keySet()){
			ArrayList<Integer> sequences = Lists.newArrayList();
			Set<MatchedResult> values = map.get(key);
			formattedString.append(values.size());
			formattedString.append(SINGLE_SPACE);
			formattedString.append(key);
			formattedString.append(SINGLE_SPACE);
			for(MatchedResult each: values) {
				if(each != null)				
					sequences.add(each.getSequenceNumber());
			}
			Collection<Integer> missingNumbers = missingSequenceFinder.missingNumbers(sequences);
			if(!missingNumbers.isEmpty())
				formattedString.append("MISSING SEQUENCES: ");
			for(Integer missingNumber: missingNumbers) {
				formattedString.append(missingNumber);
				formattedString.append(SINGLE_SPACE);
			}
			formattedString.append(NEWLINE);
		}
		return formattedString.toString();
	}
	
	public static void main(String[] args) {
		String[] fileNames = new String[] { "elem.info", "sd_fx29.0101.rgb",
				"sd_fx29.0102.rgb", "sd_fx29.0103.rgb", "sd_fx29.0104.rgb", "sd_fx29.0105.rgb", "sd_fx29.0106.rgb",
				"sd_fx29.0107.rgb", "sd_fx29.0108.rgb", "sd_fx29.0109.rgb", "sd_fx29.0110.rgb", "sd_fx29.0111.rgb",
				"sd_fx29.0112.rgb", "sd_fx29.0113.rgb", "sd_fx29.0114.rgb", "sd_fx29.0115.rgb", "sd_fx29.0116.rgb", 
				"sd_fx29.0117.rgb", "sd_fx29.0118.rgb", "sd_fx29.0119.rgb", "sd_fx29.0120.rgb", "sd_fx29.0121.rgb", 
				"sd_fx29.0123.rgb", "sd_fx29.0124.rgb", "sd_fx29.0125.rgb", "sd_fx29.0126.rgb", "sd_fx29.0127.rgb", 
				"sd_fx29.0128.rgb", "sd_fx29.0129.rgb", "sd_fx29.0130.rgb", "sd_fx29.0131.rgb", "sd_fx29.0132.rgb", 
				"sd_fx29.0133.rgb", "sd_fx29.0134.rgb", "sd_fx29.0135.rgb", "sd_fx29.0136.rgb", "sd_fx29.0137.rgb", 
				"sd_fx29.0138.rgb", "sd_fx29.0139.rgb", "sd_fx29.0140.rgb", "sd_fx29.0141.rgb", "sd_fx29.0142.rgb", 
				"sd_fx29.0143.rgb", "sd_fx29.0144.rgb", "sd_fx29.0145.rgb", "sd_fx29.0146.rgb", 
				"sd_fx29.0147.rgb", "strange.xml"};
		LssCommand lssCommand = new LssCommand(fileNames);
		HashMultimap<String, MatchedResult> map = lssCommand.execute();
		System.out.println(lssCommand.toString(map));
	}
	

}
