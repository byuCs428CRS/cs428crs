package models;

/**
 * @author: Nick Humrich
 * @date: 2/20/14
 */
public class UserCredentials {
  private String username;
  private String pass;
  private String salt;
  private int pepper;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public int getPepper() {
    return pepper;
  }

  public void setPepper(int pepper) {
    this.pepper = pepper;
  }
}
