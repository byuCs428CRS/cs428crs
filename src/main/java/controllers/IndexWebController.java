package controllers;

import exceptions.FunException;
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
@RequestMapping("/")
public class IndexWebController {

  @RequestMapping("/index")
  public @ResponseBody String index() {
    throw new FunException();
  }
}
