package com.mystudy.vo;

public class MovieInfoVO {
	private int movieId;
	private String title;
	private String story;
	private String grade;
	
	
	
	public MovieInfoVO() {
	}

	public MovieInfoVO(String title, String story, String grade) {
		super();
		this.title = title;
		this.story = story;
		this.grade = grade;
	}


	public MovieInfoVO(int movieId, String title, String story, String grade) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.story = story;
		this.grade = grade;
	}

	public MovieInfoVO(int movieId, String title, String grade) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.grade = grade;
	}
	
	
	public int getMovieId() {
		return movieId;
	}


	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getStory() {
		return story;
	}


	public void setStory(String story) {
		this.story = story;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}




	@Override
	public String toString() {
		return "MovieInfoVO [movieId=" + movieId + ", title=" + title + ", story=" + story + ", grade=" + grade + "]";
	}
	
	
	
}
