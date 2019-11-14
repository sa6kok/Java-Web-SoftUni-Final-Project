package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.services.models.HostServiceModel;
import com.localbandb.localbandb.services.services.HostService;
import com.localbandb.localbandb.web.models.HostLoginModel;
import com.localbandb.localbandb.web.models.HostRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
  public ModelAndView login() {
    return this.view("host/host-login");
  }

  @PostMapping("/login")
  public ModelAndView postLogin(@ModelAttribute HostLoginModel model, HttpSession session) {
    if (hostService.login(model.getUsername(), model.getPassword())) {
      session.setAttribute("hostUsername", model.getUsername());
     return this.redirect("property/show/all");
    }
    return this.redirect("host/login");
  }

  @GetMapping("/register")
  public ModelAndView register() {
    return this.view("host/host-register");
  }

  @PostMapping("/register")
  public ModelAndView postRegister(@ModelAttribute HostRegisterModel model) {
    if (!hostService.save(mapper.map(model, HostServiceModel.class))) {
      return this.redirect("host/register");
    }
    return this.redirect("host/login");
  }
}
