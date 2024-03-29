package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class InsertJdbcTemplate {
  public void insert(User user) throws SQLException {
    final String sql = createQueryForInsert();
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        setValuesForInsert(user, pstmt);
        pstmt.executeUpdate();
      }
    }
  }

  abstract String createQueryForInsert();

  abstract void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException;
}
