package Main.model;

import lombok.Getter;
import lombok.Setter;

public class User {
  @Getter
  @Setter
  private String username;

  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private Roles role;

  public User() {
    username = "";
    password = "";
    email = "";
    role = Roles.ADMIN;
  }

  public User(String username, String password, Roles role) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.email = "";
  }

  public User(String username, String password, Roles role, String email) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.email = email;
  }
}
