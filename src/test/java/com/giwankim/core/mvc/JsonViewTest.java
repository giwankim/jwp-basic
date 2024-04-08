package com.giwankim.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonViewTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  PrintWriter writer;

  JsonView sut;

  @BeforeEach
  void setUp() {
    sut = new JsonView();
  }

  @Test
  void shouldSetContentType() throws Exception {
    when(request.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
    when(response.getWriter()).thenReturn(writer);

    sut.render(request, response);

    verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
  }

  @Test
  void shouldWriteAttributesToResponse() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    when(request.getAttributeNames()).thenReturn(Collections.enumeration(List.of("attribute1", "attribute2")));
    when(request.getAttribute("attribute1")).thenReturn("value1");
    when(request.getAttribute("attribute2")).thenReturn("value2");
    when(response.getWriter()).thenReturn(writer);
    Map<String, Object> model = new LinkedHashMap<>();
    model.put("attribute1", "value1");
    model.put("attribute2", "value2");

    sut.render(request, response);

    verify(writer).write(objectMapper.writeValueAsString(model));
  }
}