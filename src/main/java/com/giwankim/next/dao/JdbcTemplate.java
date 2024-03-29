package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
  public void update(String sql) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        setValues(pstmt);
        pstmt.executeUpdate();
      }
    }
  }

  abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
