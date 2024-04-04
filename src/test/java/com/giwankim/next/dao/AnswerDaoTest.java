package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.Optional;

import static com.giwankim.Fixtures.anAnswer;
import static org.assertj.core.api.Assertions.assertThat;

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
}