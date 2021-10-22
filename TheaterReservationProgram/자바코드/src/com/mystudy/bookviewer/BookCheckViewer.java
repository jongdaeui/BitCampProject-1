package com.mystudy.bookviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import com.mystudy.dao.BookDAO;
import com.mystudy.dao.BookJoinDAO;
import com.mystudy.dao.MemberDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.BookVO;
import com.mystudy.vo.MemberVO;

public class BookCheckViewer {
	private Scanner scanner;
	private BookDAO bookDAO;
	private BookJoinDAO bookJoinDAO;
	private Map<Integer, String> map;
	private MenuUtil menuUtil;
	private MemberDAO memberDAO;
	private String loginId;
	private MemberVO vo;

	public void setLoginId(String loginId) {
		this.loginId = loginId;
		this.vo = memberDAO.selectOne(loginId);

	}

	public BookCheckViewer() {
		scanner = new Scanner(System.in);
		bookDAO = new BookDAO();
		bookJoinDAO = new BookJoinDAO();
		menuUtil = new MenuUtil();
		memberDAO = new MemberDAO();
		map = new HashMap<Integer, String>();
		vo = new MemberVO();

	}

	public void showMenu() {
		while (true) {
			String[] menu = { "1. 나의 예매내역 확인", "2. 예매취소", "0. 뒤로" };
			int userChoice = menuUtil.selectMenu(menu, 0, menu.length - 1);

			if (userChoice == 1) {
				printBook(this.vo.getMemberNo());

			} else if (userChoice == 2) {
				printBook(this.vo.getMemberNo());
				deleteBook(this.vo.getMemberNo());
			} else {
				break;
			}
		}
	}

	public void printBook(int memberNo) {
		map = bookJoinDAO.selectAll(memberNo);
		System.out.println("----------나의 예매내역 -------------");
		if (map.isEmpty()) {
			System.out.println(" 예매내역이 존재하지 않습니다.");
		}
		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getValue());
		}
		System.out.println("----------------------------------");
	}

	public void deleteBook(int memberNo) {
		Map<Integer, String> map2 = bookJoinDAO.selectID(memberNo);

		String massage = "취소할 영화의 예매번호를 입력해주세요";
		int userChoice = ScannerUtil.nextInt(scanner, massage);
		boolean bookId = map2.containsKey(userChoice);
		// System.out.println(bookId);

		if (bookId) {
			massage = "정말로 취소하시겠습니까? 1. 예 2.아니오";
			int userInput = ScannerUtil.nextInt(scanner, massage, 1, 2);
			if (userInput == 1) {
				bookDAO.bookDelete(userChoice);
				System.out.println("예매내역이 취소되었습니다.");
			}
		}

		while (!bookId) {
			System.out.println("잘못 입력하셨습니다.");
			userChoice = ScannerUtil.nextInt(scanner, massage);
			bookId = map2.containsKey(userChoice);
			if (bookId) {
				massage = "정말로 취소하시겠습니까? 1. 예 2.아니오";
				int userInput = ScannerUtil.nextInt(scanner, massage, 1, 2);
				if (userInput == 1) {
					bookDAO.bookDelete(userChoice);
					System.out.println("예매내역이 취소되었습니다.");
				} else if (userInput == 2) {
					break;
				}
			}
		}
	}
}
