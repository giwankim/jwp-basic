package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;
import java.util.NoSuchElementException;

import static com.giwankim.Fixtures.aQuestion;
import static org.assertj.core.api.Assertions.*;

class QuestionDaoTest {
  QuestionDao sut;

  @BeforeEach
  void setUp() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScripts(new ClassPathResource("schema.sql"));
    DatabasePopulatorUtils.execute(populator, ConnectionManager.getDatasource());
    sut = new QuestionDao();
  }

  @Test
  void shouldInsert() {
    Question question = aQuestion().build();

    Question actual = sut.insert(question);

    assertThat(actual.getWriter()).isEqualTo(question.getWriter());
    assertThat(actual.getTitle()).isEqualTo(question.getTitle());
    assertThat(actual.getContents()).isEqualTo(question.getContents());
    assertThat(actual.getCreatedDate()).isEqualTo(question.getCreatedDate());
    assertThat(actual.getCountOfAnswers()).isEqualTo(question.getCountOfAnswers());
  }

  @Test
  void shouldFindAll() {
    Question question1 = aQuestion().questionId(1L).build();
    Question question2 = aQuestion().questionId(2L).build();
    sut.insert(question1);
    sut.insert(question2);

    List<Question> actual = sut.findAll();

    assertThat(actual).containsExactly(question2, question1);
  }

  @Test
  void shouldDelete() {
    Question question = sut.insert(aQuestion().build());
    long questionId = question.getQuestionId();

    sut.delete(questionId);

    assertThat(sut.findById(questionId)).isEmpty();
  }

  @Test
  void shouldNotThrowExceptionWhenDeletingNonExistent() {
    assertThatNoException().isThrownBy(
      () -> sut.delete(99L));
  }

  @Test
  void shouldUpdate() {
    Question question = sut.insert(aQuestion().build());
    Question expected = aQuestion()
      .questionId(question.getQuestionId())
      .writer("new-writer")
      .title("new-title")
      .contents("new-content")
      .build();

    Question actual = sut.update(expected);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldThrowExceptionWhenUpdateQuestionDoesNotExist() {
    Question doesNotExist = aQuestion().questionId(99L).build();
    assertThatExceptionOfType(NoSuchElementException.class)
      .isThrownBy(() -> sut.update(doesNotExist));
  }
}
