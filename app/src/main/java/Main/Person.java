package Main;

public class Person {
  private String  username;
  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Person(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
