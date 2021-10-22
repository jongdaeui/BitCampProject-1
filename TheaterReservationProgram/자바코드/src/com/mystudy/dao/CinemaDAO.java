package com.mystudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mystudy.util.CommonUtil;
import com.mystudy.vo.CinemaVO;
import com.mystudy.vo.MemberVO;

public class CinemaDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public List<CinemaVO> selectAll() {
		List<CinemaVO> list = null;

		conn = CommonUtil.getConnection();

		if (conn == null)
			return null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CINEMA_ID, REGION, C_PHONE ");
			sql.append("  FROM CINEMA ");
			sql.append(" ORDER BY CINEMA_ID ");

			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			list = new ArrayList<CinemaVO>();

			while (rs.next()) {
				CinemaVO vo = new CinemaVO(rs.getInt("CINEMA_ID"), rs.getString("REGION"), rs.getString("C_PHONE"));

				list.add(vo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return list;

	}

	public CinemaVO selectOne(int cinemaId) {
		CinemaVO vo = null;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CINEMA_ID, REGION, C_PHONE ");
			sql.append(" FROM CINEMA ");
			sql.append(" WHERE CINEMA_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, cinemaId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new CinemaVO(rs.getInt("CINEMA_ID"), rs.getString("REGION"), rs.getString("C_PHONE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt, rs);
		}

		return vo;

	}

	public int insert(CinemaVO vo) {
		int result = 0;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO CINEMA");
			sql.append("	(CINEMA_ID, REGION, C_PHONE)");
			sql.append("VALUES (SEQ_CINEMA.NEXTVAL, ?, ?)");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getRegion());
			pstmt.setString(2, vo.getcPhone());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

	public int update(CinemaVO vo) {
		int result = 0;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CINEMA ");
			sql.append("SET REGION = ? ");
			sql.append(", C_PHONE = ? ");
			sql.append("WHERE CINEMA_ID = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getRegion());
			pstmt.setString(2, vo.getcPhone());
			pstmt.setInt(3, vo.getCinemaId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;

	}

	public int delete(int cinemaId) {
		int result = 0;

		conn = CommonUtil.getConnection();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM CINEMA WHERE CINEMA_ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, cinemaId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonUtil.close(conn, pstmt);
		}

		return result;
	}

}
