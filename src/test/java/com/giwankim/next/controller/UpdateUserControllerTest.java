package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.giwankim.next.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateUserControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  User user;

  UpdateUserController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
    sut = new UpdateUserController();
    user = aUser().build();
    Database.addUser(user);
  }

  @AfterEach
  void tearDown() {
    Database.deleteAll();
  }

  @Test
  void shouldUpdateUser() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("new-password");
    when(request.getParameter("name")).thenReturn("new-name");
    when(request.getParameter("email")).thenReturn("new-email");
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getSession()).thenReturn(session);
    User expected = aUser()
      .password("new-password")
      .name("new-name")
      .email("new-email")
      .build();

    sut.execute(request, response);

    assertThat(user).isEqualTo(expected);
  }

  @Test
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getSession()).thenReturn(session);

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("redirect:/");
  }


  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }

  @Test
  void shouldThrowExceptionWhenUserSessionDoesNotExist() {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getSession()).thenReturn(session);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingDifferentUser() {
    User another = aUser().userId("another").build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(another);
    when(request.getSession()).thenReturn(session);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }
}