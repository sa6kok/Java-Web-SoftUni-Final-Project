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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/host")
public class HostController extends BaseController {
  public static String HOST_LOGIN_VIEW = "host/host-login";
  public static String HOST_REGISTER_VIEW = "host/host-register";
  public static String USERNAME_PASSWORD_NOT_CORRECT = "Username and/or Password are not correct!";

  private final HostService hostService;
  private final ModelMapper mapper;

  @Autowired
  public HostController(HostService hostService, ModelMapper mapper) {
    this.hostService = hostService;
    this.mapper = mapper;
  }

  @GetMapping("/login")
  public ModelAndView login(@ModelAttribute("hostLoginModel") HostLoginModel hostLoginModel) {
    return this.view(HOST_LOGIN_VIEW);
  }

  @PostMapping("/login")
  public ModelAndView postLogin(@Valid @ModelAttribute HostLoginModel hostLoginModel, BindingResult bindingResult,  HttpSession session, ModelAndView modelAndView) {
    if(bindingResult.hasErrors()) {
      return this.view(HOST_LOGIN_VIEW);
    }

    if (hostService.login(hostLoginModel.getUsername(), hostLoginModel.getPassword())) {
      session.setAttribute("hostUsername", hostLoginModel.getUsername());
     return this.redirect("property/show/all");
    }
    modelAndView.addObject("message", USERNAME_PASSWORD_NOT_CORRECT );
    return this.view(HOST_LOGIN_VIEW, modelAndView);
  }

  @GetMapping("/register")
  public ModelAndView register(@ModelAttribute("hostRegisterModel")  HostRegisterModel hostRegisterModel) {
    return this.view(HOST_REGISTER_VIEW);
  }

  @PostMapping("/register")
  public ModelAndView postRegister(@Valid @ModelAttribute HostRegisterModel hostRegisterModel, BindingResult bindingResult, RedirectAttributes attributes) {
    if(bindingResult.hasErrors()) {
      return view(HOST_REGISTER_VIEW);
    }
    if (!hostService.save(mapper.map(hostRegisterModel, HostServiceModel.class))) {
      attributes.addFlashAttribute("message", "message");
      return this.redirect("host/register");
    }
    return this.redirect("host/login");
  }
}
