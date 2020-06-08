package ua.lviv.lgs; 

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class Hall implements Serializable{
	
	private static final long serialVersionUID = 6L;
			
	private Time openHall ;
	private Time closeHall;
	private Time breakTime;
	
	TreeMap<Days, Schedule> schedulesHall;
	List<Movie> moviesLibraryHall; 
	
	

	public Hall() {	}

	public void objcCreate(Cinema cinema) {
		openHall = cinema.getOpen() ;
		closeHall = cinema.getClose() ;
		schedulesHall = cinema.getSchedules();
		moviesLibraryHall = cinema.getMoviesLibrary();
		//breakTime = 
	}

	public Time getOpenHall() {
		return openHall;
	}

	public Time getCloseHall() {
		return closeHall;
	}

	public TreeMap<Days, Schedule> getSchedulesHall() {
		return schedulesHall;
	}

	public List<Movie> getMoviesLibraryHall() {
		return moviesLibraryHall;
	}
	
	public Time getBreakTime() {
		return breakTime;
	}	

	@Override
	public String toString() {
		return "Hall [openHall=" + openHall + ", closeHall=" + closeHall + ", schedulesHall=" + schedulesHall
				+ ", moviesLibraryYall=" + moviesLibraryHall + "]";
	}

	
}