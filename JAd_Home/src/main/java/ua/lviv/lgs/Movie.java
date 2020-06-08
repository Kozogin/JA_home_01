package ua.lviv.lgs; 

import java.io.Serializable;

public class Movie implements Serializable{
	
	private static final long serialVersionUID = 2L;
	private static final int TITLE_LENGTH = 50;
	private static final int MAX_SEANSE_DURATION = 6;
	private static final int MIN_SEANSE_DURATION_MIN = 30;
	
	private String title;
	private Time duration;
	
	public Movie() {}
	
	public Movie(String title, Time duration) throws WrongInputTimeException, WrongInputLengthTitleException {
		super();
		try {
			if(title.length() > TITLE_LENGTH) {
				throw new WrongInputLengthTitleException("too long, up to " + title.length() + 
						" characters, allowed " + TITLE_LENGTH);
			} else {
				this.title = title;		
				this.duration = duration;
			}			
			if(duration.getHour() > MAX_SEANSE_DURATION || (duration.getHour() == 0 && duration.getMin() < MIN_SEANSE_DURATION_MIN)) {
				throw new WrongInputTimeException("incorrect time input by duration-> , you entered " + duration);
			}
		}catch(WrongInputTimeException e) {
///////////////e.printStackTrace();
			throw new WrongInputTimeException("erase after test");////////////////////////////
		} catch (WrongInputLengthTitleException e) {
//////////////e.printStackTrace();
			throw new WrongInputLengthTitleException("erase after test");//////////////////////////// 
		}
	}
	
	public Time calcEndTime(Time startTime) throws WrongInputTimeException {
		int hour = duration.getHour() + startTime.getHour();
		int min = duration.getMin() + startTime.getMin();
		if (min > 59) {
			min -= 60;
			hour += 1;
		}
		if (hour > 23) {
			hour -= 24;
		}
		return new Time(hour, min);
	}	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return " Movie title:   * " + title + " *,   duration:" + duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}	
	 
	
	

}

class WrongInputLengthTitleException extends Exception{
	
	private static final long serialVersionUID = 2L;
	private String parametr;

	public WrongInputLengthTitleException(String parametr) {
		super(parametr);
		this.parametr = parametr;
	}

	public String getParametr() {
		return parametr;
	}
}