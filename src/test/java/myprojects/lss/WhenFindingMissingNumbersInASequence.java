package myprojects.lss;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Arrays;
import java.util.Collection;

import myprojects.lss.MissingSequenceFinder;

import org.junit.Before;
import org.junit.Test;

public class WhenFindingMissingNumbersInASequence {
	
	private MissingSequenceFinder finder;
	
	@Before
	public void setupFinder() {
		finder = new MissingSequenceFinder(5);
	}

	@Test
	public void oneNumberMissingInASequence() throws Exception {
		Collection<Integer> missingNumbers = finder.missingNumbers(Arrays.asList(12, 13, 15));
		assertEquals(missingNumbers, Arrays.asList(14));
	}
	
	@Test
	public void manyNumbersMissingInASequence() throws Exception {
		Collection<Integer> missingNumbers = finder.missingNumbers(Arrays.asList(12, 13, 17));
		assertEquals(missingNumbers, Arrays.asList(14, 15, 16));
	}
	
	@Test
	public void manyNumbersAcrossTwentiesAndThirties() throws Exception {
		Collection<Integer> missingNumbers = finder.missingNumbers(Arrays.asList(27, 29, 34));
		assertEquals(missingNumbers, Arrays.asList(28, 30, 31, 32, 33));
	}
	
	@Test
	public void numbersLargerThanThreshold() throws Exception {
		Collection<Integer> missingNumbers = finder.missingNumbers(Arrays.asList(27, 34));
		assertEquals(missingNumbers, Arrays.asList(28, 29, 30, 31, 32, 33));
	}


}
