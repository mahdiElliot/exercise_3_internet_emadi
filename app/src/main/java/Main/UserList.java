package Main;

import Main.model.User;
import Main.utils.Password;
import Main.utils.Roles;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;

public class UserList {
  public static ArrayList<User> users;

  static {
    try {
      users = new ArrayList<>(Collections.singletonList(new User("admin", Password.getEncodedPassword("admin"), Roles.ADMIN)));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}

