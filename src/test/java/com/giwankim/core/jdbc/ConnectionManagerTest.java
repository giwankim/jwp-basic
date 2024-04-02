package com.giwankim.core.jdbc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionManagerTest {
  @Test
  void shouldGetDatasource() {
    assertThat(ConnectionManager.getDatasource()).isNotNull();
  }
}