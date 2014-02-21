package controllers;

import models.UserLogin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Random;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
/**
 * @author: Nick Humrich
 * @date: 2/20/14
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/auth")
public class AuthenticationWebController {

  @RequestMapping(value = "/login", method = POST)
  public @ResponseBody
  String login(@RequestBody UserLogin user)
  {
    return null;
  }

  @RequestMapping(value = "/test", method = GET)
  public @ResponseBody
  String test(
      @RequestParam(value = "bob", required = false, defaultValue = "2") String bob,
      HttpSession session)
  {
    Random rand = new Random();
    int i = rand.nextInt();
    session.setAttribute("uid", i);
    return "Hello";
  }

  @RequestMapping(value = "/test2", method = GET)
  public @ResponseBody
  String test2(
      @RequestParam(value = "bob", required = false, defaultValue = "2") String bob,
      HttpSession session)
  {
    return "Hello " + session.getAttribute("uid");
  }

}
