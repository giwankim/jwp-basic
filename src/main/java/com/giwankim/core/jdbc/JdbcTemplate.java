package com.giwankim.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {
  public void update(String sql, PreparedStatementSetter pss) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pss.setParameters(pstmt);
        pstmt.executeUpdate();
      }
    }
  }

  public <T> List<T> query(String sql, RowMapper<T> rm) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        List<T> list = new ArrayList<>();
        try (ResultSet rs = pstmt.executeQuery()) {
          while (rs.next()) {
            list.add(rm.mapRow(rs));
          }
        }
        return list;
      }
    }
  }

  public <T> T queryForObject(String sql, PreparedStatementSetter pss) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pss.setParameters(pstmt);
        try (ResultSet rs = pstmt.executeQuery()) {
          return mapRow(rs);
        }
      }
    }
  }

  protected abstract <T> T mapRow(ResultSet rs) throws SQLException;
}
