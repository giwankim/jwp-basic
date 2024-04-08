package com.giwankim.core.mvc;

import com.giwankim.next.controller.user.UnauthorizedException;
import com.giwankim.next.controller.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
      View view = controller.handleRequest(request, response);
      view.render(request, response);
    } catch (UnauthorizedException uae) {
      response.sendError(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase());
    } catch (UserNotFoundException unfe) {
      response.sendError(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase());
    } catch (Exception e) {
      logger.error("Exception : ", e);
      response.sendError(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
  }
}
