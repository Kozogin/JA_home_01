package ua.lviv.lgs;

import static org.junit.Assert.assertFalse;

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

public class ScheduleTest {
	
//private Time time;
private Movie movie;
private Seance seance;
private Schedule scheduletest;
private Cinema cinematest;
	
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
			Set<Seance> seanses1 = new TreeSet<>();
			Set<Seance> seanses2 = new TreeSet<>();
			Set<Seance> seanses3 = new TreeSet<>();
			Set<Seance> seanses4 = new TreeSet<>();
			
			seanses1.add(new Seance(movie, new Time(10, 7)));
			seanses2.add(new Seance(movie, new Time(22, 33)));
			seanses3.add(new Seance(movie, new Time(17, 15)));
			seanses4.add(new Seance(movie, new Time(12, 55)));
			
		scheduletest = new Schedule(seanses1);
		cinematest = new Cinema();
			//open = new Time(10, 0);
			//close = new Time(2, 20);
			//breakTime = new Time(0, 15);
			List<Movie> moviesLibrary = new ArrayList<>();
			cinematest.moviesLibrary.add(movie);
			
			
			TreeMap<Days, Schedule> schedules = new TreeMap<>();
			
			cinematest.setSchedules(schedules);
			
				schedules.put(Days.valueOf("monday".toUpperCase()), new Schedule(seanses2));
				schedules.put(Days.valueOf("sunday".toUpperCase()), new Schedule(seanses3));
				schedules.put(Days.valueOf("friday".toUpperCase()), new Schedule(seanses4));
					
	}
	
	@After
	public void afterTest() {
		//time = null;
		movie = null;
		seance = null;
		scheduletest = null;
		cinematest = null;
	}
	//---------------------------------------------------------------------------------------
	
	@Test
	public void scheduleAddSeanceTest() throws WrongInputTimeException {
				
		scheduletest.addSeance(cinematest, movie, seance);		
		Set<Seance> seance1 = scheduletest.getSeances();		
		Seance real = ((TreeSet<Seance>) seance1).last();		
		Seance expected = new Seance(movie, new Time(10, 30));
		
		Assert.assertEquals(expected, real);
		
		real = ((TreeSet<Seance>) seance1).first();
		assertFalse(real.equals(expected)); 
	}
	
	@Test
	public void scheduleRemoveSeanceTest() throws WrongInputTimeException {
		
		scheduletest.addSeance(cinematest, movie, seance);		
		scheduletest.remoteSeance(cinematest);
		Set<Seance> seance1 = scheduletest.getSeances();		
		Seance real = ((TreeSet<Seance>) seance1).last();		
		Seance expected = new Seance(movie, new Time(10, 30));
				
		Assert.assertEquals(expected, real);				
	}	
	
	@Test
	public void schedulecopySeanceTest() throws WrongInputTimeException {
		
		scheduletest.seanses.clear();
		scheduletest.copySeance(cinematest, "sunday");
		
		Set<Seance> real = scheduletest.seanses;
		Set<Seance> seanses10 = new TreeSet<>();		
		seanses10.add(new Seance(movie, new Time(17, 15)));
		Set<Seance> expected = seanses10;				
		Assert.assertTrue(expected.equals(real));
		
		scheduletest.copySeance(cinematest, "monday");
		real = scheduletest.seanses;
		Assert.assertFalse(expected.equals(real));	
		
		//System.out.println(cinematest.schedules.get(Days.valueOf("monday".toUpperCase())).getSeances());
	}	
	
	@Test
	public void scheduleCalcBreakTimeOptimizeteTest() throws WrongInputTimeException {
		Set<Seance> seanses20 = new TreeSet<>();
		seanses20.add(new Seance(movie, new Time(17, 15)));
		seanses20.add(new Seance(movie, new Time(18, 15)));
		scheduletest.setSeanses(seanses20);		
		
		Time real = scheduletest.calcBreakTimeOptimizete(cinematest, movie, seance);
		Time expected = new Time(11, 16);
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void scheduleOverlayOptizateTest() throws WrongInputTimeException {
		
		Set<Seance> seanses30 = new TreeSet<>();
		seanses30.add(new Seance(movie, new Time(17, 15)));
		seanses30.add(new Seance(movie, new Time(18, 15)));
		scheduletest.setSeanses(seanses30);			
		
		scheduletest.overlayOptizate(cinematest, movie, seance, Lambda.simply);		
				
		Seance first = seanses30.stream().findFirst().get();		
		Seance last = seanses30.stream().filter(o -> !o.equals(first)).findFirst().get();
		
		Time real = first.getEndTime();
		Time expected = last.getStartTime();
		
		Assert.assertTrue(expected.equals(
				Lambda.calcOperationTime(real, new Time(0, 15), 1)));
		Assert.assertFalse(expected.equals(
				Lambda.calcOperationTime(real, new Time(0, 16), 1)));
		
		//------------------------------------------------------------
		
		scheduletest.overlayOptizate(cinematest, movie, seance, Lambda.begin);
		Seance begin = seanses30.stream().findFirst().get();
		real = begin.getStartTime();
		expected = cinematest.getOpen();		
		
		Assert.assertTrue(expected.equals(real));
		Assert.assertFalse(expected.equals(begin.getEndTime()));
		
		//-------------------------------------------------------------
		
		scheduletest.overlayOptizate(cinematest, movie, seance, Lambda.finish);
		
		Seance firstFinish = seanses30.stream().findFirst().get();		
		Seance lastFinish = seanses30.stream().filter(o -> !o.equals(firstFinish)).findFirst().get();
		
		real = lastFinish.getEndTime();
		expected = cinematest.getClose();		

		Assert.assertTrue(expected.equals(real));
		Assert.assertFalse(expected.equals(lastFinish.getStartTime()));
		
		//-----------------------------------------------------------------
		
		scheduletest.overlayOptizate(cinematest, movie, seance, Lambda.breakOptimizate);
		
		Seance firstBreak = seanses30.stream().findFirst().get();		
		Seance lastBreak = seanses30.stream().filter(o -> !o.equals(firstBreak)).findFirst().get();
		
		real = firstBreak.getStartTime();
		expected = cinematest.getOpen();
				
		Time real1 = lastBreak.getEndTime();
		Time expected1 = cinematest.getClose();
		
		Assert.assertEquals(expected, real);
		Assert.assertEquals(expected1, real1);
		
	}

	
}
