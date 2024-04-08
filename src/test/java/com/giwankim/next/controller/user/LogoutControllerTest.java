package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.controller.UserSessionUtils;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  LogoutController sut;

  @BeforeEach
  void setUp() {
    sut = new LogoutController();
  }

  @Test
  @DisplayName("루트 페이지로 이동한다.")
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);

    View view = sut.handleRequest(request, response);

    assertThat(view).isEqualTo(JspView.from("redirect:/"));
  }

  @Test
  @DisplayName("User를 세션에서 제거한다.")
  void shouldRemoveUserFromSession() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);

    sut.handleRequest(request, response);

    verify(session).removeAttribute(UserSessionUtils.SESSION_USER_KEY);
  }
}