package com.giwankim.next.dao;

import com.giwankim.core.jdbc.JdbcTemplate;
import com.giwankim.core.jdbc.KeyHolder;
import com.giwankim.core.jdbc.PreparedStatementCreator;
import com.giwankim.next.model.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AnswerDao {
  public Answer insert(Answer answer) {
    final String sql = "INSERT INTO answer (writer, contents, created_date, question_id) VALUES (?, ?, ?, ?)";
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    PreparedStatementCreator psc = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, answer.getWriter());
        ps.setString(2, answer.getContents());
        ps.setObject(3, answer.getCreatedDate());
        ps.setLong(4, answer.getQuestionId());
        return ps;
      }
    };
    KeyHolder keyHolder = new KeyHolder();
    jdbcTemplate.update(psc, keyHolder);
    return findById(keyHolder.getId()).orElseThrow();
  }

  public Optional<Answer> findById(long answerId) {
    final String sql = "SELECT answer_id, writer, contents, created_date, question_id FROM answer WHERE answer_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    Answer answer = jdbcTemplate.queryForObject(sql, rs -> new Answer(
      rs.getLong("answer_id"),
      rs.getString("writer"),
      rs.getString("contents"),
      rs.getObject("created_date", LocalDateTime.class),
      rs.getLong("question_id")
    ), answerId);
    return Optional.ofNullable(answer);
  }

  public List<Answer> findAllByQuestionId(long questionId) {
    final String sql = "SELECT answer_id, writer, contents, created_date, question_id FROM answer WHERE question_id = ?" +
      " ORDER BY answer_id DESC";
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    return jdbcTemplate.query(sql, rs -> new Answer(
      rs.getLong("answer_id"),
      rs.getString("writer"),
      rs.getString("contents"),
      rs.getObject("created_date", LocalDateTime.class),
      rs.getLong("question_id")
    ), questionId);
  }

  public Answer update(Answer answer) {
    final String sql = "UPDATE answer SET writer = ?, contents = ?, question_id = ? WHERE answer_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.update(sql,
      answer.getWriter(),
      answer.getContents(),
      answer.getQuestionId(),
      answer.getAnswerId());
    return findById(answer.getAnswerId()).orElseThrow();
  }

  public void delete(long answerId) {
    final String sql = "DELETE FROM answer WHERE answer_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.update(sql, answerId);
  }
}
