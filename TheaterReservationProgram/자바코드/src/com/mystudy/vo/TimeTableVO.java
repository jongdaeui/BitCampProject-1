package com.mystudy.vo;

public class TimeTableVO {
	private int timeId;
	private int movieId;
	private int cinemaId;
	private String screenDate;
	private String screenTime;
	
	
	
	public TimeTableVO() {
	}



	public TimeTableVO(int timeId, int movieId, int cinemaId, String screenDate, String screenTime) {
		this.timeId = timeId;
		this.movieId = movieId;
		this.cinemaId = cinemaId;
		this.screenDate = screenDate;
		this.screenTime = screenTime;
	}

	

	public int getTimeId() {
		return timeId;
	}



	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}



	public int getMovieId() {
		return movieId;
	}



	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}



	public int getCinemaId() {
		return cinemaId;
	}



	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}



	public String getScreenDate() {
		return screenDate;
	}



	public void setScreenDate(String screenDate) {
		this.screenDate = screenDate;
	}



	public String getScreenTime() {
		return screenTime;
	}



	public void setScreenTime(String screenTime) {
		this.screenTime = screenTime;
	}



	@Override
	public String toString() {
		return "TimeTable [timeId=" + timeId + ", movieId=" + movieId + ", cinemaId=" + cinemaId + ", screenDate="
				+ screenDate + ", screenTime=" + screenTime + "]";
	}
	
	
}
