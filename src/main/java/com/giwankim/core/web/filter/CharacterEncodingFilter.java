package com.giwankim.core.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(value = "/*")
public class CharacterEncodingFilter implements Filter {
  private static final String DEFAULT_ENCODING = "UTF-8";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    throws IOException, ServletException {
    request.setCharacterEncoding(DEFAULT_ENCODING);
    response.setCharacterEncoding(DEFAULT_ENCODING);
    filterChain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }
}
