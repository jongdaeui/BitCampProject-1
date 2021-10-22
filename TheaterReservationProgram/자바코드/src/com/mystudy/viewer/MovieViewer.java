package com.mystudy.viewer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import com.mystudy.dao.BookJoinDAO;
import com.mystudy.dao.MovieInfoDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.MovieInfoVO;

public class MovieViewer {
	private MovieInfoDAO movieInfoDAO;
	private MovieInfoVO movieInfoVO;
	private Scanner scanner;
	private BookJoinDAO bookJoinDAO;
	private MenuUtil menuUtil;

	public MovieViewer() {
		movieInfoDAO = new MovieInfoDAO();
		movieInfoVO = new MovieInfoVO();
		bookJoinDAO = new BookJoinDAO();
		scanner = new Scanner(System.in);
		menuUtil = new MenuUtil();
		;
	}

	public void showMenuMV() {
		while (true) {
			String[] menu = { "1. 영화정보 보기", "2. 영화 순위보기", "0. 뒤로" };
			int userChoice = menuUtil.selectMenu(menu, 0, menu.length - 1);
			if (userChoice == 1) {
				printList();

			} else if (userChoice == 2) {
				printRank();

			} else if (userChoice == 0) {
				break;
			}
		}
	}

	// 영화 목록 보기
	public void printList() {

		List<MovieInfoVO> list = movieInfoDAO.selectAll();
		if (list.isEmpty()) {
			System.out.println("----------------------------");
			System.out.println("아직 등록된 영화가 없습니다.");
		}

		while (true) {
			System.out.println("========== 영화 리스트 ==========");
			for (MovieInfoVO vo : list) {
				System.out.println(vo.getMovieId() + " " + vo.getTitle());
			}
			System.out.println("=================================");
			System.out.println();

			int userInput = validateList();
			if (userInput != 0) {
				printOne(userInput);

				String message = "영화리스트로 돌아가시려면 아무키나 입력해주세요";
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
			System.out.println();

		}

	}

	private int validateList() {
		String message = new String("상세보기할 영화 번호를 입력해주세요 0. 뒤로");
		int userInput = ScannerUtil.nextInt(scanner, message);

		movieInfoVO = movieInfoDAO.selectOne(userInput);
		while (userInput != 0 && movieInfoVO == null) {
			System.out.println("해당 번호는 유효하지 않습니다.");
			userInput = ScannerUtil.nextInt(scanner, message);
			movieInfoVO = movieInfoDAO.selectOne(userInput);
		}

		return userInput;
	}

	public void printOne(int movieId) {
		movieInfoVO = movieInfoDAO.selectOne(movieId);
		System.out.println("==========================================");
		System.out.println(movieInfoVO.getTitle() + "   등급 : " + movieInfoVO.getGrade());
		System.out.println(movieInfoVO.getStory());
		System.out.println("==========================================");
	}

	public void printRank() {

		Map<Integer, String> map = bookJoinDAO.bookRank();
		System.out.println("=========== 영화 예매순위 ===========");
		System.out.println(":: 영화제목  누적예매수 ");
		for (Entry<Integer, String> entrySet : map.entrySet()) {
			System.out.println(entrySet.getKey() + ". " + entrySet.getValue());
		}
		System.out.println("=====================================");

		validateMap();

	}

	private void validateMap() {
		Map<Integer, Integer> map2 = bookJoinDAO.bookRankIn();

		while (true) {
			String message = new String("상세보기할 영화 번호나 뒤로갈려면 0을 입력해주세요.");
			int userInput = ScannerUtil.nextInt(scanner, message);

			if (userInput != 0 && map2.get(userInput) == null) {
				System.out.println("해당 번호는 유효하지 않습니다.");

			} else if (userInput == 0) {
				break;
			} else {
				int movieId = map2.get(userInput);
				printOne(movieId);
				break;
			}

		}
	}

}
