package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.giwankim.Fixtures.anAnswer;
import static org.assertj.core.api.Assertions.*;

class AnswerDaoTest {

  private AnswerDao sut;

  @BeforeEach
  void setUp() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScripts(new ClassPathResource("schema.sql"));
    DatabasePopulatorUtils.execute(populator, ConnectionManager.getDatasource());
    sut = new AnswerDao();
  }

  @Test
  void shouldInsert() {
    Answer answer = anAnswer().build();

    Answer actual = sut.insert(answer);

    assertThat(actual.getWriter()).isEqualTo(answer.getWriter());
    assertThat(actual.getContents()).isEqualTo(answer.getContents());
    assertThat(actual.getCreatedDate()).isEqualTo(answer.getCreatedDate());
    assertThat(actual.getQuestionId()).isEqualTo(answer.getQuestionId());
  }

  @Test
  void shouldFindById() {
    Answer answer = sut.insert(anAnswer().build());

    Optional<Answer> actual = sut.findById(answer.getAnswerId());

    assertThat(actual)
      .isPresent()
      .contains(answer);
  }

  @Test
  void shouldReturnEmptyOptionalIfNotFound() {
    assertThat(sut.findById(99L)).isEmpty();
  }

  @Test
  void shouldFindAllByQuestionId() {
    long questionId = 1L;
    Answer answer1 = sut.insert(
      anAnswer()
        .contents("첫번째 질문")
        .questionId(questionId)
        .build());
    Answer answer2 = sut.insert(
      anAnswer()
        .contents("두번째 질문")
        .questionId(questionId)
        .build());

    List<Answer> actual = sut.findAllByQuestionId(questionId);

    assertThat(actual).containsExactly(answer2, answer1);
  }

  @Test
  void shouldUpdate() {
    Answer answer = sut.insert(anAnswer().build());
    Answer expected = anAnswer()
      .answerId(answer.getAnswerId())
      .writer("로드 존슨")
      .contents("EJB 없이 엔터프라이즈 자바 개발이 가능할까?")
      .build();

    Answer actual = sut.update(expected);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNotExistent() {
    Answer doesNotExist = anAnswer().answerId(99L).build();
    assertThatExceptionOfType(NoSuchElementException.class)
      .isThrownBy(() -> sut.update(doesNotExist));
  }

  @Test
  void shouldDelete() {
    Answer answer = sut.insert(anAnswer().build());
    long answerId = answer.getAnswerId();

    sut.delete(answerId);

    assertThat(sut.findById(answerId))
      .isEmpty();
  }

  @Test
  @DisplayName("존재하지 않는 응답을 지울 경우 exception을 던지지 않는다")
  void shouldNotThrowExceptionWhenDeletingNonExistent() {
    assertThatNoException().isThrownBy(
      () -> sut.delete(99));
  }
}