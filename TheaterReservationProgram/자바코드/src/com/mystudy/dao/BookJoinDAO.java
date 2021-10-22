package com.mystudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mystudy.util.CommonUtil;

public class BookJoinDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

//	public List<String> selectAll() {
//		List<String> list = null;
//
//		try {
//			conn = CommonUtil.getConnection();
//
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT B.BOOK_ID, M.MEMBER_ID, I.TITLE, T.SCREEN_DATE, T.SCREEN_TIME, B.PRICE");
//			sql.append(" FROM BOOK B, MOVIEINFO I, TIMETABLE T, MEMBER M");
//			sql.append(" WHERE M.MEMBER_NO = B.MEMBER_NO");
//			sql.append("   AND B.TIME_ID = T.TIME_ID ");
//			sql.append("   AND T.MOVIE_ID = I.MOVIE_ID ");
//			sql.append(" ORDER BY B.BOOK_ID");
//			pstmt = conn.prepareStatement(sql.toString());
//
//			rs = pstmt.executeQuery();
//
//			list = new ArrayList<String>();
//			String str = "";
//			while (rs.next()) {
//				int i = 1;
//				str = "[" + i++ + "] " + rs.getString(1) + " / " + rs.getString(2) + " / " + rs.getString(3) + " / "
//						+ rs.getString(4) + " / " + rs.getString(5) + " / " + rs.getString(6);
//				list.add(str.toString()); 
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			CommonUtil.close(conn, pstmt, rs);
//		}
//
//		return list;
//	}
//
//	public List<Integer> selectAll22(int memberNo) {
//		List<Integer> list = null;
//
//		try {
//			conn = CommonUtil.getConnection();
//
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT B.BOOK_ID");
//			sql.append("  FROM BOOK B, CINEMA C, MEMBER M, MOVIEINFO MI, TIMETABLE T ");
//			sql.append(" WHERE B.CINEMA_ID = C.CINEMA_ID AND B.MEMBER_NO = M.MEMBER_NO ");
//			sql.append("   AND B.MOVIE_ID = MI.MOVIE_ID AND B.TIME_ID = T.TIME_ID  ");
//			sql.append("   AND M.MEMBER_NO = ?  ");
//			pstmt = conn.prepareStatement(sql.toString());
//
//			pstmt.setInt(1, memberNo);
//
//			rs = pstmt.executeQuery();
//
//			list = new ArrayList<Integer>();
//			int book_Id;
//			while (rs.next()) {
//				book_Id = rs.getInt(1);
//				list.add(book_Id);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			CommonUtil.close(conn, pstmt, rs);
//		}
//
//		return list;
//	}
//
//	public List<String> selectOneAll(int memberNO) {
//		List<String> list = null;
//		try {
//			conn = CommonUtil.getConnection();
//
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT B.BOOK_ID, T.SCREEN_DATE, T.SCREEN_TIME, C.REGION, MI.TITLE ");
//			sql.append("  FROM BOOK B, CINEMA C, MEMBER M, MOVIEINFO MI, TIMETABLE T ");
//			sql.append(" WHERE B.CINEMA_ID = C.CINEMA_ID AND B.MEMBER_NO = M.MEMBER_NO ");
//			sql.append("   AND B.MOVIE_ID = MI.MOVIE_ID AND B.TIME_ID = T.TIME_ID  ");
//			sql.append("   AND M.MEMBER_NO = ?  ORDER BY T.SCREEN_DATE ");
//
//			pstmt = conn.prepareStatement(sql.toString());
//
//			pstmt.setInt(1, memberNO);
//
//			rs = pstmt.executeQuery();
//			list = new ArrayList<String>();
//			String str = "";
//
//			// int i = 1;
//			while (rs.next()) {
//				str = rs.getInt(1) + " / " + rs.getString(2) + " / " + rs.getString(3) + " / " + rs.getString(4) + " / "
//						+ rs.getString(5);
//				list.add(str.toString());
//
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			CommonUtil.close(conn, pstmt, rs);
//		}
//		return list;
//
//	}
//
//	public void bkselectOne(int bookId) {
//		// int result = 0;
//		try {
//			conn = CommonUtil.getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT B.BOOK_ID, M.MEMBER_ID, I.TITLE, T.SCREEN_DATE, T.SCREEN_TIME, B.PRICE");
//			sql.append(" FROM BOOK B, MOVIEINFO I, TIMETABLE T, MEMBER M");
//			sql.append(" WHERE M.MEMBER_NO = B.MEMBER_NO");
//			sql.append("   AND B.TIME_ID = T.TIME_ID ");
//			sql.append("   AND T.MOVIE_ID = I.MOVIE_ID ");
//			sql.append("   AND B.BOOK_ID = ?");
//			pstmt = conn.prepareStatement(sql.toString());
//
//			pstmt.setInt(1, bookId);
//
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				rs.getInt(bookId);
//				System.out.println(rs.next());
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			CommonUtil.close(conn, pstmt, rs);
//		}
//
//	}

	public Map<Integer, String> selectAll(int memberNO) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.BOOK_ID, T.SCREEN_DATE, T.SCREEN_TIME, C.REGION, MI.TITLE, B.STATUS ");
			sql.append("  FROM BOOK_LOG B, CINEMA C, MEMBER M, MOVIEINFO MI, TIMETABLE T ");
			sql.append(" WHERE B.CINEMA_ID = C.CINEMA_ID AND B.MEMBER_NO = M.MEMBER_NO ");
			sql.append("   AND B.MOVIE_ID = MI.MOVIE_ID AND B.TIME_ID = T.TIME_ID  ");
			sql.append("   AND M.MEMBER_NO = ?  ORDER BY T.SCREEN_DATE ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, memberNO);

			rs = pstmt.executeQuery();
			map = new HashMap<Integer, String>();
			String str = "";

			int i = 1;
			while (rs.next()) {
	            str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\n" + rs.getString(4) + "\t"
	                  + rs.getString(5) + "\t" + rs.getString(6);
	            map.put(i++, str);
	         }

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;

	}

	public Map<Integer, String> selectID(int memberNO) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT T.SCREEN_DATE, T.SCREEN_TIME, C.REGION, MI.TITLE, B.BOOK_ID");
			sql.append("  FROM BOOK B, CINEMA C, MEMBER M, MOVIEINFO MI, TIMETABLE T ");
			sql.append(" WHERE B.CINEMA_ID = C.CINEMA_ID AND B.MEMBER_NO = M.MEMBER_NO ");
			sql.append("   AND B.MOVIE_ID = MI.MOVIE_ID AND B.TIME_ID = T.TIME_ID  ");
			sql.append("   AND M.MEMBER_NO = ?  ORDER BY T.SCREEN_DATE ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, memberNO);

			rs = pstmt.executeQuery();
			map = new HashMap<Integer, String>();

			String str = "";
			while (rs.next()) {
				str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4);
				map.put(rs.getInt(5), str);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;

	}
	
	
	public Map<Integer, String> bookRank() {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT M.TITLE, COUNT(B.MOVIE_ID) ");
			sql.append("  FROM BOOK B, MOVIEINFO M ");
			sql.append(" WHERE B.MOVIE_ID = M.MOVIE_ID ");
			sql.append(" GROUP BY M.TITLE ");
			sql.append(" ORDER BY COUNT(B.MOVIE_ID) DESC ");
			
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			map = new HashMap<Integer, String>();

			int i = 1;
			String str = "";
			while (rs.next()) {
				str = rs.getString(1) + "\t"  +  rs.getInt(2);
				map.put(i++, str);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;

	}
	
	public Map<Integer, Integer> bookRankIn() {
		Map<Integer, Integer> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT M.MOVIE_ID, COUNT(B.MOVIE_ID) ");
			sql.append("  FROM BOOK B, MOVIEINFO M ");
			sql.append(" WHERE B.MOVIE_ID = M.MOVIE_ID ");
			sql.append(" GROUP BY M.MOVIE_ID ");
			sql.append(" ORDER BY COUNT(B.MOVIE_ID) DESC ");
			
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			map = new HashMap<Integer, Integer>();
			
			int i = 1;
			while (rs.next()) {
				int movieId = rs.getInt(1) ;
				map.put(i++, movieId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;

	}
}
