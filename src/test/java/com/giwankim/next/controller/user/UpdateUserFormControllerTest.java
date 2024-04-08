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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserFormControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  @Mock
  UserDao userDao;

  UpdateUserFormController sut;

  @BeforeEach
  void setUp() {
    sut = new UpdateUserFormController(userDao);
  }

  @Test
  @DisplayName("수정 폼을 서빙한다.")
  void shouldReturnUpdateForm() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn(user.getUserId());
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("/user/updateForm.jsp"));
  }

  @Test
  @DisplayName("유저가 존재하지 않을 경우 예외를 던진다.")
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("user-does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }

  @Test
  @DisplayName("세션의 유저와 수정 대상자가 다른 경우 예외를 던진다.")
  void shouldThrowExceptionWhenUserSessionIsDifferent() {
    User user = aUser().build();
    User another = aUser().userId("different-user").build();
    when(request.getParameter("userId")).thenReturn(user.getUserId());
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(another);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }
}
