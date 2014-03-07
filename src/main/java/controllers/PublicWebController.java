package controllers;

import exceptions.NotAuthorizedException;
import models.Schedule;
import org.apache.http.client.methods.HttpGet;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import packages.Courses;
import packages.Departments;
import packages.Requirements;
import packages.Schedules;
import service.PublicWebService;

import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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

	/**
	 * Gets all departments that exist in the systen
	 * @param dummy a boolean stating if you want dummy data
	 * @return fully populated list of departments
	 */
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

	/**
	 * Gets all requirements for the given major.a If no major is given or major 'none', then all
	 * the general requirements will be given
	 * @param major shortCode for major
	 * @return requirements for given major
	 */
	@RequestMapping(value = "/requirements", method = GET)
	public @ResponseBody
	Requirements getRequirements(
		@RequestParam(value = "major", required = false, defaultValue = "none") String major,
		@RequestParam(value = "dummy", required = false, defaultValue = "false")Boolean dummy)
	{
		if (dummy) {
			return webService.getMockRequirements(major);
		}
		return webService.getRequirements(major);

	}

	@RequestMapping(value = "/courses", method = GET)
	public @ResponseBody
	Courses getCourses(
		@RequestParam(value = "ids", required = true) String ids)
	{

		return new Courses();
	}

  @RequestMapping(value = "/schedules/all", method = GET)
  public @ResponseBody
  Schedules getSchedulesForUser(HttpSession session)
  {
    String uid = getUserId(session);
    return webService.getAllSchedulesForUser(uid);
  }

  @RequestMapping(value = "/schedules", method = POST,
      consumes = "application/json")
  @ResponseStatus(value = HttpStatus.OK)
  public void addSchedule(
      @RequestBody Schedule schedule, HttpSession session)
  {
    String uid = getUserId(session);
    webService.addSchedule(uid, schedule);
  }

  @RequestMapping(value = "/schedules/{id}", method = PUT,
      consumes = "application/json")
  @ResponseStatus(value = HttpStatus.OK)
  public void updateSchedule(@RequestBody Schedule schedule,
       @PathVariable String id, HttpSession session)
  {
    String uid = getUserId(session);
    webService.editSchedule(uid, id, schedule);
  }

  @RequestMapping(value = "schedules/{id}", method = DELETE,
    consumes = "application/json")
  @ResponseStatus(value = HttpStatus.OK)
  public void removeSchedule(@PathVariable String id, HttpSession session)
  {
    String uid = getUserId(session);
    webService.removeSchedule(uid, id);
  }

  @RequestMapping(value = "schedules/{id}", method = GET)
  public @ResponseBody Schedule
  getSchedule(@PathVariable String id, HttpSession session)
  {
    String uid = getUserId(session);
    return webService.getSchedule(uid, id);
  }

  @RequestMapping(value = "recaptcha", method = GET)
  public @ResponseBody String getRecaptcha()
  {
	  URL url;
	  HttpURLConnection conn;
	  BufferedReader rd;
	  String line;
	  StringBuilder result = new StringBuilder();
	  try {
		  url = new URL("https://www.google.com/recaptcha/api/challenge?k=6LfoisoSAAAAAFBP_LvBQ4YlpPTBOf12MnGsjk4z");
		  conn = (HttpURLConnection) url.openConnection();
		  conn.setRequestMethod("GET");
		  rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		  while ((line = rd.readLine()) != null) {
			  result.append(line);
		  }
		  rd.close();
	  } catch (IOException e) {
		  e.printStackTrace();
		  return e.getMessage();
	  } catch (Exception e) {
		  e.printStackTrace();
		  return e.getMessage();
	  }
	  return result.toString();
  }

  private String getUserId(HttpSession session) {
    Object uid = session.getAttribute("uid");
    if (uid == null) {
      throw new NotAuthorizedException("User must be logged in.");
    }
    return (String) uid;
  }

}
