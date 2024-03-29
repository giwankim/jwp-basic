package com.giwankim.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {
  public void update(String sql) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        setValues(pstmt);
        pstmt.executeUpdate();
      }
    }
  }

  public <T> List<T> query(String sql) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        List<T> list = new ArrayList<>();
        try (ResultSet rs = pstmt.executeQuery()) {
          while (rs.next()) {
            list.add(mapRow(rs));
          }
        }
        return list;
      }
    }
  }

  public <T> T queryForObject(String sql) throws SQLException {
    try (Connection connection = ConnectionManager.getConnection()) {
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        setValues(pstmt);
        try (ResultSet rs = pstmt.executeQuery()) {
          return mapRow(rs);
        }
      }
    }
  }

  protected abstract <T> T mapRow(ResultSet rs) throws SQLException;

  protected abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
