package myprojects.lss;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
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
		System.err.println(map);
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
			
			Set<MatchedResult> values = new TreeSet<MatchedResult>(map.get(key));
			formattedString.append(values.size());
			formattedString.append(SINGLE_SPACE);
			formattedString.append(key);
			formattedString.append(SINGLE_SPACE);
			if(values.size() > 1){
				formattedString.append("(");
				MatchedResult first = Iterables.get(values, 0);
				MatchedResult last = Iterables.getLast(values);
				formattedString.append(first.getSequenceNumber() + "-" + 
							last.getSequenceNumber());
				formattedString.append(")");

			}
			for(MatchedResult each: values) {
				if(each != null)		
					sequences.add(each.getSequenceNumber());
			}
			Collection<Integer> missingNumbers = missingSequenceFinder.missingNumbers(sequences);
			if(!missingNumbers.isEmpty())
				formattedString.append(" MISSING SEQUENCES: ");
			for(Integer missingNumber: missingNumbers) {
				formattedString.append(missingNumber);
				formattedString.append(SINGLE_SPACE);
			}
			formattedString.append(NEWLINE);
		}
		return formattedString.toString();
	}
	
	public static void main(String[] args) {
		File file = null;
		if(args.length == 0)
			file = new File(".");
		else if(args.length == 1) 
			file = new File(args[0]);
		else 
			System.err.println("Usage: java LssCommand <directory>");
		if(file == null) {
			System.err.println(Arrays.toString(args) + " not valid directory");
			System.exit(-1);
		}
		if(!file.isDirectory()) {
			System.err.println(file.getName() + " is not a directory");
			System.exit(-1);
		}
		File[] files = file.listFiles();
		List<String> fileNameList = Lists.newArrayList();
	
		for(File each: files)
			if(!each.getName().trim().equals(""))
				fileNameList.add(each.getName());
			
//		String[] fileNames = Iterables.toArray(fileNameList, String.class);
	    String[] fileNames=	new String[] {"alpha.txt", "file01_0041.rgb","file01_0043.rgb","file02_0045.rgb","file02_0047.rgb","file03_2.rgb","file03_info.txt",
	    	"file01_0040.rgb","file01_0042.rgb","file02_0044.rgb","file02_0046.rgb","file03_1.rgb","file03_3.rgb"};
		LssCommand lssCommand = new LssCommand(fileNames);
		HashMultimap<String, MatchedResult> map = lssCommand.execute();
		System.out.println(lssCommand.toString(map));
	}
	

}
