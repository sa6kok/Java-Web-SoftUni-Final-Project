package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.web.view.models.UserLoginModel;
import com.localbandb.localbandb.web.view.models.UserRegisterModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
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
  public ModelAndView login(@ModelAttribute("userLoginModel") UserLoginModel userLoginModel) {

    return new ModelAndView("user/user-login");
  }

  @PostMapping("/login")
  public ModelAndView postLogin(@Valid @ModelAttribute UserLoginModel userLoginModel,BindingResult bindingResult,  HttpSession session, ModelAndView modelAndView) {
    if(bindingResult.hasErrors()) {
      return this.view("user/user-login");
    }

   if(userService.login(userLoginModel.getUsername(), userLoginModel.getPassword())) {
     session.setAttribute("userUsername", userLoginModel.getUsername());
    return this.redirect("property/show/all");
   }
   modelAndView.addObject("message", "Username and/or Password are not correct!");
   return this.view("user/user-login",  modelAndView);
  }


  @GetMapping("/register")
  public ModelAndView register(@ModelAttribute("userRegisterModel")  UserRegisterModel userRegisterModel) {
    return new ModelAndView("user/user-register");
  }

  @PostMapping("/register")
  public ModelAndView registerConfirm(@Valid @ModelAttribute UserRegisterModel model, BindingResult bindingResult, RedirectAttributes attributes) {
    if(bindingResult.hasErrors()) {
      return view("user/user-register");
    }

    if (!userService.save(mapper.map(model, UserServiceModel.class))) {
      attributes.addFlashAttribute("message", "message");
      return redirect("user/register");
    }
    return this.redirect("user/login");
  }
}
