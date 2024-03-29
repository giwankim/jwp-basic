package com.giwankim.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
  public void update(String sql, PreparedStatementSetter pss) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pss.setParameters(pstmt);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public <T> List<T> query(String sql, RowMapper<T> rm) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
      List<T> list = new ArrayList<>();
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          list.add(rm.mapRow(rs));
        }
      }
      return list;
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public <T> T queryForObject(String sql, PreparedStatementSetter pss, RowMapper<T> rm) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pss.setParameters(pstmt);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }
        return rm.mapRow(rs);
      }
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }
}
