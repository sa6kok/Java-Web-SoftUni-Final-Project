package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.annotations.PageTitle;
import com.localbandb.localbandb.web.view.models.UserViewModel;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.OperationNotSupportedException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    public static final String ADMIN = "ADMIN";
   private final UserService userService;

   @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView home( ModelAndView modelAndView) {
        List<UserViewModel> allUsersWithoutTheLoggedIn = userService.findAllUsersWithoutTheLoggedIn();
        modelAndView.addObject("users", allUsersWithoutTheLoggedIn);
        return this.view("home/admin", modelAndView );
    }

    @GetMapping("/status/{id}")
    @PageTitle(ADMIN)
    public ModelAndView statusChange(@PathVariable String id,  ModelAndView modelAndView) throws NotFoundException, OperationNotSupportedException {
        if(!userService.changeUserStatus(id))  {
            throw new OperationNotSupportedException("Could not change status!");
        }

        List<UserViewModel> allUsersWithoutTheLoggedIn = userService.findAllUsersWithoutTheLoggedIn();
        modelAndView.addObject("users", allUsersWithoutTheLoggedIn);
        return this.view("home/admin", modelAndView );
    }
}
