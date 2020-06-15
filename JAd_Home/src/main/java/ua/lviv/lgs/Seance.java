package ua.lviv.lgs;  

import java.io.Serializable;

public class Seance implements Comparable<Seance>, Serializable{
	
	private static final long serialVersionUID = 5L;
	private Movie movie;
	private Time  startTime;
	private Time  endTime;	
	
	public Seance() {}
	
	public Seance(Movie movie, Time startTime) throws WrongInputTimeException {
		super();
		this.movie = movie;
		this.startTime = startTime;
		this.endTime = movie.calcEndTime(startTime); 		
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime() throws WrongInputTimeException {
		this.endTime = movie.calcEndTime(startTime);
	}	

	@Override
	public String toString() {
		return "Seanse " + movie + ", startTime:" + startTime + ", endTime:" + endTime + " ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Seance other = (Seance) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public int compareTo(Seance o) {
		
		if (this.getStartTime().getHour() < 5 && o.getStartTime().getHour() > 4) {
			return 1;

		} else if (this.getStartTime().getHour() < 5 && o.getStartTime().getHour() < 5) {
			if (this.getStartTime().getHour() > o.getStartTime().getHour()) {
				return 1;
			} else if (this.getStartTime().getHour() < o.getStartTime().getHour()) {
				return -1;
			} else {
				if (this.getStartTime().getMin() > o.getStartTime().getMin()) {
					return 1;
				} else if (this.getStartTime().getMin() < o.getStartTime().getMin()) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		
		if(this.getStartTime().getHour() > 4 && o.getStartTime().getHour() < 5) {
			return -1;
		}
		
		if(this.getStartTime().getHour() > o.getStartTime().getHour()) {
			return 1;
		} else if(this.getStartTime().getHour() < o.getStartTime().getHour()){
			return -1;
		} else {
			if(this.getStartTime().getMin() > o.getStartTime().getMin()) {
				return 1;
			} else if(this.getStartTime().getMin() < o.getStartTime().getMin()) {
				return -1;
			}
		}		
		return 0;
	}
	
}
