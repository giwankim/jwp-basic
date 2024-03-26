package core.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(value = "/*")
public class ResourceFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(ResourceFilter.class);

  private static final List<String> resourcePrefixes = Arrays.asList("/css", "/js", "/fonts", "/images", "/favicon.ico");

  private RequestDispatcher defaultRequestDispatcher;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    if (isResourceURL(path)) {
      logger.debug("path : {}", path);
      defaultRequestDispatcher.forward(request, response);
    } else {
      filterChain.doFilter(request, response);
    }
  }

  private static boolean isResourceURL(String path) {
    return resourcePrefixes.stream().anyMatch(path::startsWith);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    defaultRequestDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
  }

  @Override
  public void destroy() {
  }
}
