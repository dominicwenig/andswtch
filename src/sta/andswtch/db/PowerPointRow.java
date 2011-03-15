package sta.andswtch.db;

import android.text.format.Time;

public class PowerPointRow {

	private Time endTime;
	private int hours;
	private int minutes;
	private int seconds;
	
	
	public PowerPointRow(Time time, int hours, int minutes, int seconds) {
		super();
		this.endTime = time;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}


	public Time getEndTime() {
		return endTime;
	}


	public int getHours() {
		return hours;
	}


	public int getMinutes() {
		return minutes;
	}


	public int getSeconds() {
		return seconds;
	}
	
	
	
	
	
	
}
