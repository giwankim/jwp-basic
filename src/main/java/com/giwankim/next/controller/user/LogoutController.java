package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.*;
import com.giwankim.next.controller.UserSessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController extends AbstractController {

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    session.removeAttribute(UserSessionUtils.SESSION_USER_KEY);
    return jspView("redirect:/");
  }
}
