package com.giwankim.next.controller.qna;

import com.giwankim.core.jdbc.DataAccessException;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.Result;
import com.giwankim.next.dao.AnswerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAnswerControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  AnswerDao answerDao;

  DeleteAnswerController sut;

  @BeforeEach
  void setUp() {
    sut = new DeleteAnswerController(answerDao);
  }

  @Test
  @DisplayName("응답을 지운다.")
  void shouldDeleteAnswer() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("1");

    sut.handleRequest(request, response);

    verify(answerDao).delete(1L);
  }

  @Test
  @DisplayName("삭제 성공시 응답 바디에 성공 결과를 반환한다.")
  void shouldAddSuccessResultWhenDeleteSucceeds() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("1");

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("result", Result.ok());
  }

  @Test
  @DisplayName("삭제 실패시 실패 결과를 반환한다.")
  void shouldAddFailResultWhenDeleteFails() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("99");
    doThrow(new DataAccessException("삭제 실패")).when(answerDao).delete(99L);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("result", Result.fail("삭제 실패"));
  }
}
