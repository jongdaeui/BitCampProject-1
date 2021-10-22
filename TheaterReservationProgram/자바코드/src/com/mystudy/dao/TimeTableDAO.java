package com.mystudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mystudy.util.CommonUtil;
import com.mystudy.vo.TimeTableVO;

public class TimeTableDAO {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	/////////////////////////////////////////////////////
	// TimeTable 단일 테이블의 정보만 조회 가능하며 연관된 MOVIE, CINEMA 정보가
	// 필요할 경우 각각 MOVIEINFO, CINEMA와 조인된 SQL문을 실행할 클래스 생성해야함.
	// 1. (등록)TimeTable 등록하기
	// 2. (수정)TimeTable 수정하기
	// 3. (조회)TimeTable 전체 리스트 조회하기
	// 4. (조회)TimeID를 이용해서 한개의 데이터 조회
	// 5. (조회)VO 객체를 이용한 한개의 데이터 조회
	// 6. (삭제)ID를 받아서 데이터 삭제처리
	///////////////////////////////////////////////////////
	
	// 1. TimeTable 등록하기
	public int insertTimeTable(TimeTableVO vo) {
		int result = 0;

		try {
			conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TIMETABLE ");
			sql.append("       (TIME_ID, MOVIE_ID, CINEMA_ID, SCREEN_DATE, SCREEN_TIME) ");
			sql.append("VALUES (?, ?, ?, ?, ?) ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, vo.getTimeId());
			pstmt.setInt(2, vo.getMovieId());
			pstmt.setInt(3, vo.getCinemaId());
			pstmt.setString(4, vo.getScreenDate());
			pstmt.setString(5, vo.getScreenTime());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	// 2. TimeTable 수정하기
	public int updateTimeTable(TimeTableVO vo) {
		int result = 0;
		
		try {
			conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE TIMETABLE ");
			sql.append("   SET MOVIE_ID = ? ");
			sql.append("     , CINEMA_ID = ? ");
			sql.append("     , SCREEN_DATE = ? ");
			sql.append("     , SCREEN_TIME = ? ");
			sql.append(" WHERE TIME_ID = ? ");
		
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, vo.getMovieId());
			pstmt.setInt(2, vo.getCinemaId());
			pstmt.setString(3, vo.getScreenDate());
			pstmt.setString(4, vo.getScreenTime());
			pstmt.setInt(5, vo.getTimeId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
	
	// 3. TimeTable 전체 리스트 조회하기
	public List<TimeTableVO> selectAll() {
		List<TimeTableVO> list = null;
			
		try {
			conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TIMETABLE ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<TimeTableVO>();
			while (rs.next()) {
				TimeTableVO vo = new TimeTableVO(
						rs.getInt("TIME_ID"),
						rs.getInt("MOVIE_ID"),
						rs.getInt("CINEMA_ID"),
						rs.getString("SCREEN_DATE"),
						rs.getString("SCREEN_TIME")
						);
				list.add(vo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	// 4. TimeID를 이용해서 한개의 데이터 조회
	public TimeTableVO selectOne (int timeId) {
		TimeTableVO vo = null;
		try {
			conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT TIME_ID, MOVIE_ID, CINEMA_ID, SCREEN_DATE, SCREEN_TIME ");
			sql.append("  FROM TIMETABLE ");
			sql.append(" WHERE TIME_ID = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, timeId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new TimeTableVO(
					rs.getInt(1),
					rs.getInt(2),
					rs.getInt(3),
					rs.getString(4),
					rs.getString(5));
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}
		return vo;
	}
	
	// 5. VO 객체를 이용한 한개의 데이터 조회
	public TimeTableVO selectOne(TimeTableVO vo) {
		return selectOne(vo.getTimeId());
	}
	
	// 6. ID를 받아서 데이터 삭제처리
	public int deleteOne(int timeId) {
		int result = 0;
		
		try {
			conn = CommonUtil.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TIMETABLE WHERE TIME_ID = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, timeId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}
		return result;
	}
}
