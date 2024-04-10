package com.giwankim.core.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface Controller {
  ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
