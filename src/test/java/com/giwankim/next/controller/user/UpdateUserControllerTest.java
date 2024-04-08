package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  @Mock
  UserDao userDao;

  UpdateUserController sut;

  @BeforeEach
  void setUp() {
    sut = new UpdateUserController(userDao);
  }

  @Test
  @DisplayName("User를 수정한다.")
  void shouldUpdateUser() throws ServletException, IOException {
    User user = aUser().build();
    User updateUser = aUser()
      .password("new-password")
      .name("new-name")
      .email("new-email")
      .build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("new-password");
    when(request.getParameter("name")).thenReturn("new-name");
    when(request.getParameter("email")).thenReturn("new-email");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);

    sut.handleRequest(request, response);

    verify(userDao).update(updateUser);
  }

  @Test
  @DisplayName("루트 페이지로 이동한다.")
  void shouldRedirectToRoot() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);

    View view = sut.handleRequest(request, response);

    assertThat(view).isEqualTo(JspView.from("redirect:/"));
  }


  @Test
  @DisplayName("사용자가 존재하지 않을 경우 exception을 던진다.")
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }

  @Test
  @DisplayName("유저가 세션에 존재하지 않을 경우 exception을 던진다.")
  void shouldThrowExceptionWhenUserSessionDoesNotExist() {
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }

  @Test
  @DisplayName("유저 세션에 저장된 사용자와 수정하려는 사용자가 다른 경우 exception을 던진다.")
  void shouldThrowExceptionWhenUpdatingDifferentUser() {
    User another = aUser().userId("another").build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(another);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }
}