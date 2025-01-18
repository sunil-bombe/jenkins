package jenkins.example.jenkins.model;

public class User {
  private Integer userId;

  private String userName;

  private String subject;

  public User(Integer userId, String userName, String subject) {
    this.userId = userId;
    this.userName = userName;
    this.subject = subject;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getSubject() {
    return subject;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", userName='" + userName + '\'' +
        ", subject='" + subject + '\'' +
        '}';
  }
}
