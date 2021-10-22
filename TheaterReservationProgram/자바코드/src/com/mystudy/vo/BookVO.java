package com.mystudy.vo;

public class BookVO {
	private int bookId;
	private int cinemaId;
	private int movieId;
	private int memberNo;
	private int price;
	private int timeId;
	
	public BookVO() {
		super();
	}


	public BookVO(int cinemaId, int movieId, int memberNo, int price, int timeId) {
		super();
		this.cinemaId = cinemaId;
		this.movieId = movieId;
		this.memberNo = memberNo;
		this.price = price;
		this.timeId = timeId;
	}


	public BookVO(int bookId, int cinemaId, int movieId, int memberNo, int price, int timeId) {
		super();
		this.bookId = bookId;
		this.cinemaId = cinemaId;
		this.movieId = movieId;
		this.memberNo = memberNo;
		this.price = price;
		this.timeId = timeId;
	}


	public int getBookId() {
		return bookId;
	}


	public void setBookId(int bookId) {
		this.bookId = bookId;
	}


	public int getCinemaId() {
		return cinemaId;
	}


	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}


	public int getMovieId() {
		return movieId;
	}


	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}


	public int getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getTimeId() {
		return timeId;
	}


	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}


	@Override
	public String toString() {
		return "BookVO [bookId=" + bookId + ", cinemaId=" + cinemaId + ", movieId=" + movieId + ", memberNo=" + memberNo
				+ ", price=" + price + ", timeId=" + timeId + "]";
	}
	
	
	
}
