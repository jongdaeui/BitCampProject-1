package com.mystudy.dao;

/*MEMBER 
- 로그인 LOGIN
- 회원가입 INSERT
- 회원정보조회 SELECT
- 회원정보수정 UPDATE
- 탈퇴 DELETE 
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mystudy.util.CommonUtil;
import com.mystudy.vo.MemberVO;

public class MemberDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 로그인만들기
	public int checkIDPassword(String inputId, String inputPW) {
		int result = 0;
		conn = CommonUtil.getConnection();

		try {
			// 입력된 계정이 관리자 계정 여부 선 확인
			if (checkAdmin(inputId, inputPW) == true) {
				return -1;
			}
			// 일반회원 여부 확인
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBER_NO, MEMBER_ID, MEMBER_NAME, PASSWORD, PHONE, EMAIL ");
			sql.append("  FROM MEMBER ");
			sql.append(" WHERE MEMBER_ID = ? ");
			sql.append("   AND PASSWORD = ? ");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPW);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return result;
	}

	// 입력된 계정이 관리자 여부 확인 (MEMBER 테이블의 MEMBER_ID 값은 UK 지정되어 유일한 값임)
	public boolean checkAdmin(String inputId, String inputPW) {
		boolean result = false;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBER_NO, MEMBER_ID, MEMBER_NAME, PASSWORD, PHONE, EMAIL ");
			sql.append("  FROM MEMBER ");
			sql.append(" WHERE MEMBER_ID = 'admin' ");
			sql.append("   AND PASSWORD = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, inputPW);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insert(MemberVO vo) {
		int result = 0;

		conn = CommonUtil.getConnection();
		while (true) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO MEMBER ");
				sql.append("	(MEMBER_NO, MEMBER_ID, MEMBER_NAME, PASSWORD, PHONE, EMAIL) ");
				sql.append("VALUES (SEQ_MEMBER.NEXTVAL, ?, ?, ?, ?, ?) ");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, vo.getMemberId());
				pstmt.setString(2, vo.getMemberName());
				pstmt.setString(3, vo.getPassword());
				pstmt.setString(4, vo.getPhone());
				pstmt.setString(5, vo.getEmail());

				result = pstmt.executeUpdate();

			} catch (SQLException e) {
				if (e.getMessage().contains("ORA-00001")) {
					result = 99;
				}
				return result;
			} finally {
				CommonUtil.close(conn, pstmt);
			}
			return result;
		}
	}

	public List<MemberVO> selectAll() {
		List<MemberVO> list = null;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBER_NO, MEMBER_ID, MEMBER_NAME, PASSWORD, PHONE, EMAIL ");
			sql.append("FROM MEMBER ");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			list = new ArrayList<MemberVO>();
			while (rs.next()) {
				MemberVO vo = new MemberVO(rs.getInt("MEMBER_NO"), rs.getString("MEMBER_ID"),
						rs.getString("MEMBER_NAME"), rs.getString("PASSWORD"), rs.getString("PHONE"),
						rs.getString("EMAIL"));
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return list;
	}

	public MemberVO selectOne(int memberNo) {
		MemberVO vo = null;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBER_NO, MEMBER_ID, MEMBER_NAME, PASSWORD, PHONE, EMAIL");
			sql.append(" FROM MEMBER ");
			sql.append(" WHERE MEMBER_NO = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, memberNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new MemberVO(rs.getInt("MEMBER_NO"), rs.getString("MEMBER_ID"), rs.getString("MEMBER_NAME"),
						rs.getString("PASSWORD"), rs.getString("PHONE"), rs.getString("EMAIL"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	public MemberVO selectOne(String memberId) {
		MemberVO vo = null;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBER_NO, MEMBER_ID, MEMBER_NAME, PASSWORD, PHONE, EMAIL");
			sql.append(" FROM MEMBER ");
			sql.append(" WHERE MEMBER_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new MemberVO(rs.getInt("MEMBER_NO"), rs.getString("MEMBER_ID"), rs.getString("MEMBER_NAME"),
						rs.getString("PASSWORD"), rs.getString("PHONE"), rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	public int update(MemberVO vo) {
		int result = 0;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE  MEMBER ");
			sql.append(" SET MEMBER_ID = ? ");
			sql.append(", MEMBER_NAME = ? ");
			sql.append(", PASSWORD = ? ");
			sql.append(", PHONE = ?");
			sql.append(", EMAIL = ?");
			sql.append("WHERE MEMBER_NO = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getMemberId());
			pstmt.setString(2, vo.getMemberName());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getEmail());
			pstmt.setInt(6, vo.getMemberNo());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

	public int delete(int memberNo) {
		int result = 0;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM MEMBER WHERE MEMBER_NO = ? 	");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, memberNo);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;

	}

}
