package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HomeControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HomeController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    sut = new HomeController();
    Database.addUser(new User("user1", "password1", "name1", "test1@example.com"));
    Database.addUser(new User("user2", "password2", "name2", "test2@example.com"));
  }

  @AfterAll
  static void afterAll() {
    Database.deleteAll();
  }

  @Test
  void shouldReturnView() throws ServletException, IOException {
    String view = sut.execute(request, response);
    assertThat(view).isEqualTo("home.jsp");
  }

  @Test
  void shouldAttachUsers() throws ServletException, IOException {
    sut.execute(request, response);
    verify(request).setAttribute("users", Database.findAll());
  }
}