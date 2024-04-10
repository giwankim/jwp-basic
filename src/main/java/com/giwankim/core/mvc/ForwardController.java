package com.giwankim.core.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ForwardController extends AbstractController {
  private final String forwardUrl;

  public ForwardController(String forwardUrl) {
    this.forwardUrl = Objects.requireNonNull(forwardUrl, "입력하신 URL이 null입니다.");
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    return jspView(forwardUrl);
  }
}
