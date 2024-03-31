package com.giwankim.core.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

  private static final String DB_URL = "jdbc:h2:~/jwp-basic;MODE=MySQL";
  private static final String DB_USER = "sa";
  private static final String DB_PASSWORD = "";

  private ConnectionManager() {
  }

  public static DataSource getDatasource() {
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl(DB_URL);
    ds.setUsername(DB_USER);
    ds.setPassword(DB_PASSWORD);

    return ProxyDataSourceBuilder.create(ds)
      .logQueryToSysOut()
//        .logQueryBySlf4j(SLF4JLogLevel.DEBUG)
      .build();
  }

  public static Connection getConnection() {
    try {
      return getDatasource().getConnection();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }
}
