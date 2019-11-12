package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.data.models.Property;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {


  @GetMapping("/")
  public ModelAndView showIndex(ModelAndView modelAndView)  {
    modelAndView.setViewName("index");
    return modelAndView;
  }
}
