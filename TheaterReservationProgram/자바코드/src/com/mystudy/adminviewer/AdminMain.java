package com.mystudy.adminviewer;

import java.util.Scanner;

import com.mystudy.dao.MemberDAO;
import com.mystudy.util.ScannerUtil;
import com.mystudy.viewer.LoginViewer;
import com.mystudy.vo.MemberVO;

public class AdminMain {
	private Scanner scanner = new Scanner(System.in);
	private CinemaAdminstrator ca = new CinemaAdminstrator();
	private MovieAdminstrator ma = new MovieAdminstrator();
	private TimeTableAdminstrator ta = new TimeTableAdminstrator();
	private MemberDAO memberDAO = new MemberDAO();
	private LoginViewer loginViewer;
	private String loginId;
	private MemberVO vo;

	public void setLoginId(String loginId) {
		this.loginId = loginId;
		this.vo = memberDAO.selectOne(loginId);
	}
	
	
	public AdminMain() {
		
	}


	public void adminShowMenu() {
		while (true) {
			System.out.println("------------ 관리자 창 -------------");
			String message = "1. 영화정보 2. 상영관정보 3. 상영시간정보 0. 로그아웃 ";
			int userChoice = ScannerUtil.nextInt(scanner, message, 0, 3);
			if (userChoice == 1) {
				// 1. 영화정보
				ma.showMenu();
			} else if (userChoice == 2) {
				// 2. 상영관정보
				ca.showMenu();
			} else if (userChoice == 3) {
				// 3. 상영시간정보
				ta.showMenu();
			} else if (userChoice == 0) {
				loginViewer = new LoginViewer();
				loginViewer.setlogIn(null);
				break;
			}
		}

	}

}
