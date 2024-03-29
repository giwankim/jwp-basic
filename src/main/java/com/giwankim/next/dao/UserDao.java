package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao {
  public void insert(User user) throws SQLException {
    final String sql = "INSERT INTO users (user_id, password, name, email) VALUES (?, ?, ?, ?)";
    JdbcTemplate jdbcTemplate = new JdbcTemplate() {
      @Override
      public void setValues(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
      }
    };
    jdbcTemplate.update(sql);
  }

  public Optional<User> findByUserId(String userId) throws SQLException {
    final String sql = "SELECT user_id, password, name, email FROM users WHERE user_id = ?";
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, userId);

        try (ResultSet rs = pstmt.executeQuery()) {
          if (rs.next()) {
            return Optional.of(new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email")));
          }
          return Optional.empty();
        }
      }
    }
  }

  public List<User> findAll() throws SQLException {
    final String sql = "SELECT user_id, password, name, email FROM users";
    SelectJdbcTemplate jdbcTemplate = new SelectJdbcTemplate() {
      @Override
      User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
      }
    };
    return jdbcTemplate.query(sql);
  }

  public void update(User user) throws SQLException {
    final String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate() {
      @Override
      void setValues(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
      }
    };
    jdbcTemplate.update(sql);
  }
}
