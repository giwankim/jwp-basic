package com.giwankim.core.mvc;

import com.giwankim.next.controller.user.UnauthorizedException;
import com.giwankim.next.controller.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

  public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

  private RequestMapping requestMapping;

  @Override
  public void init() throws ServletException {
    requestMapping = new RequestMapping();
    requestMapping.init();
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

    Controller controller = requestMapping.getController(requestURI);
    if (controller == null) {
      response.sendError(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase());
      return;
    }

    try {
      String viewName = controller.execute(request, response);
      move(viewName, request, response);
    } catch (UnauthorizedException uae) {
      response.sendError(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase());
    } catch (UserNotFoundException unfe) {
      response.sendError(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase());
    } catch (Exception e) {
      logger.error("Exception : ", e);
      response.sendError(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
  }

  private void move(String viewName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
      response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
      return;
    }
    RequestDispatcher rd = request.getRequestDispatcher(viewName);
    rd.forward(request, response);
  }
}
