package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
  public void insert(User user) throws SQLException {
    final String query = "INSERT INTO users (userId, password, name, email) VALUES (?, ?, ?, ?)";

    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());

        pstmt.executeUpdate();
      }
    }
  }

  public Optional<User> findByUserId(String userId) throws SQLException {
    final String query = "SELECT userId, password, name, email FROM users WHERE userId = ?";

    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, userId);

        try (ResultSet rs = pstmt.executeQuery()) {
          if (rs.next()) {
            return Optional.of(
              new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")));
          }
          return Optional.empty();
        }
      }
    }
  }
}
