package com.mystudy.bookviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import com.mystudy.dao.CinemaDAO;
import com.mystudy.dao.TimeTable_JoinDAO;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.CinemaVO;

public class BookCineViewer {

	private Scanner scanner;
	private CinemaDAO cinemaDAO;
	private CinemaVO cinemaVO;
	private TimeTable_JoinDAO timeJoinDAO;
	private Map<Integer, String> map;

	public BookCineViewer() {
		scanner = new Scanner(System.in);
		cinemaDAO = new CinemaDAO();
		cinemaVO = new CinemaVO();
		timeJoinDAO = new TimeTable_JoinDAO();
		map = new HashMap<Integer, String>();
	}
	public void cinemaList() {
		List<CinemaVO> list = cinemaDAO.selectAll();
		if (list.isEmpty()) {
			System.out.println("----------------------------");
			System.out.println("아직 등록된 극장정보가 없습니다.");
		}
		
		while (true) {
			
			System.out.println("--------------------------------");
			for (CinemaVO vo : list) {
				System.out.println(vo.getCinemaId() + ". " + vo.getRegion() + " " + vo.getcPhone());
			}
			System.out.println("--------------------------------");
			
			
			int userChoice = validateList();
			if (userChoice != 0) {
				printOne(userChoice);
				cinemaTime(userChoice);

				String message = "극장리스트로 돌아가려면 아무키나 입력해주세요";
				String userIn = null;
				while (true) {
					userIn = ScannerUtil.nextLine(scanner, message);
					if (userIn != null) {
						break;
					}
				}
			} else {
				break;
			}
			
		}
	}
	
	private int validateList() {
		String message = new String("상세보기할 극장 번호를 입력해주세요 0. 뒤로 ");
		int userInput = ScannerUtil.nextInt(scanner, message);

		cinemaVO = cinemaDAO.selectOne(userInput);
		while (userInput != 0 && cinemaVO == null) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userInput = ScannerUtil.nextInt(scanner, message);
			cinemaVO = cinemaDAO.selectOne(userInput);
		}

		return userInput;
	}
	

	public void printOne(int cinemaId) {
		cinemaVO = cinemaDAO.selectOne(cinemaId);
		System.out.println(cinemaVO.getCinemaId() + ". " + cinemaVO.getRegion() + " " + cinemaVO.getcPhone());
	}

	public void cinemaTime(int cinemaId) {
		map = timeJoinDAO.cinamaAll(cinemaId);

		System.out.println("--------------- 상영정보 -------------------");

		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}
		System.out.println("--------------------------------------------");

	}
}
