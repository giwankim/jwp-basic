package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;

import static com.giwankim.Fixtures.aQuestion;
import static org.assertj.core.api.Assertions.assertThat;

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
    assertThat(actual.getContent()).isEqualTo(question.getContent());
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
}