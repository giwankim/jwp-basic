package com.giwankim.core.mvc;

import com.giwankim.next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMapping {
  private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);

  private final Map<String, Controller> mapping;

  public RequestMapping(Map<String, Controller> mapping) {
    this.mapping = Objects.requireNonNull(mapping, "Map이 null입니다.");
  }

  public RequestMapping() {
    this(new HashMap<>());
  }

  void init() {
    mapping.put("/", new HomeController());
    mapping.put("/user/form", new ForwardController("/user/form.jsp"));
    mapping.put("/user/loginForm", new ForwardController("/user/login.jsp"));
    mapping.put("/user", new ListUserController());
    mapping.put("/user/profile", new ProfileController());
    mapping.put("/user/login", new LoginController());
    mapping.put("/user/logout", new LogoutController());
    mapping.put("/user/updateForm", new UpdateUserFormController());
    mapping.put("/user/update", new UpdateUserController());

    logger.info("Request mapping initialized");
    mapping.forEach((path, controller) -> logger.info("Path : {}, Controller : {}", path, controller.getClass()));
  }

  public Controller getController(String url) {
    return mapping.get(url);
  }
}
