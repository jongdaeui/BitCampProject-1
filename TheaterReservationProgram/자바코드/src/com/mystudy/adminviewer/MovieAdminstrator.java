package com.mystudy.adminviewer;

import java.util.ArrayList;
import java.util.List;
// 영화보기 관리자메소드
// crud 다 만들기
// 관리자 입장에서 영화 정보 추가, 영화 정보 조회, 영화 정보 수정, 영화 정보 삭제
// 리스트 먼저 보여주고 1. 영화정보 만들기 2. 영화 정보조회
import java.util.Scanner;

import com.mystudy.dao.MovieInfoDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.CinemaVO;
import com.mystudy.vo.MovieInfoVO;

public class MovieAdminstrator {
	private MovieInfoDAO movieInfoDAO;
	private MovieInfoVO movieInfoVO;
	private Scanner scanner;
	private MenuUtil menuUtil;
	
	public MovieAdminstrator() {
		scanner = new Scanner(System.in);
		movieInfoDAO = new MovieInfoDAO();
		movieInfoVO = new MovieInfoVO();
		menuUtil = new MenuUtil();
	}

	public void showMenu() {
		List<MovieInfoVO> list = new ArrayList<>();
		list = movieInfoDAO.selectAll();
		if (list.isEmpty()) {
			System.out.println("아직 등록된 영화가 없습니다.");
		}
		System.out.println("-------- 개봉 영화 ---------");
		selectAllInfo();
		System.out.println();
		
		while (true) {
			String[] menu = { "1.영화정보 만들기", "2.영화정보조회", "0. 뒤로가기 "};
			int userChoice = menuUtil.selectMenu(menu, 0, menu.length -1);
			// 1번 영화정보 만들기
			if (userChoice == 1) {
				insertMovieInfo();
				System.out.println("영화 정보가 입력되었습니다.");
				System.out.println();

			// 2번 영화정보 조회하기
			} else if (userChoice == 2) {
				List<Integer> subList = selectAllMovieInfo();
				System.out.println();
				
				String message = "1. 조회하실 영화정보번호를 선택해주세요. 0. 뒤로가기"; 
				while (true) {
					int upDelBack = ScannerUtil.nextInt(scanner, message);
					if (upDelBack == 0) {
						break;
					}
					if (!subList.contains(upDelBack)) {
						System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
					}  else {
						updateOrDeleteOrback(upDelBack);
						break;
					} 
				
						
			
					
					
				}

			// 0번 뒤로가기
			} else if (userChoice == 0) {
				break;
			} 

		}
	}
	
	// 영화정보조회
	public List<Integer> selectAllMovieInfo() {
		List<MovieInfoVO> list = new ArrayList<>();
		List<Integer> intList = new ArrayList<>();
		list = movieInfoDAO.selectAll();
		for (MovieInfoVO vo : list) {
			System.out.println("영화정보: [" + vo.getMovieId() + "], 타이틀: [" + vo.getTitle() + "], "
					+ ", 등급: [" + vo.getGrade() + "]");
			intList.add(vo.getMovieId());
		}

		return intList;
	}
	
	
	// 전체 리스트 선택
	public void selectAllInfo() {
		List<MovieInfoVO> list = new ArrayList<>();
		list = movieInfoDAO.selectAll();
		for (MovieInfoVO mvo : list) {
			System.out.println("영화정보: " + mvo.getMovieId() + ", 타이틀: " + mvo.getTitle() + ", 등급: " + mvo.getGrade());
		}

	}

	// 한개 리스트 선택
	public void selectOneInfo(int movieId) {
		MovieInfoVO vo = movieInfoDAO.selectOne(movieId);
		System.out.println("영화정보: " + vo.getMovieId() + ", 타이틀: " + vo.getTitle() + ", 스토리: " + vo.getStory() + ", 등급: "
				+ vo.getGrade());

	}

	// 입력
	public void insertMovieInfo() {
		String message = new String();
		message = "타이틀을 입력해주세요";
		String title = ScannerUtil.nextLine(scanner, message);
		message = "스토리를 입력해주세요";
		String story = ScannerUtil.nextLine(scanner, message);
		message = "등급을 입력해주세요";
		String grade = ScannerUtil.nextLine(scanner, message);
		MovieInfoVO vo = new MovieInfoVO(title, story, grade);
		movieInfoDAO.mvInsert(vo);

	}
	
	// 업데이트 
	public void updateMovieInfo(int movieId) {
		String message = "";
		message = "타이틀을 입력해주세요";
		String title = ScannerUtil.nextLine(scanner, message);
		message = "스토리를 입력해주세요";
		String story = ScannerUtil.nextLine(scanner, message);
		message = "등급을 입력해주세요";
		String grade = ScannerUtil.nextLine(scanner, message);
		MovieInfoVO vo = new MovieInfoVO(movieId, title, story, grade);
		message = "정말로 수정하시겠습니까? (1. 예 2. 아니오) ";
		int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
		if (userChoice == 1) {
			movieInfoDAO.mvUpdate(vo);
			System.out.println("수정되었습니다.");
		} else if (userChoice == 2) {
			System.out.println("수정이 안됐습니다.");
		}

	}
	
	// 삭제
	public void deleteMovieInfo(int movieId) {
		String message = "정말로 삭제하시겠습니까? (1. 예 2. 아니오)";
		int yesNo = ScannerUtil.nextInt(scanner, message, 1, 2);
		if (yesNo == 1) {
			movieInfoDAO.mvDelete(movieId);
			System.out.println("삭제되었습니다.");
		} else if (yesNo == 2) {
			System.out.println("삭제되지 않았습니다.");

		}
	}
	
	public void updateOrDeleteOrback (int movieId ) {
		String message = "1. 수정 2. 삭제 0. 뒤로가기";
		int upDelBack = ScannerUtil.nextInt(scanner, message, 0, 2);
		if (upDelBack == 1) {
			updateMovieInfo(movieId);
		} else if (upDelBack == 2) {
			deleteMovieInfo(movieId);
		} else if (upDelBack == 0) {
			// 뒤로가기
		}
	}
	
}
