package com.mystudy.viewer;

import java.util.Scanner;

import com.mystudy.adminviewer.AdminMain;
//import com.mystudy.admin.AdminMain;
import com.mystudy.dao.MemberDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.MemberVO;

public class LoginViewer {
	private MenuUtil menuUtil;
	private Scanner scanner;
	private MemberDAO memberDAO;
	private MemberVO vo;
	private SubViewer subViewer;
	private AdminMain adminMain;
	private String loginId;

	public void setlogIn(String loginId) {
		this.loginId = loginId;
	}

	public LoginViewer() {
		menuUtil = new MenuUtil();
		scanner = new Scanner(System.in);
		memberDAO = new MemberDAO();

	}

	// ==================================================================================
	// 예매를 선택한 후 첫 로그인 메인 메뉴
	public void LoginMain() {
		System.out.println();
		System.out.println(" :::[예매를 하기 위해서 로그인이 필요합니다.]:::");
		String[] menuList = {" 1. 로그인", "2. 회원가입", "[0] 뒤로가기"}; // 메뉴 리스트 생성

		while (true) {
			int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1); // 생성된 메뉴 리스트를 이용, 유저의 메뉴 선택

			// 로그인을 선택한 경우 (시작)
			if (userChoice == 1) {
				int loginResult = checkAccount(); // checkAccount() - 계정 검증 메소드
				System.out.println((loginResult == 1) ? " 로그인 성공!! " : ""); // checkAccount() 메소드 결과 출력

				// --------- 일반 회원 클래스로 이동
				if (loginResult == 1) {
					subViewer = new SubViewer();
					subViewer.setLoginId(loginId);
					subViewer.subMenu();

				}
				// --------- 관리자 클래스로 이동
				if (loginResult == -1) {
					adminMain = new AdminMain();
					adminMain.setLoginId(loginId);
					adminMain.adminShowMenu();
				}

				// 회원가입을 선택한 경우
			} else if (userChoice == 2) {
				System.out.println((insertInfo() == 1) ? " 회원가입 성공!! " : "");

				// 뒤로가기를 선택한 경우 Break문으로 반복문 탈출 이후 LoginMenu를 호출한 곳으로 돌아감
			} else if (userChoice == 0)
				break;
		}
	}
	// ======================================================================================

	// 아이디, 비밀번호 입력 요청 메소드
	public int checkAccount() {
		System.out.println();
		System.out.println(" ========== 로그인 ==========");
		String messageID = " 아이디를 입력해주세요.", messagePW = " 비밀번호를 입력해주세요.";
		while (true) {
			// 아이디, 비밀번호 입력 요청 / input변수에 저장
			String inputID = ScannerUtil.nextLineNN(scanner, messageID);
			String inputPW = ScannerUtil.nextLineNN(scanner, messagePW);
			System.out.println();
			// 입력된 input 값 검증 메소드
			int checkResult = memberDAO.checkIDPassword(inputID, inputPW);

			// 리턴값이 1일 경우 (검증성공)
			if (checkResult == 1) {
				loginId = inputID; // login 변수에 검증 완료된 ID를 저장
				return 1; // 1 리턴
			}

			// 리턴값이 -1일 경우 (관리자계정 확인)
			if (checkResult == -1) {
				return -1;
			} else {
				int resultIncorrect = incorrectAccount(messageID, messagePW);
				if (resultIncorrect != 1) {
					return 0;
				}
			}
		}
	}

	// 잘못된 계정을 입력 한 경우 재시도, 회원가입, 뒤로가기를 실행하는 메소드
	public int incorrectAccount(String insertId, String insertPW) {
		System.out.println();
		System.out.println(" 회원정보를 찾을 수 없습니다.");
		System.out.println(" ID와 PASSWORD를 확인해주세요	");
		String[] menuList = { "1. 재시도", "2. 회원가입", "[0] 뒤로가기" };
		int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
		while (userChoice != 0) {
			if (userChoice == 1) {
				return 1;
			}
			if (userChoice == 2) {
				System.out.println((insertInfo() == 1) ? " 회원가입 성공!! " : " 회원가입 실패");
				return 0;
			}
		}
		return 0;
	}

	// 회원가입을 진행하는 메소드
	public int insertInfo() {
		int result = 0;
		System.out.println();
		String requestId = " 아이디를 입력해주세요	(필수) ";
		String requestName = " 이름을 입력해주세요(필수) ";
		String requestPW = " 비밀번호를 입력해주세요(필수) ";
		String requestPhone = " 연락처를 입력해주세요 ";
		String requestEmail = " 이메일주소를 입력해주세요 ";
		while (true) {
			System.out.println(" ========== 회원가입 ==========");
			String userInsertId = ScannerUtil.nextLineNN(scanner, requestId);
			String userInsertName = ScannerUtil.nextLineNN(scanner, requestName);
			String userInsertPW = ScannerUtil.nextLineNN(scanner, requestPW);
			String userInsertPhone = ScannerUtil.nextLine(scanner, requestPhone);
			String userInsertEmail = ScannerUtil.nextLine(scanner, requestEmail);
			vo = new MemberVO(0, userInsertId, userInsertName, userInsertPW, userInsertPhone, userInsertEmail);

			System.out.println(" ===== 회원 가입 신청 정보 =====");
			System.out.println(" 아이디 : " + userInsertId);
			System.out.println(" 이 름 : " + userInsertName);
			System.out.println(" 비밀번호 : " + userInsertPW);
			System.out.println(" 연락처 : " + userInsertPhone);
			System.out.println(" 이메일 : " + userInsertEmail);
			System.out.println(" ==========================");

			int resultInput = JoinMember(vo);
			if (resultInput == 1) {
				return 1;
			}
			if (resultInput == 0) {
				System.out.println(" -- 회원가입 취소 --");
				return result;
			}
			if (resultInput == 2) {	
				
			}
		}
	}

	public int JoinMember(MemberVO vo) {
		System.out.println(" 입력하신 정보로 회원가입을 진행하시겠습니까??");
		String[] menuList = { "1. 예", "2.재입력", "3. 회원가입 취소" };
		while (true) {
			int userChoice = menuUtil.selectMenu(menuList, 1, 3);
			if (userChoice == 1) {
				int resultJoin = memberDAO.insert(vo);
				if (resultJoin == 1) {
					System.out.println(" 회원가입이 완료 되었습니다.");
					return 1;
				}
				if (resultJoin == 99) {
					System.out.println(" 이미 사용중인 아이디 입니다. 다른 아이디를 입력해주세요");
					return 2;
				}
			}
			if (userChoice == 2) {
				return 2;
			}
			if (userChoice == 3) {
				return 0;
			}
		}
	}
}