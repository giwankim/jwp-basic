package com.giwankim;

import com.giwankim.next.model.Question;
import com.giwankim.next.model.Question.QuestionBuilder;
import com.giwankim.next.model.User;
import com.giwankim.next.model.User.UserBuilder;

import java.time.LocalDateTime;
import java.time.Month;

public class Fixtures {
  private Fixtures() {
  }

  public static UserBuilder aUser() {
    return User.builder()
      .userId("userId")
      .password("password")
      .name("name")
      .email("email");
  }

  public static QuestionBuilder aQuestion() {
    return Question.builder()
      .questionId(1L)
      .writer("사용자")
      .title("어떤 개발자로서 성장해야 나의 가치를 최대화할 수 있을까?")
      .content("좋은 개발자란 무엇일까? 끊임 없는 학습에 대한 갈망, 자극에 대한 빠른 반응, 적당한 커뮤니케이션 스킬, 열린 마음가짐?")
      .createdDate(LocalDateTime.of(2024, Month.APRIL, 3, 14, 15))
      .countOfAnswers(3);
  }
}
