package service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.security.SecureRandom;

/**
 * @author: Nick Humrich
 * @date: 2/21/14
 */
public class AuthenticationService {
  public static void main(String[] args) {
    AuthenticationService auth = new AuthenticationService();
    auth.testDoHash();
  }

  public void testDoHash() {
    String myPass = "myPass";

    for (int i = 0; i < 10; i++) {
      final String hashed = Hashing.sha256().hashString(myPass, Charsets.UTF_8).toString();
      myPass = hashed;
    }
    System.out.println(myPass);
    String salt = getRandomSalt();
    final String hashed = Hashing.sha256().hashString(salt + myPass, Charsets.UTF_8).toString();
    System.out.println(hashed + "  salt: " + salt);
  }

  public String getRandomSalt() {
    final int SALT_LENGTH = 16;

    final String salter = "zPxOcIvUbYnTnEmWlQ1L2k3j4h5g6f7d8s9a0ZpXoCiVuByNtMrAqG";
//    Random random = new Random();
    StringBuilder salt = new StringBuilder();
    SecureRandom random = new SecureRandom();
    for (int i = 0; i < SALT_LENGTH; i++) {
      int next = random.nextInt();
      if (next < 0) next *= -1;
      int index = next % salter.length();
      char nextChar = salter.charAt(index);
      salt.append(nextChar);
    }
    return salt.toString();
  }
}
