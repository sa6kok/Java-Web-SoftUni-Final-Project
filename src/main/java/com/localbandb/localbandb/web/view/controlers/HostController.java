package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.view.models.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/host")
public class HostController extends BaseController {

  private final UserService userService;
  private final ModelMapper mapper;

  @Autowired
  public HostController(UserService userService, ModelMapper mapper) {
    this.userService = userService;

    this.mapper = mapper;
  }


  @GetMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView register(@ModelAttribute("userRegisterModel")  UserRegisterModel userRegisterModel) {
    return new ModelAndView("host/host-register");
  }

  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public ModelAndView registerConfirm(@Valid @ModelAttribute UserRegisterModel model, BindingResult bindingResult, RedirectAttributes attributes) {
    if(bindingResult.hasErrors()) {
      return view("host/host-register");
    }

    UserServiceModel userServiceModel = mapper.map(model, UserServiceModel.class);
    userServiceModel.setRole("HOST");
    if (!userService.saveUser(userServiceModel)) {
      attributes.addFlashAttribute("message", "message");
      return redirect("host/register");
    }
    return this.redirect("user/login");
  }
}
