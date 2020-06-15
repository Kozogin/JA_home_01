package ua.lviv.lgs; 

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class MovieTest {
	
private Movie movie;
	
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
		movie = new Movie();		
	}
	
	@After
	public void afterTest() {
		movie = null;
	}
	//---------------------------------------------------------------------------------------
	
	@Test
	public void moviecalcEndTimeTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		Time [] movieArr = {new Time(8, 00), new Time(23, 45), new Time(1, 02)};
		Time [] expectedArr = {new Time(10, 37), new Time(2, 22), new Time(3, 39)};
		movie = new Movie("Test", new Time(2, 37));
		
		Time real = null;
		Time expected = null;
		
		for (int i = 0; i < expectedArr.length; i++) {	
			
			real = movie.calcEndTime(movieArr[i]);			
			expected = expectedArr[i];
			Assert.assertEquals(expected, real);
		}
	}
	
	@Test
	public void movieTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		Movie [] movieArr = {new Movie("Matrix", new Time(2, 2)), new Movie("Matrix 12345678", new Time(3, 59))};
		Movie [] expectedArr = {new Movie("Matrix", new Time(2, 2)), new Movie("Matrix 12345678", new Time(3, 59))};
		
		Movie real = null;
		Movie expected = null;
		
		for (int i = 0; i < expectedArr.length; i++) {	
			
			real = movieArr[i];
			expected = expectedArr[i];
			Assert.assertEquals(expected, real);
		}
	}
	
	@Test(expected = WrongInputLengthTitleException.class)
	public void movieWithWrongInputLengthTitleExceptionTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		
		Movie real = new Movie("Matrix "
				+ "Matrix Matrix Matrix Matrix Matrix Matrix Matrix Matrix Matrix "
				+ "Matrix Matrix Matrix Matrix Matrix "
				, new Time(2, 32));
		
		Movie expected = new Movie("Matrix Matrix", new Time(2, 32));
		
			Assert.assertEquals(expected, real);
	}
	
	@Test(expected = WrongInputTimeException.class)
	public void movieWithWrongInputTimeExceptionTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		
		Movie real = new Movie("Matrix", new Time(2, 95));
		Movie expected = new Movie("Matrix", new Time(2, 2));
			Assert.assertEquals(expected, real);
	}	

}
