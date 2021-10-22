package com.mystudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mystudy.util.CommonUtil;
import com.mystudy.vo.BookVO;

public class BookDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public List<String> selectAll() {
		List<String> list = null;

		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.BOOK_ID, M.MEMBER_ID, I.TITLE, T.SCREEN_DATE, T.SCREEN_TIME, B.PRICE");
			sql.append(" FROM BOOK B, MOVIEINFO I, TIMETABLE T, MEMBER M");
			sql.append(" WHERE M.MEMBER_NO = B.MEMBER_NO");
			sql.append("   AND B.TIME_ID = T.TIME_ID ");
			sql.append("   AND T.MOVIE_ID = I.MOVIE_ID ");
			sql.append(" ORDER BY B.BOOK_ID");
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			list = new ArrayList<String>();
			String str = "";
			while (rs.next()) {
				int i = 1;
				str = "[" + i++ + "] " + rs.getString(1) + " / " + rs.getString(2) + " / " + rs.getString(3) + " / "
						+ rs.getString(4) + " / " + rs.getString(5) + " / " + rs.getString(6);
				list.add(str.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return list;
	}

	public void bkselectOne(int bookId) {
		// int result = 0;
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.BOOK_ID, M.MEMBER_ID, I.TITLE, T.SCREEN_DATE, T.SCREEN_TIME, B.PRICE");
			sql.append(" FROM BOOK B, MOVIEINFO I, TIMETABLE T, MEMBER M");
			sql.append(" WHERE M.MEMBER_NO = B.MEMBER_NO");
			sql.append("   AND B.TIME_ID = T.TIME_ID ");
			sql.append("   AND T.MOVIE_ID = I.MOVIE_ID ");
			sql.append("   AND B.BOOK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, bookId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rs.getInt(bookId);
				System.out.println(rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

	}

	public BookVO selectOne(int memberNo) {
		BookVO vo = null;

		try {

			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BOOK_ID, CINEMA_ID, MOVIE_ID, MEMBER_NO, PRICE, TIME_ID");
			sql.append("  FROM BOOK");
			sql.append(" WHERE BOOK_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, memberNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new BookVO(rs.getInt("BOOK_ID"), rs.getInt("CINEMA_ID"), rs.getInt("MOVIE_ID"),
						rs.getInt("MEMBER_NO"), rs.getInt("PRICE"), rs.getInt("TIME_ID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return vo;
	}

	public int bookinsert(BookVO vo) {
		int result = 0;

		try {

			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO BOOK ");
			sql.append("   (BOOK_ID, CINEMA_ID, MOVIE_ID, MEMBER_NO, PRICE, TIME_ID)");
			sql.append(" VALUES (SEQ_BOOK.NEXTVAL, ?, ?, ?, ?, ?) ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, vo.getCinemaId());
			pstmt.setInt(2, vo.getMovieId());
			pstmt.setInt(3, vo.getMemberNo());
			pstmt.setInt(4, vo.getPrice());
			pstmt.setInt(5, vo.getTimeId());
			result = pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

	public int bookUpdate(BookVO vo) {
		int result = 0;

		try {

			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE BOOK ");
			sql.append("   SET BOOK_ID = ?");
			sql.append("   	 , CINEMA_ID = ?");
			sql.append("   	 , MOVIE_ID = ?");
			sql.append("   	 , MEMBER_NO = ?");
			sql.append("   	 , PRICE = ?");
			sql.append("   	 , TIME_ID = ?");
			sql.append(" WHERE BOOK_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, vo.getBookId());
			pstmt.setInt(2, vo.getCinemaId());
			pstmt.setInt(3, vo.getMovieId());
			pstmt.setInt(4, vo.getMemberNo());
			pstmt.setInt(5, vo.getPrice());
			pstmt.setInt(6, vo.getTimeId());
			pstmt.setInt(7, vo.getBookId());

			result = pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

	public int bookUpdate(int bookId, int price) {
		int result = 0;

		try {

			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE BOOK ");
			sql.append("   SET PRICE = ?");
			sql.append(" WHERE BOOK_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, price);
			pstmt.setInt(2, bookId);
			result = pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

	public int bookDelete(int bookId) {
		int result = 0;

		try {

			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM BOOK WHERE BOOK_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, bookId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
}
