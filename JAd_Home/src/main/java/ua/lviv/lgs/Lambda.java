package ua.lviv.lgs; 

public class Lambda {

	static Optimizate simply = (previous, next, breakTime) -> {
		try {
			if (next.getStartTime().compareTo(calcOperationTime(previous.getEndTime(), breakTime, 1)) == -1) {
				try {
					next = new Seance(next.getMovie(), calcOperationTime(previous.getEndTime(), breakTime, 1));
				} catch (WrongInputTimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next.getStartTime();
	};

	static Optimizate begin = (previous, next, breakTime) -> {
		try {
			next = new Seance(next.getMovie(), calcOperationTime(previous.getEndTime(), breakTime, 1));
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next.getStartTime();
	};

	static Optimizate finish = (previous, next, breakTime) -> {
		try {
			next = new Seance
					(next.getMovie(), 
							calcOperationTime(
									calcOperationTime(previous.getStartTime(), breakTime, -1), 
									next.getMovie().getDuration(), -1));
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next.getStartTime();
	};
	
	
	static Optimizate breakOptimizate = (previous, next, breakTime) -> {
		try {
			next = new Seance(next.getMovie(), calcOperationTime(previous.getEndTime(), breakTime, 1));
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next.getStartTime();
	};
	

	static FirstSeance simplyOne = (next, open) -> {
		try {
			if (next.getStartTime().compareTo(calcOperationTime(open.getOpen(), new Time(0, 0), 1)) == -1) {
				next = new Seance(next.getMovie(), calcOperationTime(open.getOpen(), new Time(0, 0), 1));
			}
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next;
	};
	
	static FirstSeance levelingFirst = (next, open) -> {
			try {
				next = new Seance(next.getMovie(), calcOperationTime(open.getOpen(), new Time(0, 0), 1));
			} catch (WrongInputTimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return next;
	};
	
	static FirstSeance levelingLastEquals = (previous, close) -> {
		try {
			if (previous.getStartTime()
					.compareTo(calcOperationTime(close.getClose(), 
							previous.getMovie().getDuration(), -1)) == 1) {
				
				try {
					previous = new Seance(previous.getMovie(),
							calcOperationTime(close.getClose(), previous.getMovie().getDuration(), -1));
				} catch (WrongInputTimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return previous;
	};

	static FirstSeance levelingLast = (previous, close) -> {
		try {
			previous = new Seance
					(previous.getMovie(), 
							calcOperationTime(close.getClose(), previous.getMovie().getDuration(), -1));
		} catch (WrongInputTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return previous;
	};

	public static Time calcOperationTime(Time changeTime, Time plusMinus, int sign) throws WrongInputTimeException {
		int hour = 0;
		int min = 0;
		if (sign == 1) {
			hour = changeTime.getHour() + plusMinus.getHour();
			min = changeTime.getMin() + plusMinus.getMin();
			if (min > 59) {
				min -= 60;
				hour += 1;
			}
			if (hour > 23) {
				hour -= 24;
			}
		} else {
			hour = changeTime.getHour() - plusMinus.getHour();
			min = changeTime.getMin() - plusMinus.getMin();
			if (min < 0) {
				min += 60;
				hour -= 1;
			}
			if (hour < 0) {
				hour += 24;
			}
		}
		return new Time(hour, min);
	}

}

@FunctionalInterface
interface Optimizate {
	public Time landslide(Seance Previous, Seance Next, Time breakTime);
}

@FunctionalInterface
interface FirstSeance {
	public Seance getFirst(Seance first, Cinema cinema);
}