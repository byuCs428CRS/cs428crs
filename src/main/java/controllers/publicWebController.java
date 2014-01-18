package controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import packages.Majors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
@Controller
@EnableAutoConfiguration
public class publicWebController {

  @RequestMapping(value = "/public-api/majors", method = GET)
  public @ResponseBody Majors getAllMajors() {
    return new Majors();
  }
}
