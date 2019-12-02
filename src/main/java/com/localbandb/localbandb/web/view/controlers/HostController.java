package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.models.HostServiceModel;
import com.localbandb.localbandb.services.services.HostService;
import com.localbandb.localbandb.web.view.models.HostLoginModel;
import com.localbandb.localbandb.web.view.models.HostRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/host")
public class HostController extends BaseController {
  private final HostService hostService;
  private final ModelMapper mapper;

  @Autowired
  public HostController(HostService hostService, ModelMapper mapper) {
    this.hostService = hostService;
    this.mapper = mapper;
  }

  @GetMapping("/login")
  public ModelAndView login(@ModelAttribute("hostLoginModel") HostLoginModel hostLoginModel) {

    return this.view("host/host-login");
  }

  @PostMapping("/login")
  public ModelAndView postLogin(@Valid @ModelAttribute HostLoginModel hostLoginModel, BindingResult bindingResult,  HttpSession session, ModelAndView modelAndView) {
    if(bindingResult.hasErrors()) {
      return this.view("host/host-login");
    }

    if (hostService.login(hostLoginModel.getUsername(), hostLoginModel.getPassword())) {
      session.setAttribute("hostUsername", hostLoginModel.getUsername());
     return this.redirect("property/show/all");
    }
    modelAndView.addObject("message", "Username and/or Password are not correct!");
    return this.view("host/host-login", modelAndView);
  }

  @GetMapping("/register")
  public ModelAndView register(@ModelAttribute("hostRegisterModel")  HostRegisterModel hostRegisterModel) {
    return this.view("host/host-register");
  }

  @PostMapping("/register")
  public ModelAndView postRegister(@Valid @ModelAttribute HostRegisterModel hostRegisterModel, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) {
      return view("host/host-register");
    }
    if (!hostService.save(mapper.map(hostRegisterModel, HostServiceModel.class))) {
      return this.redirect("host/register");
    }
    return this.redirect("host/login");
  }
}
