package ua.lviv.lgs;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class TimeTest {
	
private Time time;
	
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
		time = new Time(0, 0);
	}
	
	@After
	public void afterTest() {
		time = null;		
	}
	//---------------------------------------------------------------------------------------
	
	@Test
	public void timeTest() throws WrongInputTimeException {
		
		Time [] timeArr = {new Time(0, 5), new Time(8, 5)};
		Time [] expectedArr = {new Time(0, 5), new Time(8, 5)};
		Time real = null;
		Time expected = null;
		
		for (int i = 0; i < expectedArr.length; i++) {	
			
			real = timeArr[i];
			expected = expectedArr[i];
			Assert.assertEquals(expected, real);
		}		
	}
	
	@Test(expected = WrongInputTimeException.class)
	public void timeWithExeptionTest() throws WrongInputTimeException {
		
		Time real = new Time(28, 86);
		Time expected = new Time(0, 0);
		Assert.assertEquals(expected, real);
	}
		
	@Test
	public void timeCompareTest() throws WrongInputTimeException {
		Time [] [] timeArr = {{new Time(15, 5), new Time(10, 2)}, {new Time(23, 5), new Time(1, 2)}};
		int [] expectedArr = {1, -1};
		int real = 0;
		int expected = 0;
		
		for (int i = 0; i < expectedArr.length; i++) {	
			
			real = time.compare(timeArr [i][0], timeArr [i][1]);
			expected = expectedArr[i];
			Assert.assertEquals(expected, real);
		}
		
	}
	
	@Test
	public void timeCompareToTest() throws WrongInputTimeException {
		Time [] [] timeArr = {{new Time(15, 5), new Time(10, 2)}, {new Time(23, 5), new Time(1, 2)}};
		int [] expectedArr = {1, -1};
		int real = 0;
		int expected = 0;
		
		for (int i = 0; i < expectedArr.length; i++) {				
			
			time = timeArr [i][0];
			real = time.compareTo(timeArr [i][1]);
			expected = expectedArr[i];
			Assert.assertEquals(expected, real);
		}		
	}			

}
