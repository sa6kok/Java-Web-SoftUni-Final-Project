package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.web.models.UserLoginModel;
import com.localbandb.localbandb.web.models.UserRegisterModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
  private final UserService userService;
  private final ModelMapper mapper;

  @Autowired
  public UserController(UserService userService, ModelMapper mapper) {
    this.userService = userService;
    this.mapper = mapper;
  }

  @GetMapping("/login")
  public ModelAndView login() {
    return new ModelAndView("user/user-login");
  }

  @PostMapping("/login")
  public ModelAndView postLogin(@ModelAttribute UserLoginModel model, HttpSession session) {
   if(userService.login(model.getUsername(), model.getPassword())) {
     session.setAttribute("userUsername", model.getUsername());
    return this.redirect("property/show/all");
   }
   return this.redirect("user/login");
  }


  @GetMapping("/register")
  public ModelAndView register() {
    return new ModelAndView("user/user-register");
  }

  @PostMapping("/register")
  public String registerConfirm(@ModelAttribute UserRegisterModel model, RedirectAttributes redirectAttributes) {
    if (!userService.save(mapper.map(model, UserServiceModel.class))) {
      redirectAttributes.addFlashAttribute("user", model);
      return "redirect:/user/register";

    }
    return "redirect:/user/login";
  }
}
