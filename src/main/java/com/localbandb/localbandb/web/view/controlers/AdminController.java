package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.annotations.PageTitle;
import com.localbandb.localbandb.web.view.models.UserViewModel;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.OperationNotSupportedException;
import java.util.List;

import static com.localbandb.localbandb.web.view.constants.Constants.*;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    @Secured("ROLE_ADMIN")
    public ModelAndView home(ModelAndView modelAndView) {
        List<UserViewModel> allUsersWithoutTheLoggedIn = userService.findAllUsersWithoutTheLoggedIn();
        modelAndView.addObject("users", allUsersWithoutTheLoggedIn);
        return this.view("home/admin", modelAndView);
    }

    @GetMapping("/status/{id}")
    @Secured("ROLE_ADMIN")
    @PageTitle(ADMIN)
    public ModelAndView statusChange(@PathVariable String id, ModelAndView modelAndView) throws NotFoundException, OperationNotSupportedException {
        if (!userService.changeUserStatus(id)) {
            throw new OperationNotSupportedException(COULD_NOT_CHANGE_STATUS);
        }

        List<UserViewModel> allUsersWithoutTheLoggedIn = userService.findAllUsersWithoutTheLoggedIn();
        modelAndView.addObject("users", allUsersWithoutTheLoggedIn);
        return this.view("home/admin", modelAndView);
    }
}
