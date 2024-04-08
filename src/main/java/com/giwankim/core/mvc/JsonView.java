package com.giwankim.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JsonView implements View {
  @Override
  public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    PrintWriter writer = response.getWriter();
    writer.write(objectMapper.writeValueAsString(createModel(request)));
  }

  private Map<String, Object> createModel(HttpServletRequest request) {
    Map<String, Object> model = new HashMap<>();
    Enumeration<String> attributeNames = request.getAttributeNames();
    while (attributeNames.hasMoreElements()) {
      String attributeName = attributeNames.nextElement();
      model.put(attributeName, request.getAttribute(attributeName));
    }
    return model;
  }
}
