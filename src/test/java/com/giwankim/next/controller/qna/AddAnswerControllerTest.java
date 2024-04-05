package com.giwankim.next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.model.Answer;
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
import java.io.PrintWriter;

import static com.giwankim.Fixtures.anAnswer;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddAnswerControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  @Mock
  AnswerDao answerDao;

  AddAnswerController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    sut = new AddAnswerController(answerDao);
  }

  @Test
  @DisplayName("")
  void shouldSaveAnswer() throws ServletException, IOException {
    when(request.getParameter("writer")).thenReturn("사용자");
    when(request.getParameter("contents")).thenReturn("응답 컨텐츠입니다.");
    when(request.getParameter("questionId")).thenReturn("1");
    when(response.getWriter()).thenReturn(mock(PrintWriter.class));

    sut.execute(request, response);

    verify(answerDao, times(1)).insert(argThat(answer ->
      "사용자".equals(answer.getWriter()) &&
        "응답 컨텐츠입니다.".equals(answer.getContents()) &&
        answer.getQuestionId() == 1L
    ));
  }

  @Test
  void shouldRespondWithSavedAnswer() throws IOException, ServletException {
    Answer answer = anAnswer().build();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    PrintWriter out = mock(PrintWriter.class);
    when(request.getParameter("writer")).thenReturn(answer.getWriter());
    when(request.getParameter("contents")).thenReturn(answer.getContents());
    when(request.getParameter("questionId")).thenReturn(String.valueOf(answer.getQuestionId()));
    when(answerDao.insert(any(Answer.class))).thenReturn(answer);
    when(response.getWriter()).thenReturn(out);

    sut.execute(request, response);

    verify(out).print(objectMapper.writeValueAsString(answer));
  }
}