package controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import packages.Departments;
import service.PublicWebService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/public-api")
public class PublicWebController {

  private PublicWebService webService;

  public PublicWebController() {
    webService = new PublicWebService();
  }

  @RequestMapping(value = "/departments", method = GET)
  public @ResponseBody
  Departments getAllDepartments(
      @RequestParam(value = "dummy", required = false, defaultValue = "false") Boolean dummy)
  {
    if (dummy) {
      return webService.getMockDepartments();
    }
    return webService.getAllDepartments();
  }
}
