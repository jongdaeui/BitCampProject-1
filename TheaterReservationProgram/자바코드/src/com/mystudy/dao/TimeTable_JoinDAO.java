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

public class TimeTable_JoinDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	///////////////////////////////////////////////////////////////
	// 1. 관람가능한 전체 날짜 조회 - dateAll : List<String>
	// 2. 해당 날짜에 관람 가능한 전체 시간 조회 - timeAll : List<String>
	// 3. 해당 날짜, 시간에 관람 가능한 전체 영화 조회 - movieAll : List<String>
	//////////////////////////////////////////////////////////////

	// 1. 영화 선택
	public Map<Integer, String> movieAll() {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT TITLE  ");
			sql.append("  FROM MOVIEINFO ");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			map = new HashMap<>();
			int i = 1;
			while (rs.next()) {
				String str = "";
				str = rs.getString(1);
				map.put(i++, str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;
	}

	// 2. 예매 지역 보기
	public Map<Integer, String> regionAll(String title) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT C.REGION ");
			sql.append("  FROM TIMETABLE T, MOVIEINFO M, CINEMA C ");
			sql.append(" WHERE T.MOVIE_ID = M.MOVIE_ID ");
			sql.append("   AND T.CINEMA_ID = C.CINEMA_ID  ");
			sql.append("   AND M.TITLE = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, title);

			rs = pstmt.executeQuery();

			map = new HashMap<>();
			int i = 1;
			while (rs.next()) {
				String str = "";
				str = rs.getString(1);
				map.put(i++, str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;
	}

	// 3. 관람가능한 전체 날짜 조회
	public Map<Integer, String> dateAll(String title, String region) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT T.SCREEN_DATE, C.REGION ");
			sql.append("  FROM TIMETABLE T, MOVIEINFO M, CINEMA C ");
			sql.append(" WHERE T.MOVIE_ID = M.MOVIE_ID ");
			sql.append("   AND T.CINEMA_ID = C.CINEMA_ID ");
			sql.append("   AND M.TITLE = ? AND C.REGION = ?  ");
			sql.append(" ORDER BY T.SCREEN_DATE  ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, region);

			rs = pstmt.executeQuery();

			map = new HashMap<>();
			int i = 1;
			while (rs.next()) {
				String str = "";
				str = rs.getString(1);
				map.put(i++, str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;
	}

	// 4. 해당 날짜에 관람 가능한 전체 시간표 조회
	public Map<Integer, String> timeAll(String title, String region, String date) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT T.SCREEN_DATE, T.SCREEN_TIME ");
			sql.append("  FROM TIMETABLE T, MOVIEINFO M, CINEMA C ");
			sql.append(" WHERE T.MOVIE_ID = M.MOVIE_ID AND T.CINEMA_ID = C.CINEMA_ID ");
			sql.append("   AND M.TITLE = ? AND C.REGION = ? AND T.SCREEN_DATE = ?  ");
			sql.append(" ORDER BY T.SCREEN_TIME  ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, region);
			pstmt.setString(3, date);

			rs = pstmt.executeQuery();

			map = new HashMap<>();
			String str = "";
			int i = 1;
			while (rs.next()) {
				str = rs.getString(2);
				map.put(i++, str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;
	}

	// 해당 날짜, 시간에 관람 가능한 전체 영화 조회
	public Map<Integer, String> tableAll(String title, String region, String date, String time) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT DISTINCT T.SCREEN_DATE, T.SCREEN_TIME, C.REGION, M.TITLE, T.TIME_ID, T.MOVIE_ID, C.CINEMA_ID ");
			sql.append("  FROM TIMETABLE T, MOVIEINFO M, CINEMA C ");
			sql.append(" WHERE T.MOVIE_ID = M.MOVIE_ID AND T.CINEMA_ID = C.CINEMA_ID ");
			sql.append("   AND M.TITLE = ? AND C.REGION = ?  ");
			sql.append("   AND T.SCREEN_DATE = ?  AND T.SCREEN_TIME = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, region);
			pstmt.setString(3, date);
			pstmt.setString(4, time);

			rs = pstmt.executeQuery();
			map = new HashMap<>();
			String str = "";

			int i = 1;
			while (rs.next()) {
				str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4);
				map.put(i++, str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;
	}

	public Map<Integer, String> cinamaAll(int cinemaId) {
		Map<Integer, String> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT M.TITLE, T.SCREEN_DATE, T.SCREEN_TIME ");
			sql.append("  FROM CINEMA C, TIMETABLE T, MOVIEINFO M ");
			sql.append(" WHERE C.CINEMA_ID = T.CINEMA_ID ");
			sql.append("   AND T.MOVIE_ID = M.MOVIE_ID ");
			sql.append("   AND C.CINEMA_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, cinemaId);

			rs = pstmt.executeQuery();
			map = new HashMap<>();
			String str = "";
			int i = 1;
			while (rs.next()) {
				str = rs.getString(1) + " / " + rs.getString(2) + " / " + rs.getString(3);
				map.put(i++, str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;

	}

	public Map<Integer, Integer> tableIn(String title, String region, String date, String time) {
		Map<Integer, Integer> map = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT T.SCREEN_DATE, T.SCREEN_TIME, C.REGION, M.TITLE, T.TIME_ID, T.MOVIE_ID, C.CINEMA_ID ");
			sql.append("  FROM TIMETABLE T, MOVIEINFO M, CINEMA C ");
			sql.append(" WHERE T.MOVIE_ID = M.MOVIE_ID AND T.CINEMA_ID = C.CINEMA_ID ");
			sql.append("   AND M.TITLE = ? AND C.REGION = ?  ");
			sql.append("   AND T.SCREEN_DATE = ?  AND T.SCREEN_TIME = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, region);
			pstmt.setString(3, date);
			pstmt.setString(4, time);

			rs = pstmt.executeQuery();
			map = new HashMap<>();
			int i = 1;
			while (rs.next()) {
				int timeId = rs.getInt(5);
				int movieId = rs.getInt(6);
				int cinemaId = rs.getInt(7);

				map.put(i++, timeId);
				map.put(i++, movieId);
				map.put(i++, cinemaId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return map;

	}

}
