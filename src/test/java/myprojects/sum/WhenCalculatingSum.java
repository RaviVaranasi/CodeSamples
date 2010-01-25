package myprojects.sum;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class WhenCalculatingSum {
	
	@Test
	public void calculateSumFor5ElementList() throws Exception {
		assertEquals(new Integer(31),  new ListAdder(asList(5, 3, 2, 1, 20)).computeSum());
	}
	
	@Test
	public void calculateSumFor2ElementList() throws Exception {
		assertEquals(new Integer(13),  new ListAdder(asList(10, 3)).computeSum());
	}

	@Test
	public void calculateSumFor1ElementList() throws Exception {
		assertEquals(new Integer(3),  new ListAdder(asList(3)).computeSum());
	}
	
	@Test
	public void calculateSumFor10ElementList() throws Exception {
		assertEquals(new Integer(550),  new ListAdder(asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)).computeSum());
	}
	
	@Test
	public void calculateSumForEmptyList() throws Exception {
		assertEquals(new Integer(0),  new ListAdder(new ArrayList<Integer>()).computeSum());
	}
}
