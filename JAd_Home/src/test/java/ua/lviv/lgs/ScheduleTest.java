package ua.lviv.lgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class ScheduleTest {
	
//private Time time;
private Movie movie;
private Seance seance;
private Schedule schedule;
private Cinema cinema;
	
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
	public void beforeTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		//time = new Time(0, 0);
		movie = new Movie("Matrix", new Time(2, 32));
		seance = new Seance();
			Set<Seance> seanses = new TreeSet<>();
			seanses.add(new Seance(movie, new Time(10, 7)));		
		schedule = new Schedule(seanses);
		cinema = new Cinema();
			//open = new Time(10, 0);
			//close = new Time(2, 20);
			//breakTime = new Time(0, 15);
			List<Movie> moviesLibrary = new ArrayList<>();
			cinema.moviesLibrary.add(movie);
			
			System.out.println(cinema.moviesLibrary);
		
	}
	
	@After
	public void afterTest() {
		//time = null;
		movie = null;
		seance = null;
		schedule = null;
	}
	//---------------------------------------------------------------------------------------
	
	@Test
	public void scheduleAddSeanceTest() throws WrongInputTimeException {
		
		schedule.addSeance(cinema, movie, seance);
		
		Schedule real = null;
		Schedule expected = null;
		Assert.assertEquals(expected, real);				
	}	
	
	
	
}
