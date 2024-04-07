package com.giwankim.core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {
  private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

  private final String viewName;

  public JspView(String viewName) {
    this.viewName = viewName;
  }

  @Override
  public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
      String redirectView = viewName.substring(DEFAULT_REDIRECT_PREFIX.length());
      response.sendRedirect(redirectView);
      return;
    }
    RequestDispatcher rd = request.getRequestDispatcher(viewName);
    rd.forward(request, response);
  }
}
