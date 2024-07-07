package com.giwankim.next.controller;

import java.util.Objects;

public class Result {
  private final boolean success;
  private final String message;

  public Result(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public Result(boolean success) {
    this(success, "");
  }

  public static Result ok() {
    return new Result(true);
  }

  public static Result fail(String message) {
    return new Result(false, message);
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Result result)) return false;
    return success == result.success && Objects.equals(message, result.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, message);
  }

  @Override
  public String toString() {
    return "Result{" +
      "success=" + success +
      ", message='" + message + '\'' +
      '}';
  }
}
