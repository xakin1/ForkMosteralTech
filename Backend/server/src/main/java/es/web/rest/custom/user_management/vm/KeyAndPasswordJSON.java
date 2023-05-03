package es.web.rest.custom.user_management.vm;

/** View Model object for storing the user's key and password. */
public class KeyAndPasswordJSON {

  private String key;

  private String newPassword;

  public KeyAndPasswordJSON() {}

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
