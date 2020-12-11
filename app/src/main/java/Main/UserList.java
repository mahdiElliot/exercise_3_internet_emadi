package Main;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;

public class UserList {
  public static ArrayList<Person> users;

  static {
    try {
      users = new ArrayList<>(Collections.singletonList(new Person("admin", Password.getEncodedPassword("admin"), Roles.ADMIN)));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}

