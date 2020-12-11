package Main;

import lombok.Getter;

public class Person {
  @Getter
  private String  username;
  @Getter
  private byte[] password;
  @Getter
  private Roles role;

  public Person(String username, byte[] password, Roles role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
