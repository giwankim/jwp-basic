package com.giwankim.core.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JspViewTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  RequestDispatcher rd;

  @Test
  @DisplayName("뷰 이름이 redirect 접두사로 시작할 경우 redirect 응답을 보낸다.")
  void shouldRedirect() throws Exception {
    JspView sut = new JspView("redirect:/index.jsp");

    sut.render(Collections.emptyMap(), request, response);

    verify(response).sendRedirect("/index.jsp");
  }

  @Test
  @DisplayName("뷰 페이지로 포워드한다.")
  void shouldForward() throws Exception {
    when(request.getRequestDispatcher("/index.jsp")).thenReturn(rd);
    JspView sut = new JspView("/index.jsp");

    sut.render(Collections.emptyMap(), request, response);

    verify(rd).forward(request, response);
  }

  @Test
  @DisplayName("모델의 키, 값을 request객체에 세팅한다.")
  void shouldSetAttributes() throws Exception {
    JspView sut = new JspView("/index.jsp");
    Map<String, Object> model = Map.of("attribute1", "value1", "attribute2", "value2");
    when(request.getRequestDispatcher("/index.jsp")).thenReturn(rd);

    sut.render(model, request, response);

    verify(request).setAttribute("attribute1", "value1");
    verify(request).setAttribute("attribute2", "value2");
  }
}
