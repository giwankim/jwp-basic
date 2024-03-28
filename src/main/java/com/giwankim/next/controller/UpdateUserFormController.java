package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.next.model.User;
import com.giwankim.core.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserFormController implements Controller {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    User user = Database.findById(userId)
      .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
    if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
      throw new UnauthorizedException("다른 사용자의 정보를 수정할 수 없습니다.");
    }
    request.setAttribute("user", user);
    return "/user/updateForm.jsp";
  }
}
