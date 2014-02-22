package controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import packages.Courses;
import packages.Departments;
import packages.Requirements;
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

}
