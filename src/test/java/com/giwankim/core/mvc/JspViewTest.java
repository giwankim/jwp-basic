package com.giwankim.core.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class JspViewTest {
  HttpServletRequest request;

  HttpServletResponse response;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
  }

  @Test
  @DisplayName("뷰 이름이 redirect 접두사로 시작할 경우 redirect 응답을 보낸다.")
  void shouldRedirect() throws Exception {
    JspView sut = new JspView("redirect:/index.jsp");

    sut.render(request, response);

    verify(response).sendRedirect("/index.jsp");
  }

  @Test
  @DisplayName("뷰 페이지로 포워드한다.")
  void shouldForward() throws Exception {
    RequestDispatcher rd = mock(RequestDispatcher.class);
    when(request.getRequestDispatcher("/index.jsp")).thenReturn(rd);
    JspView sut = new JspView("/index.jsp");

    sut.render(request, response);

    verify(rd).forward(request, response);
  }
}