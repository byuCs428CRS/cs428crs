package controllers;

import models.Department;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AdminWebService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/admin-api")
public class AdminWebController {

  private AdminWebService webService;

  public AdminWebController() {
    webService = new AdminWebService();
  }

  @RequestMapping(value = "/department", method = POST)
  public @ResponseBody String addDepartment(@RequestBody Department department) {
    webService.addDepartment(department);
    return "ok";
  }

}
