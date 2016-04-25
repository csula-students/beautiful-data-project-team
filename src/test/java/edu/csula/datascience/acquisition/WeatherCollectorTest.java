package edu.csula.datascience.acquisition;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import edu.csula.datascience.acquisition.*;
import junit.framework.Assert;

public class WeatherCollectorTest {
	private WeatherCollector weatherCollector;
	private MockSource weatherSource;
	
	@Before
	public void setup(){
		weatherCollector = new WeatherCollector();
		weatherSource = new MockSource();
	}
	
	@Test
	public void mungee(){
		ArrayList<Document> mungedList = (ArrayList<Document>) weatherCollector.mungee(weatherSource.next());
		Assert.assertEquals(2, mungedList.size());
	}
	

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
