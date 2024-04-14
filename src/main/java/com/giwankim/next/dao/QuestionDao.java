package com.giwankim.next.dao;

import com.giwankim.core.jdbc.JdbcTemplate;
import com.giwankim.core.jdbc.KeyHolder;
import com.giwankim.core.jdbc.PreparedStatementCreator;
import com.giwankim.core.jdbc.PreparedStatementSetter;
import com.giwankim.next.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class QuestionDao {
  public Question insert(Question question) {
    final String sql =
      "INSERT INTO question (writer, title, contents, created_date, count_of_answers) VALUES (?, ?, ?, ?, ?)";
    JdbcTemplate jdbcTemplate = JdbcTemplate.INSTANCE;
    PreparedStatementCreator psc = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, question.getWriter());
        ps.setString(2, question.getTitle());
        ps.setString(3, question.getContents());
        ps.setObject(4, question.getCreatedDate());
        ps.setInt(5, question.getCountOfAnswers());
        return ps;
      }
    };
    KeyHolder keyHolder = new KeyHolder();
    jdbcTemplate.update(psc, keyHolder);
    return findById(keyHolder.getId()).orElseThrow();
  }

  public Optional<Question> findById(long questionId) {
    final String sql = "SELECT question_id, writer, title, contents, created_date, count_of_answers FROM question" +
      " WHERE question_id = ?";
    JdbcTemplate jdbcTemplate = JdbcTemplate.INSTANCE;
    Question question = jdbcTemplate.queryForObject(sql, rs -> new Question(
      rs.getLong("question_id"),
      rs.getString("writer"),
      rs.getString("title"),
      rs.getString("contents"),
      rs.getObject("created_date", LocalDateTime.class),
      rs.getInt("count_of_answers")
    ), questionId);
    return Optional.ofNullable(question);
  }

  public List<Question> findAll() {
    final String sql = "SELECT question_id, writer, title, contents, created_date, count_of_answers FROM question" +
      " ORDER BY question_id DESC";
    JdbcTemplate jdbcTemplate = JdbcTemplate.INSTANCE;
    return jdbcTemplate.query(sql, rs -> new Question(
      rs.getLong("question_id"),
      rs.getString("writer"),
      rs.getString("title"),
      rs.getString("contents"),
      rs.getObject("created_date", LocalDateTime.class),
      rs.getInt("count_of_answers")));
  }

  public Question update(Question question) {
    final String sql = "UPDATE question SET writer = ?, title = ?, contents = ? WHERE question_id = ?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setString(1, question.getWriter());
        ps.setString(2, question.getTitle());
        ps.setString(3, question.getContents());
        ps.setLong(4, question.getQuestionId());
      }
    };
    JdbcTemplate jdbcTemplate = JdbcTemplate.INSTANCE;
    jdbcTemplate.update(sql, pss);
    return findById(question.getQuestionId()).orElseThrow();
  }

  public void delete(long questionId) {
    final String sql = "DELETE FROM question WHERE question_id = ?";
    JdbcTemplate jdbcTemplate = JdbcTemplate.INSTANCE;
    jdbcTemplate.update(sql, questionId);
  }

  public void incrementAnswerCount(long questionId) {
    final String sql = "UPDATE question SET count_of_answers = count_of_answers + 1 WHERE question_id = ?";
    JdbcTemplate jdbcTemplate = JdbcTemplate.INSTANCE;
    jdbcTemplate.update(sql, questionId);
  }
}
