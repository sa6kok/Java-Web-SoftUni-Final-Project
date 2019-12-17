package com.localbandb.localbandb.web.error;

import com.localbandb.localbandb.services.services.ErrorService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
  private final ErrorService errorService;

  public GlobalExceptionHandler(ErrorService errorService) {
    this.errorService = errorService;
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllErrors(Exception ex) {
    ModelAndView modelAndView = new ModelAndView("error");
    modelAndView.addObject("error",  ex.getMessage().substring(0, Math.min(ex.getMessage().length(), 33)));
    errorService.saveError(ex);
    return modelAndView;
  }
}
