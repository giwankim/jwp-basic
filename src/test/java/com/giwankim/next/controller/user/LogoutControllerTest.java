package com.giwankim.next.controller.user;

import com.giwankim.next.controller.UserSessionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LogoutControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  LogoutController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
    sut = new LogoutController();
  }

  @Test
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("redirect:/");
  }

  @Test
  void shouldRemoveUserFromSession() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);

    sut.execute(request, response);

    verify(session).removeAttribute(UserSessionUtils.SESSION_USER_KEY);
  }
}