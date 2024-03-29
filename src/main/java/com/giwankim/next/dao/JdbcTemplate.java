package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
  public void update(String sql, User user) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        setValues(user, pstmt);
        pstmt.executeUpdate();
      }
    }
  }

  abstract void setValues(User user, PreparedStatement pstmt) throws SQLException;
}
