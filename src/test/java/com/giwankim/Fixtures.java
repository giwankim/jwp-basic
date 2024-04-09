package com.giwankim;

import com.giwankim.next.model.Answer;
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
      .contents("좋은 개발자란 무엇일까? 끊임 없는 학습에 대한 갈망, 자극에 대한 빠른 반응, 적당한 커뮤니케이션 스킬, 열린 마음가짐?")
      .createdDate(LocalDateTime.of(2024, Month.APRIL, 3, 14, 15))
      .countOfAnswers(3);
  }

  public static Answer.AnswerBuilder anAnswer() {
    return Answer.builder()
      .answerId(1L)
      .writer("토비")
      .contents("람다식에서 사용되는 변수라면 람다식 내부에서 정의된 로컬 변수이거나 람다식이 선언된 외부의 변수를 참조하는 것일텐데, 전자라면 아무리 변경해도 문제될 이유가 없고, 후자는 변경 자체가 허용이 안될텐데. 이 설명이 무슨 뜻인지 이해가 안 됨.")
      .createdDate(LocalDateTime.of(2024, Month.APRIL, 3, 14, 15))
      .questionId(1L);
  }
}
