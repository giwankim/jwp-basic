package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;
import java.util.Optional;

import static com.giwankim.Fixtures.anAnswer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

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
  void shouldDelete() {
    Answer answer = sut.insert(anAnswer().build());
    long answerId = answer.getAnswerId();

    sut.delete(answerId);

    assertThat(sut.findById(answerId))
      .isEmpty();
  }

  @Test
  void shouldNotThrowExceptionWhenDeletingNonExistent() {
    assertThatNoException().isThrownBy(
      () -> sut.delete(99));
  }
}