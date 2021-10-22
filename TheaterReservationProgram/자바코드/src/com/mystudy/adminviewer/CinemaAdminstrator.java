package com.mystudy.adminviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mystudy.dao.CinemaDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.CinemaVO;

public class CinemaAdminstrator {
	private CinemaDAO cinemaDAO;
	private CinemaVO cinemaVO;
	private Scanner scanner;
	private MenuUtil menuUtil;
	
	public CinemaAdminstrator() {
		scanner = new Scanner(System.in);
		cinemaDAO = new CinemaDAO();
		cinemaVO = new CinemaVO();
		menuUtil = new MenuUtil();
	}

	public void showMenu() {
		List<CinemaVO> list = new ArrayList<>();
		list = cinemaDAO.selectAll();
		if (list.isEmpty()) {
			System.out.println("상영하는 지점이 없습니다.");
		}
		System.out.println("----------상영 지점----------");
		selectAllCinema();
		System.out.println();

		while (true) {
			String[] menu = { "1.지점 만들기", "2. 지점 조회하기", "0. 뒤로가기" };
			int userChoice = menuUtil.selectMenu(menu, 0, menu.length - 1);
				// 1. 지점 만들기
			if (userChoice == 1) {
				insertCinema();
				System.out.println("극장정보가 입력되었습니다.");
				System.out.println();

			} else if (userChoice == 2) {
				// 2. 지점 조회하기
				List<Integer> intList = selectAllCinemamenu();
				System.out.println();

				String message = "1. 조회하실 지점번호를 입력해주세요. 0. 뒤로가기";

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

	// 지점조회
	public List<Integer> selectAllCinemamenu() {
		List<CinemaVO> list = new ArrayList<>();
		List<Integer> intList = new ArrayList<>();
		list = cinemaDAO.selectAll();
		for (CinemaVO vo : list) {
			System.out.println("지점번호: [" + vo.getCinemaId() + "]번" + " 지역: [" + vo.getRegion() + "] 전화번호: ["
					+ vo.getcPhone() + "]");
			intList.add(vo.getCinemaId());
		}

		return intList;
	}

	// 리스트 전체 선택
	public void selectAllCinema() {
		List<CinemaVO> list = new ArrayList<>();
		list = cinemaDAO.selectAll();
		for (CinemaVO cvo : list) {
			System.out
					.println("지점번호: " + cvo.getCinemaId() + ", 지역: " + cvo.getRegion() + ", 전화번호: " + cvo.getcPhone());
		}
	}

	// 리스트 한개 선택
	public void selectOneCinema(int cinemaId) {
		CinemaVO vo = cinemaDAO.selectOne(cinemaId);
		System.out.println("지점정보: " + vo.getCinemaId() + ", 지역: " + vo.getRegion() + ", 전화번호: " + vo.getcPhone());

	}

	// 입력
	public void insertCinema() {
		String message = "";
		message = "지역 정보를 입력해주세요.";
		String region = ScannerUtil.nextLine(scanner, message);
		message = "전화번호를 입력해주세요.";
		String cPhone = ScannerUtil.nextLine(scanner, message);
		CinemaVO vo = new CinemaVO(region, cPhone);
		cinemaDAO.insert(vo);

	}

	// 업데이트
	public void updateCinema(int cinemaId) {
		String message = "";
		message = "수정할 지역 정보를 입력해주세요.";
		String region = ScannerUtil.nextLine(scanner, message);
		message = "수정할 전화번호를 입력해주세요.";
		String cPhone = ScannerUtil.nextLine(scanner, message);
		CinemaVO vo = new CinemaVO(cinemaId, region, cPhone);
		message = "정말로 수정하시겠습니까? 1. 예 2. 아니오";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
		if (userChoice == 1) {
			cinemaDAO.update(vo);
			System.out.println("수정되었습니다.");
		} else if (userChoice == 2) {
			System.out.println("수정되지 않았습니다.");
		}

	}

	// 삭제
	public void deleteCinema(int cinemaId) {
		String message = "정말로 삭제하시겠습니까? 1. 예 2. 아니오";
		int yesNo = ScannerUtil.nextInt(scanner, message, 1, 2);
		if (yesNo == 1) {
			System.out.println("삭제되었습니다.");
			cinemaDAO.delete(cinemaId);
		} else if (yesNo == 2) {
			System.out.println("삭제되지 않았습니다.");
		}

	}

	public void updateOrDeleteOrBack(int cinemaId) {
		String message = "1. 수정 2. 삭제 0. 뒤로가기";
		int upDelBack = ScannerUtil.nextInt(scanner, message, 0, 2);
		if (upDelBack == 1) {
			// 수정
			updateCinema(cinemaId);
		} else if (upDelBack == 2) {
			// 삭제
			deleteCinema(cinemaId);
		} else if (upDelBack == 0) {
			// System.out.println("뒤로가기");
			// 뒤로가기
		}

	}

	

}
