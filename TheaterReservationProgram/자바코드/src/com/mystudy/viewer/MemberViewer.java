package com.mystudy.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.mystudy.bookviewer.BookCheckViewer;
import com.mystudy.dao.BookJoinDAO;
import com.mystudy.dao.MemberDAO;
import com.mystudy.util.MenuUtil;
import com.mystudy.util.ScannerUtil;
import com.mystudy.vo.MemberVO;

public class MemberViewer {
	private Scanner scanner;
	private MenuUtil menuUtil;
	private MemberDAO memberDAO;
	private BookJoinDAO bookJoinDAO;
	private String loginId;
	private MemberVO vo;
	private SubViewer subViewer;
	private BookCheckViewer bookCheckViewer;

	public MemberViewer() {
		scanner = new Scanner(System.in);
		menuUtil = new MenuUtil();
		memberDAO = new MemberDAO();
		bookJoinDAO = new BookJoinDAO();

	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
		this.vo = memberDAO.selectOne(loginId);
	}

	public void MemberMenu() {
		String[] menuList = { " 1. 정보확인", "2. 정보수정", "3. 이용내역", "[0] 뒤로가기" };
		while (true) {
			System.out.println();
			int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
			if (userChoice == 1) {
				// 정보확인
				printInfo();
			}
			if (userChoice == 2) {
				// 정보수정
				updateMenu();
			}
			if (userChoice == 3) {
				// 이용내역
				bookCheckViewer = new BookCheckViewer();
				bookCheckViewer.printBook(this.vo.getMemberNo());
			}
			if (userChoice == 0) {
				break;

			}
		}
	}

	public void printInfo() {
		System.out.println();
		System.out.println(" ========= 회원 정보 ==========");
		System.out.println(" 회원 아이디 : " + this.vo.getMemberId());
		System.out.println(" 회원 이름  : " + this.vo.getMemberName());
//		System.out.println("회원 비밀번호 : " + vo.getPassword());
		String userPhone = this.vo.getPhone();
		if (userPhone == null) {
			userPhone = "";
		}
		String userEmail = this.vo.getEmail();
		if (userEmail == null) {
			userEmail = "";
		}
		System.out.println(" 회원 연락처 : " + userPhone);
		System.out.println(" 회원 이메일 : " + userEmail);
		System.out.println(" =============================");
		System.out.println();
		String message = " 메뉴로 돌아가시려면 아무키나 입력해주세요";
		String userInput = null;
		while (true) {
			userInput = ScannerUtil.nextLine(scanner, message);
			if (userInput != null) {
				break;
			}
		}
	}

	public void updateMenu() {
		// 항목별 수정 희망 여부 확인
		String[] menuList = { " 1. 정보 수정", "2. 비밀번호 수정", "[0] 뒤로가기" };

		while (true) {
			System.out.println();
			int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
			if (userChoice == 1) {
				int resultCheck = checkAccount();
				if (resultCheck == 1) {
					updateInfo();
				}
			}
			if (userChoice == 2) {
				int resultCheck = checkAccount();
				if (resultCheck == 1) {
					updatePassword();
				}
			}
			if (userChoice == 0) {
				break;
			}
		}
	}

	public int checkAccount() {
		System.out.println();
		System.out.println(" !! 계정 보호를 위한 본인 확인을 진행합니다. !!");
		while (true) {
			String checkPassword = ScannerUtil.nextLineNN(scanner, " 비밀번호를 입력해주세요.");
			System.out.println();
			int checkResult = memberDAO.checkIDPassword(this.vo.getMemberId(), checkPassword);
			if (checkResult == 1) {
				System.out.println(" 본인 확인 완료!! ");
				return 1;
			} else {
				System.out.println(" 비밀번호를 확인해주세요.");
				String[] menuList = { " 1. 재시도", "[0] 뒤로가기" };
				int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
				if (userChoice == 0) {
					return 0;
				}
			}
		}
	}

	public void updateInfo() {
		System.out.println();
		System.out.println(" ========== 정보수정 시작 ==========");
		while (true) {
			String inputName = ScannerUtil.nextLineNN(scanner,
					" 변경하실 이름을 입력해주세요. \n 현재 이름 : " + this.vo.getMemberName());
			
			String userPhone = this.vo.getPhone();
			if (userPhone == null) {
				userPhone = "";
			}	
			String inputPhone = ScannerUtil.nextLine(scanner,
					"\n 변경하실 연락처를 입력해주세요. \n 현재 연락처 : " + userPhone);
			
			String userEmail = this.vo.getEmail();
			if (userEmail == null) {
				userEmail = "";
			}
			String inputEmail = ScannerUtil.nextLine(scanner,
					"\n 변경하실 이메일 주소를 입력해주세요.  \n 현재 이메일 : " + userEmail);
			System.out.println();

			
			
			System.out.println(" ========== 정보 수정 요청 ==========");
			System.out.println(" 이 름  : " + inputName);
			System.out.println(" 연락처  : " + inputPhone);
			System.out.println(" 이메일  : " + inputEmail);
			System.out.println(" =================================");

			System.out.println();
			System.out.println(" 입력하신 정보로 수정하겠습니까?");
			System.out.println();
			String[] menuList = { " 1. 예", "2. 재입력", "[0] 취소 후 뒤로가기" };
			int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
			if (userChoice == 1) {
				MemberVO vo = new MemberVO(this.vo.getMemberNo(), this.vo.getMemberId(), inputName,
						this.vo.getPassword(), inputPhone, inputEmail);
				int resultUpdate = memberDAO.update(vo);
				if (resultUpdate == 1) {
					System.out.println();
					setLoginId(vo.getMemberId());
					System.out.println();
					System.out.println(" 입력하신 정보로 수정이 완료되었습니다.");
					printInfo();
					break;
				}
			}
			if (userChoice == 0) {
				System.out.println(" 정보 수정 취소!! ");
				break;
			}
		}
	}

	public void updatePassword() {
		System.out.println();
		System.out.println(" ========== 비밀번호 수정 시작 =========");
		while (true) {
			String inputPassword = ScannerUtil.nextLineNN(scanner, " 변경하실 비밀번호을 입력해주세요.");
			String inputPassword2 = ScannerUtil.nextLineNN(scanner, " 변경하실 비밀번호를 한번 더 입력해주세요.");
			if (inputPassword.equals(inputPassword2)) {
				System.out.println(" 입력하신 정보로 수정하겠습니까?");
				String[] menuList = { " 1. 예", "2. 재입력", "[0] 취소 후 뒤로가기" };
				int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
				if (userChoice == 1) {
					MemberVO vo = new MemberVO(this.vo.getMemberNo(), this.vo.getMemberId(), this.vo.getMemberName(),
							inputPassword, this.vo.getPhone(), this.vo.getEmail());
					int resultUpdate = memberDAO.update(vo);
					if (resultUpdate == 1) {
						setLoginId(this.vo.getMemberId());
						System.out.println(" 입력하신 정보로 수정이 완료되었습니다.");
						printInfo();
						break;
					}
				}
				if (userChoice == 0) {
					System.out.println("정보 수정 취소");
					break;
				}
			} else {
				System.out.println("입력된 비밀번호를 확인 해주세요");
				String[] menuList = { "1. 재입력", "[0] 취소 후 뒤로가기" };
				int userChoice = menuUtil.selectMenu(menuList, 0, menuList.length - 1);
				if (userChoice == 0) {
					break;
				}
			}
		}
	}
}
