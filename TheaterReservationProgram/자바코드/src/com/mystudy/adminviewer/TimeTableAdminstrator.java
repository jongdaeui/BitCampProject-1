package com.mystudy.adminviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mystudy.dao.TimeTableDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.CinemaVO;
import com.mystudy.vo.TimeTableVO;

public class TimeTableAdminstrator {
	private TimeTableDAO timeTableDAO;
	private TimeTableVO timeTableVO;
	private Scanner scanner;
	private MenuUtil menuUtil;

	// 생성자
	public TimeTableAdminstrator() {
		scanner = new Scanner(System.in);
		timeTableDAO = new TimeTableDAO();
		timeTableVO = new TimeTableVO();
		menuUtil = new MenuUtil();
	}

	public void showMenu() {
		List<TimeTableVO> list = new ArrayList<>();
		list = timeTableDAO.selectAll();
		if (list.isEmpty()) {
			System.out.println("---- 아직 등록된 시간표가 없습니다.");
		}
		System.out.println("---- 영화 시간표 ----");
		selectAllTimeTable();
		System.out.println();

		while (true) {
			String[] menu = { "1. 영화시간표 만들기 ", "2. 영화시간표 조회 ", "0. 뒤로가기" };
			int userChoice = menuUtil.selectMenu(menu, 0, menu.length - 1);

			if (userChoice == 1) {
				// 영화 시간표 만들기
				insertTimeTable();
				System.out.println("영화 시간표가 입력되었습니다.");
				System.out.println();

			} else if (userChoice == 2) {
				// 영화 시간표 조회
				List<Integer> intList = selectAllMovieInfoMenu();
				System.out.println();

				String message = "1. 조회하실 영화시간을 입력해주세요. 0. 뒤로가기";

				while (true) {
					int upDelBack = ScannerUtil.nextInt(scanner, message);
					if (upDelBack == 0) {
						break;
					}
					if (!intList.contains(upDelBack)) {
						System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
					} else {
						updateOrDeleteOrBack(upDelBack);
						break;
					}
				}

			} else if (userChoice == 0) {
				// 0번 뒤로가기
				break;
			}
		}

	}

	// 시간조회
	public List<Integer> selectAllMovieInfoMenu() {
		List<TimeTableVO> list = new ArrayList<>();
		List<Integer> intList = new ArrayList<>();
		list = timeTableDAO.selectAll();
		for (TimeTableVO vo : list) {
			System.out.println("영화시간: [" + vo.getTimeId() + "], 영화번호: [" + vo.getMovieId() + "], 상영관: [" + vo.getCinemaId()
					+ "], 상영날짜: [" + vo.getScreenDate() + "], 상영시간: [" + vo.getScreenTime() + "]");

			intList.add(vo.getTimeId());
		}

		return intList;
	}

	// 전체리스트 선택
	public void selectAllTimeTable() {
		List<TimeTableVO> list = new ArrayList<>();
		list = timeTableDAO.selectAll();
		for (TimeTableVO tvo : list)
			System.out.println("영화시간: " + tvo.getTimeId() + ", 영화번호: " + tvo.getMovieId() + ", 상영관: "
					+ tvo.getCinemaId() + ", 상영날짜: " + tvo.getScreenDate() + ", 상영시간: " + tvo.getScreenTime());

	}

	// 리스트 한개 선택
	public void SelectOneTimeTable(int timeId) {
		TimeTableVO vo = timeTableDAO.selectOne(timeId);
		System.out.println("영화시간: " + vo.getTimeId() + ", 영화번호: " + vo.getMovieId() + ", 상영관 : " + vo.getCinemaId()
				+ ", 상영날짜: " + vo.getScreenDate() + ", 상영시간: " + vo.getScreenTime());

	}

	// 입력
	public void insertTimeTable() {
		String message = "";
		message = "영화시간을 입력해주세요.";
		int timeId = ScannerUtil.nextInt(scanner, message);
		message = "영화번호를 입력해주세요.";
		int movieId = ScannerUtil.nextInt(scanner, message);
		message = "상영관을 입력해주세요.";
		int cinemaId = ScannerUtil.nextInt(scanner, message);
		message = "상영날짜를 입력해주세요.";
		String screenDate = ScannerUtil.nextLine(scanner, message);
		message = "상영시간을 입력해주세요.";
		String screenTime = ScannerUtil.nextLine(scanner, message);
		TimeTableVO vo = new TimeTableVO(timeId, movieId, cinemaId, screenDate, screenTime);
		timeTableDAO.insertTimeTable(vo);
	}

	// 업데이트
	public void UpdateTimeTable(int moveId) {
		String message = "";
		message = "수정할 영화번호를 입력해주세요.";
		int movieId = ScannerUtil.nextInt(scanner, message);
		message = "수정할 상영관을 입력해주세요.";
		int cinemaId = ScannerUtil.nextInt(scanner, message);
		message = "수정할 상영날짜를 입력해주세요.";
		String screenDate = ScannerUtil.nextLine(scanner, message);
		message = "수정할 상영시간을 입력해주세요.";
		String screenTime = ScannerUtil.nextLine(scanner, message);
		TimeTableVO vo = new TimeTableVO(moveId, movieId, cinemaId, screenDate, screenTime);
		message = "정말로 수정하시겠습니까? 1. 예 2. 아니오";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
		if (userChoice == 1) {
			timeTableDAO.updateTimeTable(vo);
			System.out.println("수정되었습니다.");
		} else if (userChoice == 2) {
			System.out.println("수정되지 않았습니다.");
		}
	}

	// 삭제
	public void deleteTimeTable(int moveId) {
		String message = "정말로 삭제하시겠습니까? 1. 예 2. 아니오";
		int yesNo = ScannerUtil.nextInt(scanner, message, 1, 2);
		if (yesNo == 1) {
			System.out.println("삭제되었습니다.");
			timeTableDAO.deleteOne(moveId);
		} else if (yesNo == 2) {
			System.out.println("삭제되지 않았습니다.");
		}

	}

	public void updateOrDeleteOrBack(int moveId) {
		String message = "1. 수정 2. 삭제 0. 뒤로가기";
		int upDelBack = ScannerUtil.nextInt(scanner, message, 0, 2);
		if (upDelBack == 1) {
			UpdateTimeTable(moveId);
		} else if (upDelBack == 2) {
			deleteTimeTable(moveId);
		} else if (upDelBack == 0) {
			// 뒤로가기
		}
	}

}
