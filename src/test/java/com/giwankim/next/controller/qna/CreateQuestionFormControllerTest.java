package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateQuestionFormControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  CreateQuestionFormController sut;

  @BeforeEach
  void setUp() {
    sut = new CreateQuestionFormController();
  }

  @Test
  void shouldReturnQuestionForm() throws ServletException, IOException {
    ModelAndView mv = sut.handleRequest(request, response);
    assertThat(mv.getView()).isEqualTo(JspView.from("/qna/form.jsp"));
  }
}