package com.mystudy.viewer;

import com.mystudy.util.MenuUtil;

public class MainViewer {
	MenuUtil menuUtil;
	MovieViewer movieViewer;
	CinemaViewer cinemaViewer;
	LoginViewer loginViewer;
	
	
	
	public MainViewer() {
		menuUtil = new MenuUtil();
		movieViewer = new MovieViewer();
		cinemaViewer = new CinemaViewer();
		loginViewer = new LoginViewer();
	}



	public void startMain() {
		System.out.println();
		System.out.println(" ========== [비트박스] 프로그램 시작 ========== ");
		String[] menuList = {" 1. 영화 정보", "2. 극장 정보", "3. 예매하기", "[0] 프로그램 종료"};
		while (true) {
			int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
			if (userChoice == 1) {
				// 비회원 영화 조회 (MovieViewer)
				movieViewer.showMenuMV();
			}
			
			if (userChoice == 2) {
				// 비회원 극장 정보 (CinemaViewer)
				cinemaViewer.cinemaList();
			}
			
			if (userChoice == 3) {
				// 예매를 위한 로그인 메뉴로 접속 (LoginViewer)
				loginViewer.LoginMain();
			}
			
			if (userChoice == 0) {
				// 접속 종료
				System.out.println(" ========== [비트박스] 프로그램 종료 ========== ");
				break;
			}
		}
	}
}
