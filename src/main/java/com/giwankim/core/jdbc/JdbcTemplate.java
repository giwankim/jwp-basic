package com.giwankim.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
  public void update(String sql, PreparedStatementSetter pss) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      pss.setValues(ps);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public void update(String sql, Object... args) {
    update(sql, createPreparedStatement(args));
  }

  public <T> List<T> query(String sql, RowMapper<T> rm) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      List<T> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(rm.mapRow(rs));
        }
      }
      return list;
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      pss.setValues(ps);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) {
          return null;
        }
        return rm.mapRow(rs);
      }
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public <T> T queryForObject(String sql, RowMapper<T> rm, Object... args) {
    return queryForObject(sql, rm, createPreparedStatement(args));
  }

  private PreparedStatementSetter createPreparedStatement(Object... args) {
    return ps -> {
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i + 1, args[i]);
      }
    };
  }
}
