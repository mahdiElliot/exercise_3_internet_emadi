package Main.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Password {
  private final static String salt = "salt";

  public static String  getEncodedPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 49999, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    return Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
  }
}
