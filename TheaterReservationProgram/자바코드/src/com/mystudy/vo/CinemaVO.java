package com.mystudy.vo;	

public class CinemaVO {
	private int cinemaId;
	private String region;
	private String cPhone;
	
	
	public CinemaVO() {
	}

	public CinemaVO(String region, String cPhone) {
		super();
		this.region = region;
		this.cPhone = cPhone;
	}

	public CinemaVO(int cinemaId, String region, String cPhone) {
		super();
		this.cinemaId = cinemaId;
		this.region = region;
		this.cPhone = cPhone;
	}
	
	
	public int getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getcPhone() {
		return cPhone;
	}
	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}
	
	
	@Override
	public String toString() {
		return "CinemaVO [cinemaId=" + cinemaId + ", region=" + region + ", cPhone=" + cPhone + "]";
	}
	
	
	
}
