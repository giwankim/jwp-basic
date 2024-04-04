package com.giwankim.next.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Answer {
  private final long answerId;
  private final String writer;
  private final String contents;
  private final LocalDateTime createdDate;
  private final long questionId;

  public Answer(long answerId, String writer, String contents, LocalDateTime createdDate, long questionId) {
    this.answerId = answerId;
    this.writer = writer;
    this.contents = contents;
    this.createdDate = createdDate;
    this.questionId = questionId;
  }

  public long getAnswerId() {
    return answerId;
  }

  public String getWriter() {
    return writer;
  }

  public String getContents() {
    return contents;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public String getFormattedCreatedDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return createdDate.format(formatter);
  }

  public long getQuestionId() {
    return questionId;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Answer answer)) return false;
    return answerId == answer.answerId && questionId == answer.questionId && Objects.equals(writer, answer.writer) && Objects.equals(contents, answer.contents) && Objects.equals(createdDate, answer.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(answerId, writer, contents, createdDate, questionId);
  }

  @Override
  public String toString() {
    return "Answer{" +
      "answerId=" + answerId +
      ", writer='" + writer + '\'' +
      ", contents='" + contents + '\'' +
      ", created_date=" + createdDate +
      ", questionId=" + questionId +
      '}';
  }

  public static AnswerBuilder builder() {
    return new AnswerBuilder();
  }

  public static class AnswerBuilder {
    private long answerId;
    private String writer;
    private String contents;
    private LocalDateTime createdDate;
    private long questionId;

    AnswerBuilder() {
    }

    public AnswerBuilder answerId(long val) {
      answerId = val;
      return this;
    }

    public AnswerBuilder writer(String val) {
      writer = val;
      return this;
    }

    public AnswerBuilder contents(String val) {
      contents = val;
      return this;
    }

    public AnswerBuilder createdDate(LocalDateTime val) {
      createdDate = val;
      return this;
    }

    public AnswerBuilder questionId(long val) {
      questionId = val;
      return this;
    }

    public Answer build() {
      return new Answer(answerId, writer, contents, createdDate, questionId);
    }
  }
}
