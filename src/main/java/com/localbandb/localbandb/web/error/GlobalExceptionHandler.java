package com.localbandb.localbandb.web.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllErrors(Exception ex) {
    ModelAndView modelAndView = new ModelAndView("error");
    modelAndView.addObject("error",  ex.getMessage().substring(0, 33));
    ex.printStackTrace();
    return modelAndView;
  }
}
