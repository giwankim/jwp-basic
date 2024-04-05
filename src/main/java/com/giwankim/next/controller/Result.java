package com.giwankim.next.controller;

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
  public String toString() {
    return "Result{" +
      "success=" + success +
      ", message='" + message + '\'' +
      '}';
  }
}
