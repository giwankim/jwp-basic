package com.giwankim.core.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {
  private final View view;
  private final Map<String, Object> model;

  public ModelAndView(View view, Map<String, Object> model) {
    this.view = Objects.requireNonNull(view);
    this.model = model;
  }

  public ModelAndView(View view) {
    this(view, new HashMap<>());
  }

  public ModelAndView addObject(String attributeName, Object attributeValue) {
    model.put(attributeName, attributeValue);
    return this;
  }

  public View getView() {
    return view;
  }

  public Map<String, Object> getModel() {
    return Collections.unmodifiableMap(model);
  }
}
