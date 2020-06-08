package ua.lviv.lgs; 

//import static java.util.stream.Collectors.toCollection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set; 
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Schedule implements Serializable{
		
	private static final long serialVersionUID = 4L;
	Set<Seance> seanses = new TreeSet<>();
	Time timeSchedule;
	
	
	public Schedule() {
	}

	public Schedule(Set<Seance> seanses) {
		super();
		this.seanses = seanses;
	}

	public void addSeance(Cinema cinema, Movie movie, Seance seance) throws WrongInputTimeException {
		
		if(cinema.moviesLibrary.size() != 0) {
			cinema.showMovie();
			System.out.println("Enter id movie for seance: ");
			
////////////int idMovie = cinema.scanInt.get();
			int idMovie = 1;////////////////////////////////
			
				if(!(idMovie < 1 || idMovie > cinema.moviesLibrary.size())){					
					Movie movieSelect = cinema.moviesLibrary.get(idMovie - 1);
					Seance first = new Seance();
					Seance last = new Seance();
					Seance seanceLast = new Seance();
					int hour = 0;
					do {
						System.out.println("Enter correct start seance hour: ");
						
////////////////////////hour = cinema.scanInt.get();
						hour = 10;//////////////////////////////////////////////
						
						seance = new Seance(movieSelect, new Time(hour, 59));				
						first = Lambda.simplyOne.getFirst(seance, cinema);
						
						seanceLast = new Seance(movieSelect, new Time(hour, 0));
						last = Lambda.levelingLastEquals.getFirst(seanceLast, cinema);				
						
					} while(!seance.equals(first) || !seanceLast.equals(last));
					
					int min = 0;	
					do {
						System.out.println("Enter correct start seance min: " );
						
////////////////////////min = cinema.scanInt.get();
						min = 30;///////////////////////////////////////////////
						
						seance = new Seance(movieSelect, new Time(hour, min));				
						first = Lambda.simplyOne.getFirst(seance, cinema);
						
						seanceLast = new Seance(movieSelect, new Time(hour, min));
						last = Lambda.levelingLastEquals.getFirst(seanceLast, cinema);
					} while(!seance.equals(first) || !seanceLast.equals(last));					
					
					seance = new Seance(movieSelect, new Time(hour, min));
					seanses.add(seance);					
										
				} else {
					System.out.println("Here is no movie with this number ");
				}
		} else {
			System.out.println("No film. At first add film");
		}
	}

	public void showSeance() {		
			int id = 0;
			for (Seance seance : seanses) {
				id++;
				System.out.println(id + " : " + seance);
			}
		
	}	

	public void remoteSeance(Cinema cinema) {
		if (seanses.size() != 0) {
			showSeance();
			System.out.println("Remove, Enter id seance to remove:");
			int idSeance = cinema.scanInt.get();
			if (seanses.size() >= idSeance) {
				int id = 0;
				Iterator<Seance> iterator = seanses.iterator();
				while (iterator.hasNext()) {
					iterator.next();
					id++; 
					if (id == idSeance) {
						iterator.remove();
					}
				}
			} else {
				System.out.println("Here is no movie with this number ");
			}
		} else {
			System.out.println("No Seances");
		}
	}
	
	public void copySeance(Cinema cinema, String dayOfWeek) {
		Days[] arrDays = Days.values();		
		boolean flag = cinema.isMonthPresent(arrDays, dayOfWeek);
		if (flag) {
			Days day = Days.valueOf(dayOfWeek.toUpperCase());
		seanses = cinema.schedules.get(day).getSeances();
		}		
	}
	
	void overlayOptizate(Cinema cinema, Movie movie, Seance seance, Optimizate typeOptimizate) throws WrongInputTimeException {
		
		Time breakTime = cinema.getBreakTime();
		List<Seance> replace = new ArrayList<>();
		Seance previous = new Seance();
		replace.addAll(seanses);
		
		Seance first = replace.stream().findFirst().orElse(previous);
		if(typeOptimizate.equals(Lambda.simply)) {
			first = Lambda.simplyOne.getFirst(first, cinema);
		} else {
			first = Lambda.levelingFirst.getFirst(first, cinema);
		}
			
		replace.remove(0);
		replace.add(0, first);			
		
		if(typeOptimizate.equals(Lambda.finish)) {
			Collections.reverse(replace);			
			Seance last = replace.stream().findFirst().orElse(previous);
			last = Lambda.levelingLast.getFirst(last, cinema);
			
			replace.remove(0);
			replace.add(0, last);					
		}
		
		if(typeOptimizate.equals(Lambda.breakOptimizate)) {
			breakTime = calcBreakTimeOptimizete(cinema, movie, seance);			
		}
		
		ListIterator<Seance> iterator = replace.listIterator();
		int id = 0;
		while(iterator.hasNext()) {			
			Seance next = iterator.next();
			
			if(id != 0) {
				Time NextStart = typeOptimizate.landslide(previous, next,  breakTime);				
				next = new Seance(next.getMovie(), NextStart);				
				iterator.set(next);
			} else {
				if(next.getStartTime().compareTo(cinema.getOpen()) != 1) {
					 next = new Seance(next.getMovie(),cinema.getOpen());										
					iterator.set(next);
				}
			}
			
			previous = new Seance(next.getMovie(),next.getStartTime());			
			id++;
		}
		
		replace = replace.stream()				
				.filter(o -> (compareOverlay( o.getEndTime(), cinema.getClose()) ))
				.collect(Collectors.toList());		
		
		seanses.clear();
		seanses.addAll(replace);
	}	
	
	Time calcBreakTimeOptimizete(Cinema cinema, Movie movie, Seance seance) throws WrongInputTimeException {
		
		Time allDuration = new Time(0, 0);		
		List<Time> duration = seanses.stream().map(o -> o.getMovie().getDuration()).collect(Collectors.toList());
		allDuration = duration.stream().reduce((s1, s2) -> {
			try {
				return Lambda.calcOperationTime(s1, s2, 1);
			} catch (WrongInputTimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return s1;
		}).get();
		
		Time allOpenOp = new Time(0, 0);
		Time allOpenCl = new Time(0, 0);
		allOpenOp = Lambda.calcOperationTime(new Time(23, 59), cinema.getOpen(), -1);		
		allOpenCl = Lambda.calcOperationTime(new Time(0, 1), cinema.getClose(), 1);
		allOpenOp = Lambda.calcOperationTime(allOpenOp, allOpenCl, 1);
		
		Time allBreaks = Lambda.calcOperationTime(allOpenOp, allDuration, -1);		
		long countSeance = seanses.stream().count();
		
		int minBreakTime = (int) ((60 * allBreaks.getHour() + allBreaks.getMin())/(countSeance - 1));
		int HourBreakTime = minBreakTime/60;
		minBreakTime -= 60 * HourBreakTime;		
		
		return new Time(HourBreakTime, minBreakTime);
	}	

	public void setSeanses(Set<Seance> seanses) {
		this.seanses = seanses;
	}

	public Set<Seance> getSeances() {
		return seanses;
	}
	
	public Time getTime() {
		return timeSchedule;		
	}

	@Override
	public String toString() {
		return "seanses: " + seanses;
	}

	public boolean compareOverlay(Time o1, Time o2) {

		int o1EndHour;
		int o1EndMin;
		int o2CloseHour;
		int o2CloseMin;

		o1EndMin = o1.getMin();
		if (o1.getHour() < 7) {
			o1EndHour = o1.getHour() + 24;
		} else {
			o1EndHour = o1.getHour();
		}

		o2CloseMin = o2.getMin();
		if (o2.getHour() < 7) {
			o2CloseHour = o2.getHour() + 24;
		} else {
			o2CloseHour = o2.getHour();
		}

		if (o1EndHour < o2CloseHour) {
			return true;
		} else if (o1EndHour > o2CloseHour) {
			return false;
		} else {
			if (o1EndMin < o2CloseMin) {
				return true;
			} else if (o1EndMin > o2CloseMin) {
				return false;
			} else {
				return true;
			}
		}
	}	

}