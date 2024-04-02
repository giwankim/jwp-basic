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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateUserFormControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  UserDao userDao;

  UpdateUserFormController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
    userDao = mock(UserDao.class);
    sut = new UpdateUserFormController(userDao);
  }

  @Test
  void shouldReturnUpdateForm() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("/user/updateForm.jsp");
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("user-does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }

  @Test
  void shouldThrowExceptionWhenUserSessionIsDifferent() {
    User anotherUser = aUser().userId("different-user").build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(anotherUser);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }
}
