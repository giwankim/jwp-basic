package com.giwankim.next.dao;

import com.giwankim.core.jdbc.JdbcTemplate;
import com.giwankim.next.model.User;

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
      protected User mapRow(ResultSet rs) throws SQLException {
        return null;
      }

      @Override
      protected void setValues(PreparedStatement pstmt) throws SQLException {
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
    JdbcTemplate jdbcTemplate = new JdbcTemplate() {
      @Override
      protected User mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return null;
        }
        return new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
      }

      @Override
      protected void setValues(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    };
    return Optional.ofNullable(jdbcTemplate.queryForObject(sql));
  }

  public List<User> findAll() throws SQLException {
    final String sql = "SELECT user_id, password, name, email FROM users";
    JdbcTemplate jdbcTemplate = new JdbcTemplate() {
      @Override
      protected User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
      }

      @Override
      protected void setValues(PreparedStatement pstmt) throws SQLException {
      }
    };
    return jdbcTemplate.query(sql);
  }

  public void update(User user) throws SQLException {
    final String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate() {
      @Override
      protected User mapRow(ResultSet rs) throws SQLException {
        return null;
      }

      @Override
      protected void setValues(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
      }
    };
    jdbcTemplate.update(sql);
  }
}
