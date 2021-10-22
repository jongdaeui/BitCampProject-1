package com.mystudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mystudy.util.CommonUtil;
import com.mystudy.vo.MovieInfoVO;

public class MovieInfoDAO {
//	전체 영화 리스트 
//	개별 영화 리스트
//	영화정보 등록
//	영화정보 수정
//	영화정보 삭제 

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public List<MovieInfoVO> selectAll() {
		List<MovieInfoVO> list = null;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIE_ID, TITLE, GRADE");
			sql.append("  FROM MOVIEINFO ");
			sql.append(" ORDER BY MOVIE_ID");

			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			list = new ArrayList<MovieInfoVO>();
			while (rs.next()) {
				MovieInfoVO vo = new MovieInfoVO(rs.getInt("MOVIE_ID"), rs.getString("TITLE"), rs.getString("GRADE"));
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return list;
	}

	public MovieInfoVO selectOne(int movieId) {
		MovieInfoVO vo = null;

		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT MOVIE_ID, TITLE, STORY, GRADE ");
			sql.append("  FROM MOVIEINFO ");
			sql.append(" WHERE MOVIE_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, movieId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new MovieInfoVO(rs.getInt("MOVIE_ID"), rs.getString("TITLE"), rs.getString("STORY"),
						rs.getString("GRADE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return vo;
	}

	public int mvInsert(MovieInfoVO vo) {
		int result = 0;
		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO MOVIEINFO ");
			sql.append("      (MOVIE_ID, TITLE, STORY, GRADE)");
			sql.append(" VALUES (SEQ_MOVIEINFO.NEXTVAL, ?, ?, ?) ");

			pstmt = conn.prepareStatement(sql.toString());

			int idx = 1;
			pstmt.setString(idx++, vo.getTitle());
			pstmt.setString(idx++, vo.getStory());
			pstmt.setString(idx++, vo.getGrade());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

	public int mvUpdate(MovieInfoVO vo) {
		int rusult = 0;

		try {
			conn = CommonUtil.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("UPDATE MOVIEINFO ");
			sql.append("   SET TITLE = ? ");
			sql.append("     , STORY = ? ");
			sql.append("     , GRADE = ? ");
			sql.append(" WHERE MOVIE_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			int idx = 1;

			pstmt.setString(idx++, vo.getTitle());
			pstmt.setString(idx++, vo.getStory());
			pstmt.setString(idx++, vo.getGrade());
			pstmt.setInt(idx++, vo.getMovieId());

			rusult = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return rusult;
	}

	public int mvDelete(int movieId) {
		int result = 0;

		try {
			conn = CommonUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM MOVIEINFO WHERE MOVIE_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, movieId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

}
