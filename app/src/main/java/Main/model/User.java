package Main.model;

import Main.utils.Roles;
import lombok.Getter;

public class User {
  @Getter
  private String username;

  @Getter
  private byte[] password;

  @Getter
  private String email;

  @Getter
  private Roles role;

  public User() {
    username = "";
    password = new byte[0];
    email = "";
    role = Roles.ADMIN;
  }

  public User(String username, byte[] password, Roles role) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.email = "";
  }

  public User(String username, byte[] password, Roles role, String email) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.email = email;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(byte[] password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRole(Roles role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public byte[] getPassword() {
    return password;
  }

  public Roles getRole() {
    return role;
  }
}
