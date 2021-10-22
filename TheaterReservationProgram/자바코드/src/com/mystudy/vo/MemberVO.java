package com.mystudy.vo;

public class MemberVO {
	private int memberNo;
	private String memberId;
	private String memberName;
	private String password;
	private String phone;
	private String email;
	
	
	
	public MemberVO() {
	}

	public MemberVO(int memberNo, String memberId, String memberName, String password, String phone, String email) {
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberName = memberName;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MemberVO [memberNo=" + memberNo + ", memberId=" + memberId + ", memberName=" + memberName
				+ ", password=" + password + ", phone=" + phone + ", email=" + email + "]";
	}
}
