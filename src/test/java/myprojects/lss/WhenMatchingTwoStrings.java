package myprojects.lss;

import static org.junit.Assert.*;

import myprojects.lss.SequenceMatcherPredicate;

import org.junit.Before;
import org.junit.Test;

public class WhenMatchingTwoStrings {
	
	private SequenceMatcherPredicate predicate;
	
	@Before
	public void setUpPredicate() {
		predicate = new SequenceMatcherPredicate(5);
	}

	@Test
	public void withTwoIdenticalStringsHavingSequentialNumbers() throws Exception {
		assertTrue(predicate.isMatch("sd_fx29.0129.rgb", "sd_fx29.0130.rgb"));
		assertTrue(predicate.isMatch("sd_fx29.0128.rgb", "sd_fx29.0129.rgb"));
		assertTrue(predicate.isMatch("sd_fx29.0119.rgb", "sd_fx29.0120.rgb"));
	}
	
	@Test
	public void withSequentialNumbersStarting() throws Exception {
		assertTrue(predicate.isMatch("0.rgb", "1.rgb"));
	}
	
	@Test
	public void withNoExtension() throws Exception {
		assertTrue(predicate.isMatch("A0", "A1"));
	}
	
	@Test
	public void withAlphaNumericDifferentButIdenticalNumbers() throws Exception {
		assertFalse(predicate.isMatch("A01.txt", "A02.rgd"));
	}
	
	@Test
	public void withSkippedNumbers() throws Exception {
		assertTrue(predicate.isMatch("sd_fx29.0119.rgb", "sd_fx29.0124.rgb"));
	}
	
	
	@Test
	public void withSequentialNumbersFollowedByIdenticalNumbers() throws Exception {
		assertTrue(predicate.isMatch("A1_2000.sh", "A2_2000.sh"));
	}
	
	@Test
	public void withTwoStringWithoutNumbers() throws Exception {
		assertFalse(predicate.isMatch("A.txt", "B.txt"));
	}
	
	@Test
	public void withTwoStringWithASequenceButDifferentStrings() throws Exception {
		assertFalse(predicate.isMatch("file01_0043.rgb", "file02_0044.rgb"));
	}
	
	@Test
	public void withTwoStringsOfSameLengthButDifferentExtensions() throws Exception {
		assertFalse(predicate.isMatch("A01.txt", "B01.txa"));
	}
	
	
	@Test
	public void withTwoRelatedStrings() throws Exception {
		assertTrue(predicate.isMatch("file03_1.rgb", "file03_3.rgb"));
	}

}
