package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {
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

  abstract <T> T mapRow(ResultSet rs) throws SQLException;
}
