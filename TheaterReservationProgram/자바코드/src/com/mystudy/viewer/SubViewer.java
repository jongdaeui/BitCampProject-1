package com.mystudy.viewer;

import com.mystudy.bookviewer.BookViewer;
import com.mystudy.util.MenuUtil;

public class SubViewer {
	private MenuUtil menuUtil;
	private BookViewer bookViewer;
	private MemberViewer memberViewer;
	private LoginViewer loginViewer;
	private String loginId;

	public void setLoginId(String loginId) {
		this.loginId = loginId;
		if (this.loginId == null) {
			System.out.println(" 로그아웃 상태입니다.");
		}
		System.out.println(" " + this.loginId + "님 ! 반갑습니다.");
		System.out.println();
	}

	public SubViewer() {
		menuUtil = new MenuUtil();
		bookViewer = new BookViewer();
		memberViewer = new MemberViewer();
	}

	public void subMenu() {
		String[] menuList = {" 1. 예매", "2. 회원정보", "[0] 로그아웃"};
		while (true) {
			int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
			if (userChoice == 1) {
				bookViewer.setLoginId(loginId);
				bookViewer.showMenu();
			}
			if (userChoice == 2) {
				memberViewer.setLoginId(loginId);
				memberViewer.MemberMenu();
			}
			if (userChoice == 0) {
				loginViewer = new LoginViewer();
				loginViewer.setlogIn(null);
				break;
			}
		}
	}
}
