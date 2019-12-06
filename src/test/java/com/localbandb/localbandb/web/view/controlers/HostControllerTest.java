package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.data.models.Host;
import com.localbandb.localbandb.data.repositories.HostRepository;
import com.localbandb.localbandb.services.models.HostServiceModel;
import com.localbandb.localbandb.services.services.HostService;
import com.localbandb.localbandb.web.base.ViewTestBase;
import com.localbandb.localbandb.web.view.models.HostLoginModel;
import com.localbandb.localbandb.web.view.models.HostRegisterModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class HostControllerTest extends ViewTestBase {

  @MockBean
  HostRepository hostRepository;

  @MockBean
  HostService hostService;

  @MockBean
  BindingResult bindingResult;

  @Test
  void hostLogin_onGet_shouldReturnLoginView() throws Exception {
    mockMvc.perform(get("/host/login"))
        .andExpect(status().isOk())
        .andExpect(view().name(HostController.HOST_LOGIN_VIEW));

  }

  @Test
  void postLogin_whenBindingResultHasErrors_shouldReturnTheSameView() throws Exception {
    HostLoginModel hostLoginModel = new HostLoginModel();

 Mockito.when(hostService.login(hostLoginModel.getUsername(), hostLoginModel.getPassword())).thenReturn(true);
    mockMvc.perform(post("/host/login", hostLoginModel))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/property/show/all"));
  }

  @Test
  void postLogin_whenBindingResultHasNoErrorsAndDataIsIncorrect_shouldReturnTheSameView() throws Exception {
    HostLoginModel hostLoginModel = new HostLoginModel("sa6kok", "1234");

    Mockito.when(hostService.login(hostLoginModel.getUsername(), hostLoginModel.getPassword())).thenReturn(false);
    mockMvc.perform(post("/host/login", hostLoginModel))
        .andExpect(status().isOk())
        .andExpect(view().name(HostController.HOST_LOGIN_VIEW));
  }

  @Test
  void registerGet_shouldReturnRegisterView() throws Exception {
    mockMvc.perform(get("/host/register"))
        .andExpect(status().isOk())
        .andExpect(view().name(HostController.HOST_REGISTER_VIEW));
  }

  @Test
  void postRegister_withoutData_shouldReturnRegisterView() throws Exception {
    mockMvc.perform(post("/host/register"))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/host/register"));
  }
}