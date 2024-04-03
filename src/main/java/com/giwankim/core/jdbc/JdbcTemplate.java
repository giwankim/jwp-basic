package com.giwankim.core.jdbc;

import java.sql.*;
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
    update(sql, createPreparedStatementSetter(args));
  }

  public void update(PreparedStatementCreator psc, KeyHolder keyHolder) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement ps = psc.createPreparedStatement(connection)) {
      ps.executeUpdate();
      try (ResultSet keysResultSet = ps.getGeneratedKeys()) {
        if (keysResultSet.next()) {
          long questionId = keysResultSet.getLong(1);
          keyHolder.setId(questionId);
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) {
    try (Connection connection = ConnectionManager.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      pss.setValues(ps);
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

  public <T> List<T> query(String sql, RowMapper<T> rm, Object... args) {
    return query(sql, rm, createPreparedStatementSetter(args));
  }

  public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) {
    List<T> list = query(sql, rm, pss);
    if (list.isEmpty()) {
      return null;
    }
    return list.getFirst();
  }

  public <T> T queryForObject(String sql, RowMapper<T> rm, Object... args) {
    return queryForObject(sql, rm, createPreparedStatementSetter(args));
  }

  private PreparedStatementSetter createPreparedStatementSetter(Object... args) {
    return ps -> {
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i + 1, args[i]);
      }
    };
  }
}
