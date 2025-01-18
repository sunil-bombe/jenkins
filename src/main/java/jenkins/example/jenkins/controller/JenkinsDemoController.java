package jenkins.example.jenkins.controller;

import jenkins.example.jenkins.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JenkinsDemoController {

  @GetMapping("/jenkins/demo")
  public User genkinsDemoGetAPI(){
    return new User(1, "Ganesh","jenkins");
  }

}
