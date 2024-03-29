package com.giwankim.next.dao;

import com.giwankim.core.jdbc.JdbcTemplate;
import com.giwankim.core.jdbc.PreparedStatementSetter;
import com.giwankim.core.jdbc.RowMapper;
import com.giwankim.next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao {
  public void insert(User user) {
    final String sql = "INSERT INTO users (user_id, password, name, email) VALUES (?, ?, ?, ?)";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
      }
    };
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.update(sql, pss);
  }

  public Optional<User> findByUserId(String userId) {
    final String sql = "SELECT user_id, password, name, email FROM users WHERE user_id = ?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    };
    RowMapper<User> rm = new RowMapper<>() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        return new User(
          rs.getString("user_id"),
          rs.getString("password"),
          rs.getString("name"),
          rs.getString("email"));
      }
    };
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    return Optional.ofNullable(jdbcTemplate.queryForObject(sql, pss, rm));
  }

  public List<User> findAll() {
    final String sql = "SELECT user_id, password, name, email FROM users";
    RowMapper<User> rm = new RowMapper<>() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("user_id"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
      }
    };
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    return jdbcTemplate.query(sql, rm);
  }

  public void update(User user) {
    final String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
      }
    };
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.update(sql, pss);
  }
}
