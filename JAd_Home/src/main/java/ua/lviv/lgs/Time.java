package ua.lviv.lgs; 

import java.io.Serializable;
import java.util.Comparator;

public class Time implements Comparable<Time>, Comparator<Time>, Serializable{
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + min;
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
		Time other = (Time) obj;
		if (hour != other.hour)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;
	private int hour;
	private int min;
	
	public Time(int hour, int min) throws WrongInputTimeException {
		super();
		try {
			
		if(hour < 0 || hour > 23) {
			throw new WrongInputTimeException("incorrect session time input -> hour(0-23), you entered " + hour);
		} else {
			this.hour = hour;
		}
		if(min < 0 || min > 59) {
			throw new WrongInputTimeException("incorrect session time input -> min(0-59), you entered " + min);
		} else {
			this.min = min;
		}	
		} catch(WrongInputTimeException e) {
//			e.printStackTrace();
			throw new WrongInputTimeException("erase after test");/////////////////////////////
		} 
	}
	
	

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public String toString() {
		if(min < 10) {
			return " " + hour + ":0" + min + " ";
		}
		return " " + hour + ":" + min + " ";
	}

	@Override
	public int compareTo(Time o) {
		if (this.getHour() < 5 && o.getHour() > 4) {
			return 1;

		} else if (this.getHour() < 5 && o.getHour() < 5) {
			if (this.getHour() > o.getHour()) {
				return 1;
			} else if (this.getHour() < o.getHour()) {
				return -1;
			} else {
				if (this.getMin() > o.getMin()) {
					return 1;
				} else if (this.getMin() < o.getMin()) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		
		if(this.getHour() > 4 && o.getHour() < 5) {
			return -1;
		}
		
		if(this.getHour() > o.getHour()) {
			return 1;
		} else if(this.getHour() < o.getHour()){
			return -1;
		} else {
			if(this.getMin() > o.getMin()) {
				return 1;
			} else if(this.getMin() < o.getMin()) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public int compare(Time o1, Time o2) {
		
		if (o1.getHour() < 5 && o2.getHour() > 4) {
			return 1;

		} else if (o1.getHour() < 5 && o2.getHour() < 5) {
			if (o1.getHour() > o2.getHour()) {
				return 1;
			} else if (o1.getHour() < o2.getHour()) {
				return -1;
			} else {
				if (o1.getMin() > o2.getMin()) {
					return 1;
				} else if (o1.getMin() < o2.getMin()) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		
		if(o1.getHour() > 4 && o2.getHour() < 5) {
			return -1;
		}
		
		if(o1.getHour() > o2.getHour()) {
			return 1;
		} else if(o1.getHour() < o2.getHour()){
			return -1;
		} else {
			if(o1.getMin() > o2.getMin()) {
				return 1;
			} else if(o1.getMin() < o2.getMin()) {
				return -1;
			}
		}
		return 0;
	}	


}

class WrongInputTimeException extends Exception{
		
	private static final long serialVersionUID = 1L;
	private String parametr;

	public WrongInputTimeException(String parametr) {
		super(parametr);
		this.parametr = parametr;
	}

	public String getParametr() {
		return parametr;
	}
}