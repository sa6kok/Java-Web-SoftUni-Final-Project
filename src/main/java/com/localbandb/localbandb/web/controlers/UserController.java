package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.services.models.binding.UserBindingModel;
import com.localbandb.localbandb.services.services.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping("/login")
  public ModelAndView login() {
    return new ModelAndView("user-login");
  }


  @GetMapping("/register")
  public ModelAndView register(RedirectAttributes redirectAttributes) {
    return new ModelAndView("user-register");
  }

  @PostMapping("/register")
  public String registerConfirm(@ModelAttribute UserBindingModel model, RedirectAttributes redirectAttributes) {
    if(!model.getPassword().equals(model.getConfirmPassword())) {
      redirectAttributes.addFlashAttribute("user", model);
      return "redirect:/user/register";
    }
    if (userService.save(model)) {
      return "redirect:/user/login";
    }
    redirectAttributes.addFlashAttribute("user", model);
    return "redirect:/user/register";
  }
}
