package com.giwankim.next;

import com.giwankim.next.model.User;

public class Fixtures {
  private Fixtures() {
  }

  public static User.UserBuilder aUser() {
    return User.builder()
      .userId("userId")
      .password("password")
      .name("name")
      .email("email");
  }
}
