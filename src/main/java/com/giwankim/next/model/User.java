package com.giwankim.next.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String userId;
  private String password;
  private String name;
  private String email;

  public User(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void update(User updateUser) {
    this.password = updateUser.password;
    this.name = updateUser.name;
    this.email = updateUser.email;
  }

  public boolean comparePasswords(String password) {
    return this.password.equals(password);
  }

  public boolean isSameUser(User user) {
    if (this.userId == null) {
      return false;
    }
    return this.userId.equals(user.userId);
  }

  public boolean isWriter(Question question) {
    return Objects.equals(userId, question.getWriter());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(userId, user.userId) && Objects.equals(password,
      user.password) && Objects.equals(name, user.name) && Objects.equals(email,
      user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, password, name, email);
  }

  @Override
  public String toString() {
    return "User{" +
      "userId='" + userId + '\'' +
      ", password='" + password + '\'' +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      '}';
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public static class UserBuilder {
    private String userId;
    private String password;
    private String name;
    private String email;

    UserBuilder() {
    }

    public UserBuilder userId(String val) {
      userId = val;
      return this;
    }

    public UserBuilder password(String val) {
      password = val;
      return this;
    }

    public UserBuilder name(String val) {
      name = val;
      return this;
    }

    public UserBuilder email(String val) {
      email = val;
      return this;
    }

    public User build() {
      return new User(userId, password, name, email);
    }
  }
}
