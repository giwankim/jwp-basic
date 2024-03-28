package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateJdbcTemplate {
  public void update(User user, UserDao userDao) throws SQLException {
    final String sql = userDao.createQueryForUpdate();
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        userDao.setValuesForUpdate(user, pstmt);
        pstmt.executeUpdate();
      }
    }
  }
}
