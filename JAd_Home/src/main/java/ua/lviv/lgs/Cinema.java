package ua.lviv.lgs; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;

public class Cinema implements Serializable {

	private static final long serialVersionUID = 5L;
	private Time open;
	private Time close;
	private Time breakTime;

	TreeMap<Days, Schedule> schedules = new TreeMap<>();
	List<Movie> moviesLibrary = new ArrayList<>();

	public Cinema() throws WrongInputTimeException {
		open = new Time(10, 0);
		close = new Time(2, 20);
		breakTime = new Time(0, 15);
	}

	public Cinema(Time open, Time close) {
		super();
		try {
			if (close.getHour() - open.getHour() > 1) {
				this.open = open;
				this.close = close;
			} else if (close.getHour() < 5 && open.getHour() - close.getHour() > 3) {
				this.open = open;
				this.close = close;
			} else {
				throw new WrongInputTimeException(
						"incorrect time input ->, you entered open: " + open + ", close: " + close);
			}
		} catch (WrongInputTimeException e) {
			e.printStackTrace();
		}
	}

	Supplier<String> scanString = () -> {
		Scanner sc = new Scanner(System.in);
		return sc.next();
	};
	
	Supplier<String> scanLine = () -> {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	};

	Supplier<Integer> scanInt = () -> {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	};

	public void showMovie() {
		try {
			int id = 0;
			for (Movie movies : moviesLibrary) {
				id++;
				System.out.println(id + " : " + movies);
			}
		} catch (NullPointerException e) {
		}
	}

	public void addMovie() throws WrongInputTimeException, WrongInputLengthTitleException {
		
//		System.out.println("Enter the name of the movie: ");
//		String titleMovie = scanLine.get();
//		System.out.println("Enter duration of movie, hour: ");
//		int hour = scanInt.get();
//		System.out.println("Enter duration of movie, min: ");
//		int min = scanInt.get();	
		
		String titleMovie = "Everest";		/////////////////////
		int hour = 1;				///////////////////////////
		int min = 52;				////////////////////////////
		
		if(moviesLibrary == null) {
			moviesLibrary = new ArrayList<>();
		}
		moviesLibrary.add(new Movie(titleMovie, new Time(hour, min)));
	}

	public void showSeance() {
		try {
			Set<Entry<Days, Schedule>> entrySet = schedules.entrySet();
			for (Entry<Days, Schedule> entry : entrySet) {
				System.out.println(entry.getKey().name() + "---");
				Set<Seance> sean = entry.getValue().getSeances();
				sean.stream().forEach(System.out::println);
			}
		} catch (NullPointerException e) {			
		}		
	}

	public void addSeance(Schedule schedule, String dayOfWeek) {
		
		Days[] arrDays = Days.values();		
		boolean flag = isMonthPresent(arrDays, dayOfWeek);
		Set<Seance> seansMono = schedule.getSeances();
		Set<Seance> seansMono2 = new TreeSet<>();
		seansMono2.addAll(seansMono);
		Schedule schedule2 = new Schedule(seansMono2);

		if (flag) {
			schedules.put(Days.valueOf(dayOfWeek.toUpperCase()), schedule2);
		}
	}

	public boolean isMonthPresent(Days[] arrDays, String dayOfWeek) {
		for (Days days : arrDays) {
			if (days.name().equalsIgnoreCase(dayOfWeek)) {
				return true;
			}
		}
////////////System.out.println("Wrong input the day of week");
		return false;
	}

	public void removeMovie(Schedule schedule) {
		if(moviesLibrary.size() != 0) {
/////////////showMovie();			
			Movie deleteMovie = remoteListMovies();
			remoteSchedulesMovie(deleteMovie);
			remoteSeancesMovie(schedule, deleteMovie);
		} else {
			System.out.println("No movie");
		}
	}

	public void removeSeance(Schedule schedule, String dayOfWeek) {
		try {
			Days[] arrDays = Days.values();
			boolean flag = isMonthPresent(arrDays, dayOfWeek);
			if (flag) {
				Days day = Days.valueOf(dayOfWeek.toUpperCase());
				Set<Seance> seancesRemove = schedules.get(day).getSeances();

				int idRemove = -1;
				while (idRemove != 0) {
					int id = 0;
					for (Seance seance : seancesRemove) {
						id++;
						System.out.println(id + " : " + seance);
					}

					System.out.println("Enter id Seanc for remove, enter 0 for exit");
					idRemove = scanInt.get();
					if(seancesRemove.size() < 1 || seancesRemove.size() < idRemove) {
						System.out.println("Here is no seance with this number ");
					}
					int idR = 0;
					Iterator<Seance> iterator = seancesRemove.iterator();
					while (iterator.hasNext()) {
						idR++;
						iterator.next();
						if (idRemove == idR) {
							iterator.remove();
						}
					}
					if(seancesRemove.size() == 0) {
						idRemove = 0;
						System.out.println("There are no seance");
					}
					
				}
				
				if (seancesRemove.size() != 0) {
					System.out.println(seancesRemove.size());
					Schedule schedule2 = new Schedule(seancesRemove);
					schedules.put(day, schedule2);
				} else {
					System.out.println(seancesRemove.size());
					schedules.remove(day);					
				}
			}
		} catch (NullPointerException e) {			
		} catch (IndexOutOfBoundsException e) {
		}
	}
	
	private Movie remoteListMovies() {

		/* remove from List<Movie> moviesLibrary */
		try {
///////////////System.out.println("Remove movie Enter number film to remove: ");
			//int idRemove = scanInt.get();
			int idRemove = 1;

			Movie deleteMovie = moviesLibrary.get(idRemove - 1);
			moviesLibrary.remove(idRemove - 1);
			moviesLibrary.forEach(System.out::println);
			return deleteMovie;
		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Here is no movie with this number ");
		}
		return null;

	}

	private void remoteSchedulesMovie(Movie deleteMovie) {
		
		/* remove from TreeMap<Days, Schedule> schedules */
		try {
		Set<Entry<Days, Schedule>> entrySet = schedules.entrySet();
		boolean deleteDaysBool = false;
		List<Days> daysDeleted = new ArrayList<>();

		for (Entry<Days, Schedule> entry : entrySet) {
			Set<Seance> sean = entry.getValue().getSeances();
			Iterator<Seance> mapIterator = sean.iterator();

			while (mapIterator.hasNext()) {
				Seance next = mapIterator.next();

				if (next.getMovie().equals(deleteMovie)) {
					mapIterator.remove();
					if (entry.getValue().getSeances().size() == 0) {
						daysDeleted.add(entry.getKey());
						deleteDaysBool = true;
					}
				}
			}
		}
		// deleted day which doesn't have seances
		if (deleteDaysBool) {
			for (Days day : daysDeleted) {
				schedules.remove(day);
			}
		}
	} catch (NullPointerException e) {
	} catch (IndexOutOfBoundsException e) {
	}

	}

	private void remoteSeancesMovie(Schedule schedule, Movie deleteMovie) {

		/* remove from Set<Seance> seanses */
		try {
		Iterator<Seance> iterator = schedule.seanses.iterator();
		while (iterator.hasNext()) {
			Seance next = iterator.next();
			if (next.getMovie().equals(deleteMovie)) {
				iterator.remove();
			}
		}
	} catch (NullPointerException e) {
	}
	}

	public Time getOpen() {
		return open;
	}

	public void setOpen(Time open) {
		this.open = open;
	}

	public Time getClose() {
		return close;
	}

	public void setClose(Time close) {
		this.close = close;
	}	

	public void setSchedules(TreeMap<Days, Schedule> schedules) {
		this.schedules = schedules;
	}

	public void setMoviesLibrary(List<Movie> moviesLibrary) {
		this.moviesLibrary = moviesLibrary;
	}

	public TreeMap<Days, Schedule> getSchedules() {
		return schedules;
	}

	public List<Movie> getMoviesLibrary() {
		return moviesLibrary;
	}
	
	public Time getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(Time breakTime) {
		this.breakTime = breakTime;
	}

	@Override
	public String toString() {
		return "Cinema open:" + open + ", close:" + close + " ";
	}
}