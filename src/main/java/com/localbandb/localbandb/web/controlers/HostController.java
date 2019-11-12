package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host")
public class HostController {

  @GetMapping("/login")
  public String login() {
    return "host-login";
  }

  @GetMapping("/register")
  public String register() {
    return "host-register";
  }
}
