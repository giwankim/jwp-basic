package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.giwankim.next.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateUserControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  CreateUserController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    sut = new CreateUserController();
  }

  @AfterEach
  void tearDown() {
    Database.deleteAll();
  }

  @Test
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("name")).thenReturn("name");
    when(request.getParameter("email")).thenReturn("email");

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("redirect:/");
  }

  @Test
  void shouldPersistUser() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("name")).thenReturn("name");
    when(request.getParameter("email")).thenReturn("email");

    sut.execute(request, response);

    assertThat(Database.findById("userId"))
      .isPresent()
      .contains(aUser().build());
  }
}