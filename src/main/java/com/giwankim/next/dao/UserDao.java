package com.giwankim.next.dao;

import com.giwankim.core.jdbc.MyJdbcTemplate;
import com.giwankim.core.jdbc.RowMapper;
import com.giwankim.next.model.User;

import java.util.List;
import java.util.Optional;

public class UserDao {
  public User insert(User user) {
    final String sql = "INSERT INTO users (user_id, password, name, email) VALUES (?, ?, ?, ?)";
    MyJdbcTemplate jdbcTemplate = MyJdbcTemplate.getInstance();
    jdbcTemplate.update(
      sql,
      user.getUserId(),
      user.getPassword(),
      user.getName(),
      user.getEmail());
    return findByUserId(user.getUserId())
      .orElseThrow();
  }

  public Optional<User> findByUserId(String userId) {
    final String sql = "SELECT user_id, password, name, email FROM users WHERE user_id = ?";
    RowMapper<User> rm = rs -> new User(
      rs.getString("user_id"),
      rs.getString("password"),
      rs.getString("name"),
      rs.getString("email"));
    MyJdbcTemplate jdbcTemplate = MyJdbcTemplate.getInstance();
    return Optional.ofNullable(
      jdbcTemplate.queryForObject(sql, rm, userId));
  }

  public List<User> findAll() {
    final String sql = "SELECT user_id, password, name, email FROM users";
    RowMapper<User> rm = rs -> new User(
      rs.getString("user_id"),
      rs.getString("password"),
      rs.getString("name"),
      rs.getString("email"));
    MyJdbcTemplate jdbcTemplate = MyJdbcTemplate.getInstance();
    return jdbcTemplate.query(sql, rm);
  }

  public User update(User user) {
    final String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";
    MyJdbcTemplate jdbcTemplate = MyJdbcTemplate.getInstance();
    jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    return findByUserId(user.getUserId())
      .orElseThrow();
  }
}
