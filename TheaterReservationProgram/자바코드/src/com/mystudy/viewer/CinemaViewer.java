package com.mystudy.viewer;

import java.util.List;
import java.util.Scanner;

import com.mystudy.dao.CinemaDAO;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.CinemaVO;

/* 극장정보선택 메소드 만들기
   
 극장리스트 메소드
 1. 극장 개별정보 0 뒤로가기  -> 뒤로가기하면 다시 극장정보 선택으로 넘어가게 만들고
 
 극장 번호 선택 메소드 만들기
 
 극장 정보 출력 메소드 만들기
 abc
 ABC 
*/
public class CinemaViewer {
	private CinemaDAO cinemaDAO;
	private CinemaVO cinemaVO;
	private Scanner scanner;

	// 클래스 이름으로 생성자 메소드 만들기
	// 생성자 메소드
	public CinemaViewer() {
		cinemaDAO = new CinemaDAO();
		cinemaVO = new CinemaVO();
		scanner = new Scanner(System.in);

	}

	// 극장 정보
	public void cinemaList() {
		List<CinemaVO> list = cinemaDAO.selectAll(); // cinemaDAO 위쪽에 생성해놓은것에서 호출
		if (list.isEmpty()) {
			System.out.println("아직 등록된 극장이 없습니다.");
		}
		while (true) {
			System.out.println();
			System.out.println(" ========== 극장 리스트 ==========");
			for (CinemaVO vo : list) {
				System.out.println(" [" + vo.getCinemaId() + "] : " + vo.getRegion());
			}
			System.out.println(" ============================== ");
			System.out.println();

			int userChoice = validate();
			// String message = new String("1. 극장 개별보기 0. 뒤로가기");
			// int userChoice = ScannerUtil.nextInt(scanner, message);

			if (userChoice != 0) {
				System.out.println();

				cinemaOne(userChoice); // 극장개별보기

			} else if (userChoice == 0) {
				break;

			}
		}

	}

	// 개별극장보기
	public void cinemaOne(int CinemaId) {
		cinemaVO = cinemaDAO.selectOne(CinemaId);
		//System.out.println(cinemaVO);
		System.out.println(" ========== 극장 정보 ========== ");
		System.out.println(" " + cinemaVO.getRegion() + " - " 
						+ "전화번호: " +  cinemaVO.getcPhone());
		System.out.println(" ============================= ");
		String message = "리스트로 돌아가시려면 아무키나 입력해주세요";
		String userInput = null;
		while (true) {
			userInput = ScannerUtil.nextLine(scanner, message);
			if (userInput != null) {
				break;
			}
		}
	}
 
	// 뒤로가기
	private int validate() {
		int userInput = 0;
		String message = new String("상세보기 할 지점 번호나 뒤로가려면 0을 입력해주세요");
		userInput = ScannerUtil.nextInt(scanner, message);
		cinemaVO = cinemaDAO.selectOne(userInput);
		
		while (userInput != 0 && cinemaVO == null) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userInput = ScannerUtil.nextInt(scanner, message);
			cinemaVO = cinemaDAO.selectOne(userInput);

		}

		return userInput;

	}

}
