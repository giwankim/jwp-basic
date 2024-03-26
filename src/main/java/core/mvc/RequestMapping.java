package core.mvc;

import next.controller.HomeController;
import next.controller.ListUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMapping {
  private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);

  private final Map<String, Controller> mapping;

  public RequestMapping(Map<String, Controller> mapping) {
    this.mapping = Objects.requireNonNull(mapping, "Mapping이 null입니다.");
  }

  public RequestMapping() {
    this(new HashMap<>());
  }

  void init() {
    mapping.put("/", new HomeController());
    mapping.put("/user/form", new ForwardController("/user/form.jsp"));
    mapping.put("/user/loginForm", new ForwardController("/user/login.jsp"));
    mapping.put("/user", new ListUserController());

    logger.info("Request mapping initialized");
    mapping.forEach((path, controller) -> logger.info("Path : {}, Controller : {}", path, controller.getClass()));
  }

  public Controller getController(String url) {
    return mapping.get(url);
  }
}
