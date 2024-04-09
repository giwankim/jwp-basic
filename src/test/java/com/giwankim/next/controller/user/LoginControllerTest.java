package com.giwankim.next.controller.user;

import com.giwankim.next.dao.UserDao;
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
import static org.mockito.Mockito.*;

class LoginControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  UserDao userDao;

  LoginController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
    userDao = mock(UserDao.class);
    sut = new LoginController(userDao);
  }

  @Test
  void shouldSetUserInSession() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getSession()).thenReturn(session);
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    sut.execute(request, response);

    verify(session).setAttribute(SESSION_USER_KEY, aUser().build());
  }

  @Test
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getSession()).thenReturn(session);
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("redirect:/");
  }

  @Test
  void shouldForwardToLoginPageWhenUserDoesNotExist() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("does-not-exist");
    when(request.getSession()).thenReturn(session);

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("/user/login.jsp");
  }

  @Test
  void shouldSetLoginFailedWhenUserDoesNotExist() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("does-not-exist");
    when(request.getSession()).thenReturn(session);

    sut.execute(request, response);

    verify(request).setAttribute("loginFailed", true);
  }

  @Test
  void shouldForwardToLoginPageWhenPasswordsDoNotMatch() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("different-password");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("/user/login.jsp");
  }

  @Test
  void shouldSetLoginFailedAttributeWhenPasswordsDoNotMatch() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("different-password");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    sut.execute(request, response);

    verify(request).setAttribute("loginFailed", true);
  }
}