package myprojects.lss;
/*
 * Finds missing sequences in a list
 */

import static com.google.common.collect.Iterators.peekingIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;

public class MissingSequenceFinder {
	
	private final Integer threshold;

	public MissingSequenceFinder(Integer threshold){
		this.threshold = threshold;
		
	}

	public Collection<Integer> missingNumbers(List<Integer> sequence) {
		Collections.sort(sequence);
		ArrayList<Integer> list = Lists.newArrayList();
		PeekingIterator<Integer> iterator = peekingIterator(sequence.iterator());
		while(iterator.hasNext()){
			Integer current = iterator.next();
			if(!iterator.hasNext())
				continue;
			Integer next = iterator.peek();
			int delta = next - current;
			if(delta > 100 || next < current || delta == 1)
				continue;
			for(int i =1; i <= delta -1 ; i++)
				list.add(current + i);
		}
			
		return list;
	}

}
