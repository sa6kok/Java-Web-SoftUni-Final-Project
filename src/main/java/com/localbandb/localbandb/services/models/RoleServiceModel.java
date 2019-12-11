package com.localbandb.localbandb.services.models;

public class RoleServiceModel {
  private String authority;

  public RoleServiceModel() {
  }

  public RoleServiceModel(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
