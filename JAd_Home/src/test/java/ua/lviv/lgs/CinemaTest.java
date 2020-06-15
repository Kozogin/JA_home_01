package ua.lviv.lgs; 

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class CinemaTest {
	
	private Movie movie;
	private Seance seance;
	private Schedule scheduletest;
	private Cinema cinematest;
	List<Movie> moviesLibraryTest = new ArrayList<>();
	
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
		
		movie = new Movie("Matrix", new Time(2, 32));
		seance = new Seance();
		cinematest = new Cinema();
		
		Set<Seance> seanses50 = new TreeSet<>();
		seanses50.add(new Seance(movie, new Time(10, 7)));
		seanses50.add(new Seance(movie, new Time(22, 33)));
		seanses50.add(new Seance(movie, new Time(17, 15)));
		seanses50.add(new Seance(movie, new Time(12, 55)));
		
		scheduletest = new Schedule(seanses50);	
		
		moviesLibraryTest.add(movie);

	}
	
	@After
	public void afterTest() {		
		movie = null;
		seance = null;
		scheduletest = null;
		cinematest = null;
	}
	//---------------------------------------------------------------------------------------
	
	@Test
	public void cinemaAddMovieTest() throws WrongInputTimeException, WrongInputLengthTitleException {
			
		cinematest.setMoviesLibrary(moviesLibraryTest);
		cinematest.addMovie();		
		
		Movie first = cinematest.moviesLibrary.stream().findFirst().get();		
		Movie last = cinematest.moviesLibrary.stream().filter(o -> !o.equals(first)).findFirst().get();

		Movie real = last;		
		Movie expected = new Movie("Everest", new Time(1, 52));
								
		Assert.assertEquals(expected, real);
		
		real = last;		
		expected = new Movie("Everest", new Time(1, 42));
								
		Assert.assertFalse(expected.equals(real));
		
	}
	
	@Test
	public void cinemaAddSeanceTest() throws WrongInputTimeException, WrongInputLengthTitleException {
			
		cinematest.addSeance(scheduletest, "sunday");
		cinematest.addSeance(scheduletest, "friday");			
		cinematest.schedules.put(Days.valueOf("monday".toUpperCase()), scheduletest);	
		
		Set<Seance> expected = cinematest.schedules.get(Days.MONDAY).getSeances();
		Set<Seance>  real = cinematest.schedules.get(Days.FRIDAY).getSeances();
		
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void cinemaIsMonthPresentTest() throws WrongInputTimeException, WrongInputLengthTitleException {
			
		boolean real = cinematest.isMonthPresent(Days.values(), "sunday");
		boolean expected = true;
		
		Assert.assertEquals(expected, real);
		
		real = cinematest.isMonthPresent(Days.values(), "sundayTest");		
		Assert.assertFalse(real);
	}
	
	@Test
	public void cinemaRemoveMovieTest() throws WrongInputTimeException, WrongInputLengthTitleException {
		
		cinematest.setMoviesLibrary(moviesLibraryTest);		
				
		List<Movie> real = cinematest.moviesLibrary;
		List<Movie> expected = new ArrayList<>();
		
		
		cinematest.addSeance(scheduletest, "sunday");
		
//		System.out.println(cinematest.moviesLibrary);
//		System.out.println(scheduletest);
//		System.out.println(cinematest.schedules);
		
		cinematest.removeMovie(scheduletest);
		
//		System.out.println(cinematest.moviesLibrary);
//		System.out.println(scheduletest);
//		System.out.println(cinematest.schedules);
		
		Set<Seance>  real2= scheduletest.getSeances();
		Set<Seance>  expected2= new Schedule().getSeances();
		
//		System.out.println(real2);
//		System.out.println(expected2);
		
		TreeMap<Days, Schedule> real3 = cinematest.schedules;
		TreeMap<Days, Schedule> expected3 = new TreeMap<>();		

//		System.out.println(real3);
//		System.out.println(expected3);
		
		Assert.assertEquals(expected, real);
		Assert.assertEquals(expected2, real2);
		Assert.assertEquals(expected3, real3);
		
		
	}

}
