package com.giwankim.core.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

  private static final String DEFAULT_DB_URL = "jdbc:h2:~/jwp-basic;MODE=MySQL";
  private static final String DEFAULT_DB_USER = "sa";
  private static final String DEFAULT_DB_PASSWORD = "";

  private ConnectionManager() {
  }

  public static DataSource getDatasource() {
    Properties properties = getProperties();
    HikariDataSource ds = new HikariDataSource();
    ds.setJdbcUrl(properties.getProperty("datasource.url", DEFAULT_DB_URL));
    ds.setUsername(properties.getProperty("datasource.username", DEFAULT_DB_USER));
    ds.setPassword(properties.getProperty("datasource.password", DEFAULT_DB_PASSWORD));

    return ProxyDataSourceBuilder.create(ds)
      .logQueryToSysOut()
//        .logQueryBySlf4j(SLF4JLogLevel.DEBUG)
      .build();
  }

  private static Properties getProperties() {
    try (InputStream inStream = ConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
      Properties properties = new Properties();
      properties.load(inStream);
      return properties;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static Connection getConnection() {
    try {
      return getDatasource().getConnection();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }
}
