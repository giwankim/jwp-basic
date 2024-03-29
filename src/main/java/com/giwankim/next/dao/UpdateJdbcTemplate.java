package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UpdateJdbcTemplate {
  public void update(User user) throws SQLException {
    final String sql = createQueryForUpdate();
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        setValuesForUpdate(user, pstmt);
        pstmt.executeUpdate();
      }
    }
  }

  abstract String createQueryForUpdate();

  abstract void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;
}
