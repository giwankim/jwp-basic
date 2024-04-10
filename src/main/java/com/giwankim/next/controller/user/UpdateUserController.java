package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.UnauthorizedException;
import com.giwankim.next.controller.UserSessionUtils;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserController extends AbstractController {
  private static final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);

  private final UserDao userDao;

  public UpdateUserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    User user = userDao.findByUserId(userId)
      .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
    if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
      throw new UnauthorizedException("다른 사용자의 정보를 수정할 수 없습니다.");
    }

    User updateUser = new User(
      request.getParameter("userId"),
      request.getParameter("password"),
      request.getParameter("name"),
      request.getParameter("email"));
    userDao.update(updateUser);
    logger.debug("update user : {}", updateUser);

    return jspView("redirect:/");
  }
}
