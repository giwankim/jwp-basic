package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJdbcTemplate {
  public void insert(User user, UserDao userDao) throws SQLException {
    final String sql = userDao.createQueryForInsert();
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        userDao.setValuesForInsert(user, pstmt);
        pstmt.executeUpdate();
      }
    }
  }
}
