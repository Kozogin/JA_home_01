package ua.lviv.lgs; 

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class SeanceTest {
	
private Seance seance;
	
	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		protected void failed(Throwable e, org.junit.runner.Description description) {
			System.out.println("FAILED --> " +  description.getMethodName());
		};
		protected void succeeded(org.junit.runner.Description description) {
			System.out.println("SUCCEED --> " +  description.getMethodName());
		};
		
	};
	
	@Before
	public void beforeTest() throws WrongInputTimeException {		
		seance = new Seance();
	}
	
	@After
	public void afterTest() {
		seance = null;
	}
	//---------------------------------------------------------------------------------------
	
	@Test
	public void seanceCompareToTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		Seance [] seanceArr = {new Seance(new Movie("Matrix", new Time(2, 32)), new Time(10, 7)),
				new Seance(new Movie("Matrix", new Time(2, 32)), new Time(8, 7)),
				new Seance(new Movie("Matrix", new Time(2, 32)), new Time(2, 7))};
		
		int [] expectedArr = {0, 1, -1};
		int real = 0;
		int expected = 0;
		seance = new Seance(new Movie("Matrix", new Time(2, 32)), new Time(10, 7));
		
		for (int i = 0; i < expectedArr.length; i++) {	
			
			real = seance.compareTo(seanceArr [i]);
			expected = expectedArr[i];
			Assert.assertEquals(expected, real);
		}		
	}		
		
}
