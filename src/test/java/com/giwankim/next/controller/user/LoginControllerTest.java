package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  @Mock
  UserDao userDao;

  LoginController sut;

  @BeforeEach
  void setUp() {
    sut = new LoginController(userDao);
  }

  @Test
  @DisplayName("사용자 객체를 세션에 세팅한다.")
  void shouldSetUserInSession() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn(user.getUserId());
    when(request.getParameter("password")).thenReturn(user.getPassword());
    when(request.getSession()).thenReturn(session);
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));

    sut.handleRequest(request, response);

    verify(session).setAttribute(SESSION_USER_KEY, user);
  }

  @Test
  @DisplayName("루트 페이지로 이동한다.")
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getSession()).thenReturn(session);
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/"));
  }

  @Test
  @DisplayName("사용자가 세션에 존재하지 않을 경우 로그인 페이지를 서빙한다.")
  void shouldForwardToLoginPageWhenUserDoesNotExist() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("does-not-exist");
    when(userDao.findByUserId("does-not-exist")).thenReturn(Optional.empty());

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("/user/login.jsp"));
  }

  @Test
  @DisplayName("사용자가 세션에 존재하지 않을 경우 loginFailed = true로 세팅한다.")
  void shouldSetLoginFailedWhenUserDoesNotExist() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("does-not-exist");
    when(userDao.findByUserId("does-not-exist")).thenReturn(Optional.empty());

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("loginFailed", true);
  }

  @Test
  @DisplayName("암호가 같이 않을 경우 로그인 페이지를 서빙한다.")
  void shouldForwardToLoginPageWhenPasswordsDoNotMatch() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn(user.getUserId());
    when(request.getParameter("password")).thenReturn("different-password");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("/user/login.jsp"));
  }

  @Test
  @DisplayName("비밀번호가 불일치할 경우 loginFailed = true 세팅한다.")
  void shouldSetLoginFailedAttributeWhenPasswordsDoNotMatch() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("different-password");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("loginFailed", true);
  }
}
