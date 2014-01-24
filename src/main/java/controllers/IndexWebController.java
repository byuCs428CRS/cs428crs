package controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
@Controller
@EnableAutoConfiguration
public class IndexWebController {

  @RequestMapping("/")
  public @ResponseBody String index() {
    return "Hello from CRS!";
  }
}
