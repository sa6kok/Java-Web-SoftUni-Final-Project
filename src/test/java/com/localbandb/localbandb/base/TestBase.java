package com.localbandb.localbandb.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WithMockUser(roles={"HOST", "ADMIN", "GUEST"})
@SpringBootTest
public abstract class TestBase {
  @BeforeEach
  public void setupTest() {
    MockitoAnnotations.initMocks(this);
    this.beforeEach();
  }

  public void beforeEach() {
  }
}