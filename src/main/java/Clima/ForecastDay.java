package Clima;

public class ForecastDay {
	private long date_epoch;
	private Day day;
	
	public long getDate_epoch() {
		return date_epoch;
	}
	public void setDate_epoch(long date_epoch) {
		this.date_epoch = date_epoch;
	}
	public Day getDay() {
		return day;
	}
	public void setDay(Day day) {
		this.day = day;
	}
}
