package com.giwankim.core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

public class JspView implements View {
  private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

  private final String viewName;

  public JspView(String viewName) {
    this.viewName = viewName;
  }

  public static JspView from(String viewName) {
    return new JspView(viewName);
  }

  @Override
  public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
      String redirectRoute = viewName.substring(DEFAULT_REDIRECT_PREFIX.length());
      response.sendRedirect(redirectRoute);
      return;
    }
    model.forEach(request::setAttribute);
    RequestDispatcher rd = request.getRequestDispatcher(viewName);
    rd.forward(request, response);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof JspView jspView)) return false;
    return Objects.equals(viewName, jspView.viewName);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(viewName);
  }
}
