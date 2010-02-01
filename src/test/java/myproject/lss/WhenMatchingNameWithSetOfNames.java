package myproject.lss;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import myprojects.lss.MatchedResult;
import myprojects.lss.SequenceGrouper;

import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class WhenMatchingNameWithSetOfNames {
	
	private SequenceGrouper grouper;
	
	@Before
	public void setUpSequenceGrouper(){
		grouper = new SequenceGrouper(5);		
	}

	@Test
	public void showGroupedElements() throws Exception {
		Collection<MatchedResult> matches = grouper.match("sd_fx29.0115.rgb", 
				asList("sd_fx29.0116.rgd", "sd_fx29.0119.rgd", "sd_fx29.0120.rgd"));
		assertEquals(4, matches.size());
		Collection<Integer> actual = Collections2.transform(matches, new Function<MatchedResult, Integer>() {

			public Integer apply(MatchedResult arg0) {
				return arg0.getSequenceNumber();
			}
		});
		assertThat(actual, JUnitMatchers.hasItems(116, 119, 120));
	}
	
	@Test
	public void showGroupElementsGreaterThan5() throws Exception {
		Collection<MatchedResult> matches = grouper.match("sd_fx29.0115.rgb", 
				asList("sd_fx29.0116.rgd", "sd_fx29.0119.rgd", "sd_fx29.0120.rgd", "sd_fx29.0121.rgd",
						"sd_fx29.0122.rgd", "sd_fx29.0123.rgd"));
		assertEquals(7, matches.size());
		Collection<Integer> actual = Collections2.transform(matches, new Function<MatchedResult, Integer>() {

			public Integer apply(MatchedResult arg0) {
				return arg0.getSequenceNumber();
			}
		});
		assertThat(actual, JUnitMatchers.hasItems(116, 119, 120, 121, 122, 123));
	}
	
	
	@Test
	public void baseNameNotPartOfSequence() throws Exception {
		Collection<MatchedResult> matches = grouper.match("base.rgb", 
				asList("sd_fx29.0116.rgd", "sd_fx29.0119.rgd", "sd_fx29.0120.rgd", "sd_fx29.0121.rgd",
						"sd_fx29.0122.rgd", "sd_fx29.0123.rgd"));
		assertEquals(0, matches.size());
	}
	
	@Test
	public void notRelated() throws Exception {
		Collection<MatchedResult> matches = grouper.match("a123.rgb", 
				asList("b.rgd", "24c.rgd", "a2130_123.rgd"));	
		assertTrue(matches.isEmpty());
	}
	
	

}
