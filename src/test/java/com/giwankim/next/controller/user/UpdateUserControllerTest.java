package com.giwankim.next.controller.user;

import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class UpdateUserControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  UserDao userDao;

  UpdateUserController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
    userDao = mock(UserDao.class);
    sut = new UpdateUserController(userDao);
  }

  @Test
  void shouldUpdateUser() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("new-password");
    when(request.getParameter("name")).thenReturn("new-name");
    when(request.getParameter("email")).thenReturn("new-email");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    User updateUser = aUser()
      .password("new-password")
      .name("new-name")
      .email("new-email")
      .build();

    sut.execute(request, response);

    verify(userDao).update(updateUser);
  }

  @Test
  void shouldRedirectToRoot() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);

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
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingDifferentUser() {
    User another = aUser().userId("another").build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(another);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }
}