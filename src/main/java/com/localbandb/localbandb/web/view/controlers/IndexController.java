package com.localbandb.localbandb.web.view.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends BaseController {
  @GetMapping("/")
  public ModelAndView showIndex()  {
    return this.view("home/index");
  }
}
