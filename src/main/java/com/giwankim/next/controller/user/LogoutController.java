package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.Controller;
import com.giwankim.next.controller.UserSessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController implements Controller {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    session.removeAttribute(UserSessionUtils.SESSION_USER_KEY);
    return "redirect:/";
  }
}
