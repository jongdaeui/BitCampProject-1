package com.mystudy.bookviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.mystudy.dao.BookDAO;
import com.mystudy.dao.CinemaDAO;
import com.mystudy.dao.MemberDAO;
import com.mystudy.dao.TimeTable_JoinDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.BookVO;
import com.mystudy.vo.CinemaVO;
import com.mystudy.vo.MemberVO;
import com.mystudy.vo.TimeTableVO;

public class BookViewer {

	private Scanner scanner;
	private BookCineViewer bookCine;
	private BookCheckViewer bookCheck;
	private TimeTable_JoinDAO timeJoinDAO;
	private BookDAO bookDAO;
	private BookVO bookVO;
	private Map<Integer, String> map;
	private Map<Integer, Integer> map2;
	private MenuUtil menuUtil;
	private String loginId;
	private MemberVO vo;
	private MemberDAO memberDAO;

	public void setLoginId(String loginId) {
		this.loginId = loginId;
		memberDAO = new MemberDAO();
		this.vo = memberDAO.selectOne(this.loginId);
	}

	public BookViewer() {
		scanner = new Scanner(System.in);
		bookCine = new BookCineViewer();
		bookCheck = new BookCheckViewer();
		timeJoinDAO = new TimeTable_JoinDAO();
		bookDAO = new BookDAO();
		bookVO = new BookVO();
		map = new HashMap<>();
		map2 = new HashMap<>();
		menuUtil = new MenuUtil();
	}

	public void showMenu() {

		while (true) {
			String[] menu = { " 1. 예매하기", "2. 예매확인", "3. 극장별 상영시간 확인", "0.뒤로가기" };
			int userChoice = menuUtil.selectMenu(menu, 0, menu.length - 1);

			if (userChoice == 1) {

				String title = selectMV();
				String region = selectRegion(title);
				String screenDate = selectDay(title, region);
				String screenTime = selectTime(title, region, screenDate);
				selectMix(title, region, screenDate, screenTime);

				String message = "정말 예매하시겠습니까? 1. 예 2.아니오";
				int userInput = ScannerUtil.nextInt(scanner, message, 1, 2);
				if (userInput == 1) {
					insertBook(title, region, screenDate, screenTime);
				} 

			} else if (userChoice == 2) {
				bookCheck.setLoginId(loginId);
				bookCheck.showMenu();
			} else if (userChoice == 3) {
				bookCine.cinemaList();
			} else if (userChoice == 0) {
				break;
			}
		}
	}

	public String selectMV() {
		System.out.println("----- 관람 가능 영화 -----");
		map = timeJoinDAO.movieAll();

		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}
		System.out.println();

		String message = "예매할 영화를 선택하세요.";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1);

		String title = (String) map.get(userChoice);
		if (title != null) {
			System.out.println("선택 영화 : " + title);
			System.out.println();
		}
		while (userChoice != 0 && userChoice > map.size()) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userChoice = ScannerUtil.nextInt(scanner, message);

			title = (String) map.get(userChoice);
			if (title != null) {
				System.out.println("선택 영화 : " + title);
				System.out.println();
			}
		}
		return title;
	}

	public String selectRegion(String title) {

		System.out.println("----- 관람 가능 지점 -----");
		map = timeJoinDAO.regionAll(title);

		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}

		String message = "원하시는 지점 번호 선택하세요. ";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1);

		String region = (String) map.get(userChoice);
		if (region != null) {
			System.out.println("선택 지점 : " + region);
			System.out.println();
		}

		while (userChoice != 0 && userChoice > map.size()) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userChoice = ScannerUtil.nextInt(scanner, message);

			region = (String) map.get(userChoice);
			if (region != null) {
				System.out.println("선택 지점 : " + region);
				System.out.println();
			}
		}



		return region;
	}

	public String selectDay(String title, String region) {

		System.out.println("----- 관람 가능 날짜 -----");
		map = timeJoinDAO.dateAll(title, region);

		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}

		String message = "예매 날짜 번호를 선택하세요.";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1);

		String screenDate = (String) map.get(userChoice);
		if (screenDate != null) {
			System.out.println("선택 날짜 : " + screenDate);
			System.out.println();
		}

		while (userChoice != 0 && userChoice > map.size()) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userChoice = ScannerUtil.nextInt(scanner, message);

			screenDate = (String) map.get(userChoice);
			if (screenDate != null) {
				System.out.println("선택 날짜 : " + screenDate);
				System.out.println();
			}
		}



		return screenDate;
	}

	public String selectTime(String title, String region, String screenDate) {
		System.out.println("----- 관람 가능 시간 조회 -----");
		map = timeJoinDAO.timeAll(title, region, screenDate);

		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}

		String message = "관람을 원하는 시간을 선택하세요. ";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1);

		String screenTime = (String) map.get(userChoice);
		if (screenTime != null) {
			System.out.println("선택 날짜/시간 : " + screenDate + " / " + screenTime);
			System.out.println();
		}
		while (userChoice != 0 && userChoice > map.size()) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userChoice = ScannerUtil.nextInt(scanner, message);

			screenTime = (String) map.get(userChoice);
			if (screenTime != null) {
				System.out.println("선택 날짜/시간 : " + screenDate + " / " + screenTime);
				System.out.println();
			}
		}



		return screenTime;

	}

	public void selectMix(String title, String region, String screenDate, String screenTime) {

		System.out.println("----- 관람 가능 영화 조회 -----");

		map = timeJoinDAO.tableAll(title, region, screenDate, screenTime);

		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}
		System.out.println();

	}

	public void insertBook(String title, String region, String screenDate, String screenTime) {

	      map2 = timeJoinDAO.tableIn(title, region, screenDate, screenTime);

	      int timeId = map2.get(1);
	      int movieId = map2.get(2);
	      int cinemaId = map2.get(3);
	      
	      // 조조할인
	      if (screenTime.equals("11:00")) {
	         int salePrice = 7000;
	         bookVO = new BookVO(cinemaId, movieId, this.vo.getMemberNo(), salePrice, timeId);
	      } else {
	         int price = 12000;
	         bookVO = new BookVO(cinemaId, movieId, this.vo.getMemberNo(), price, timeId);
	      }

	      bookDAO.bookinsert(bookVO);
	      int price = bookVO.getPrice();

	      System.out.println("예매에 성공했습니다.");
	      System.out.println("영화 금액은 " + price + "원으로 현장결제입니다.");
	      System.out.println();
	      
	      map = timeJoinDAO.tableAll(title, region, screenDate, screenTime);
	      System.out.println("----------- 예매 내역 ------------");
	      for (Entry<Integer, String> entrySet : map.entrySet()) {
	         System.out.println(entrySet.getValue());
	      }
	      System.out.println();
	      
	      
	   }


}
