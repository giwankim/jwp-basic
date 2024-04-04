package com.giwankim.next.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Question {
  private final long questionId;
  private final String writer;
  private final String title;
  private final String content;
  private final LocalDateTime createdDate;
  private final int countOfAnswers;

  public Question(long questionId, String writer, String title, String content, LocalDateTime createdDate, int countOfAnswers) {
    this.questionId = questionId;
    this.writer = writer;
    this.title = title;
    this.content = content;
    this.createdDate = createdDate;
    this.countOfAnswers = countOfAnswers;
  }

  public long getQuestionId() {
    return questionId;
  }

  public String getWriter() {
    return writer;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public String getFormattedCreatedDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return createdDate.format(formatter);
  }

  public int getCountOfAnswers() {
    return countOfAnswers;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Question question)) return false;
    return questionId == question.questionId && countOfAnswers == question.countOfAnswers && Objects.equals(writer, question.writer) && Objects.equals(title, question.title) && Objects.equals(content, question.content) && Objects.equals(createdDate, question.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questionId, writer, title, content, createdDate, countOfAnswers);
  }

  @Override
  public String toString() {
    return "Question{" +
      "questionId=" + questionId +
      ", writer='" + writer + '\'' +
      ", title='" + title + '\'' +
      ", content='" + content + '\'' +
      ", createdDate=" + createdDate +
      ", countOfAnswers=" + countOfAnswers +
      '}';
  }

  public static QuestionBuilder builder() {
    return new QuestionBuilder();
  }

  public static class QuestionBuilder {
    private long questionId;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private int countOfAnswers;

    QuestionBuilder() {
    }

    public QuestionBuilder questionId(long val) {
      questionId = val;
      return this;
    }

    public QuestionBuilder writer(String val) {
      writer = val;
      return this;
    }

    public QuestionBuilder title(String val) {
      title = val;
      return this;
    }

    public QuestionBuilder content(String val) {
      content = val;
      return this;
    }

    public QuestionBuilder createdDate(LocalDateTime val) {
      createdDate = val;
      return this;
    }

    public QuestionBuilder countOfAnswers(int val) {
      countOfAnswers = val;
      return this;
    }

    public Question build() {
      return new Question(questionId, writer, title, content, createdDate, countOfAnswers);
    }
  }
}
