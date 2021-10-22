package com.mystudy.util;

import java.util.InputMismatchException;
import java.util.Scanner;



public class MenuUtil {
	private Scanner scanner;
	
	public void createMenu() {
		String[] menuList = {" 1. 입력", "2. 조회", "3. 수정", "4. 삭제"};
		selectMenu(menuList, 1, menuList.length);
	}
	
	public int selectMenu(String[] menuList, int min, int max) {
		scanner = new Scanner(System.in);
		int userChoice;
		while (true) {
			try {
				userChoice = ScannerUtil.nextInt(scanner, String.join(" ", menuList), min, max);
				scanner.nextLine();
				return userChoice;
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
//	public String printMenuList(String[] menuList) {
//		return String.join(" ", menuList);
//	}
}
