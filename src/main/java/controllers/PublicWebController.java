package controllers;

import catalogData.SemesterDownloader;
import exceptions.NotAuthorizedException;
import models.Schedule;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import packages.Courses;
import packages.Schedules;
import service.PublicWebService;
import exceptions.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/public-api")
public class PublicWebController
{
    private boolean shuttingDown;
    private int countdown;

    private PublicWebService webService;
    private HashMap<String,Courses> cachedCourses;
    private List<String> cachedSemesters;
    private String replaceWithImplementatedBYUPlannedCourses;

    public PublicWebController()
    {
    	cachedCourses = new HashMap<String,Courses>();
        countdown = 5;
        shuttingDown = false;
        replaceWithImplementatedBYUPlannedCourses = "";
        webService = new PublicWebService();
    }

  @RequestMapping(value = "/shutdown", method = POST)
  @ResponseStatus(value = HttpStatus.OK)
  public void shutdown(HttpServletRequest req)
  {
    //for temparary testing
    //System.out.println(req.getRemoteAddr());

    if (!req.getRemoteAddr().matches("172[.]31[.]\\d{1,3}[.]\\d{1,3}|localhost|127[.]0[.]0[.]1")) {
      throw new ResourceNotFoundException();
    }
    shuttingDown = true;
  }

  @RequestMapping(value = "/health", method = GET)
  @ResponseStatus(value = HttpStatus.OK)
  public void healthCheck(HttpServletRequest req)
  {
    //for temporary testing
    //System.out.println(req.getRemoteAddr());

    if (!req.getRemoteAddr().matches("172[.]31[.]\\d{1,3}[.]\\d{1,3}|localhost|127[.]0[.]0[.]1")) {
      throw new ResourceNotFoundException();
    }
    if (shuttingDown) {
      if (countdown-- <= 0) {
        System.exit(0);
      }
      //ToDo: should probably be changed to a 503 instead of a 500
      throw new ServerException("Server Shutting Down");
    }
  }


    //TODO:: change this method to return semesters that
    // are published in the database, SemesterDownloader.getSemesterCodes()
    // needs to be changed to webServie.getSemesterCodes() (needs to be implemented)
    // the webService.getSemesterCodes() needs to talk to the DatabaseRegistrationStore/SemesterDAO
    // and get all of the semesterCodes that are in the database
    //
    // The object that is returned as a List<String> of semester codes
    // ie.  { "20151", "20153" }
    @RequestMapping(value = "/semesters", method = GET)
    public
    @ResponseBody
    List<String> getSemesters()
    {
        if (cachedSemesters == null)
        {
            cachedSemesters = SemesterDownloader.getSemesterCodes();
        }
        return cachedSemesters;
    }

    /**
     * Returns the courses for the requested semester
     *
     * @param sem_id
     * @return
     */
    @RequestMapping(value = "/courses/{sem_id}", method = GET)
    public
    @ResponseBody
    Courses getCourses(@PathVariable String sem_id)
    {
    	if(!cachedCourses.containsKey(sem_id)){
    		cachedCourses.put(sem_id, webService.getCourses(sem_id));
    	}
        return cachedCourses.get(sem_id);
    }


    /**
     * Returns all schedules for the signed in student;
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/schedules/all", method = GET)
    public
    @ResponseBody
    Schedules getSchedulesForUser(HttpSession session)
    {
        String uid = getUserId(session);
        return webService.getAllSchedulesForUser(uid);
    }

    /**
     * Saves the given schedule in the database
     *
     * @param schedule
     * @param session
     */
    @RequestMapping(value = "/saveSchedule", method = POST,
            consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveSchedule(
            @RequestBody Schedule schedule, HttpSession session)
    {
        String uid = getUserId(session);
        webService.saveSchedule(uid, schedule);
    }

    /**
     * Loads the schedule for the semester
     *
     * @param sem_id  the semesterID of the schedule requested
     * @param session
     * @return
     */
    @RequestMapping(value = "/loadSchedule/{sem_id}", method = GET)
    public
    @ResponseBody
    Schedule
    getSchedule(@PathVariable String sem_id, HttpSession session)
    {
        String uid = getUserId(session);
        return webService.getSchedule(uid, sem_id);
    }
    
    /**
     * The method accepts a JSON blob (the data from https://home.byu.edu/webapp/mymap/organize/data.json )
     * and store it in the database
     */
    @RequestMapping(value = "/saveCoursesFromMyMap", method = POST,
            consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveCoursesFromMyMap(@RequestBody String mymapPlanned, HttpSession session)
    {
        //TODO: This needs to be implemented;
    	replaceWithImplementatedBYUPlannedCourses = mymapPlanned;
    }
    
    /**
     * The method returns the JSON blob
     * stored in the database
     */
    @RequestMapping(value = "/loadCoursesFromMyMap", method = GET)
    public
    @ResponseBody
    String loadCoursesFromMyMap(HttpSession session)
    {
        String uid = getUserId(session);
        //TODO: This needs to be implemented, does not need to return a string necessarily. It can return an object.
        // just needs to return something like this: 
        return replaceWithImplementatedBYUPlannedCourses;
    }
    
    /**
     * returns the UID (netid) of the user logged in 
     * @param session
     * @return
     */
    private String getUserId(HttpSession session)
    {
        Object uid = session.getAttribute("uid");
        if (uid == null)
        {
            throw new NotAuthorizedException("User must be logged in.");
        }
        return (String) uid;
    }
    
    /**
     * Clears the cached objects, this method could be changed to 
     * automatically regenerate the cache.
     * @return "success"
     */
    @RequestMapping(value = "dbupdated", method = GET)
    public
    @ResponseBody
    String dbUpdated()
    {
        cachedCourses = new HashMap<String,Courses>();
        cachedSemesters = null;
        return "success";
    }

    @RequestMapping(value = "recaptcha", method = GET)
    public
    @ResponseBody
    String getRecaptcha()
    {
        URL url;
        HttpURLConnection conn;
        BufferedReader br;
        String line;
        StringBuilder result = new StringBuilder();
        try
        {
            url = new URL("https://www.google.com/recaptcha/api/challenge?k=6LfoisoSAAAAAFBP_LvBQ4YlpPTBOf12MnGsjk4z");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            String response = IOUtils.toString(reader);
            reader.close();
            List<Integer> braceIndexes = new ArrayList<>(2);
            boolean inQuotes = false, jsonObjectFound = false;
            char closingQuotesChar = '\0';
            for (int i = 0; i < response.length() && !jsonObjectFound; i++)
            {
                if (!inQuotes)
                {
                    switch (response.charAt(i))
                    {
                        case '"':
                        case '\'':
                            inQuotes = true;
                            closingQuotesChar = response.charAt(i);
                            break;
                        case '{':
                        case '}':
                            braceIndexes.add(i);
                            if (braceIndexes.size() == 2)
                            {
                                jsonObjectFound = true;
                            }
                            break;

                    }
                } else if (closingQuotesChar == response.charAt(i))
                {
                    inQuotes = false;
                }
            }
            if (braceIndexes.size() != 2)
            {
                throw new RuntimeException("Google returned invalid JSON: " + response);
            }
            JSONObject googleJSON = new JSONObject(response.substring(braceIndexes.get(0), braceIndexes.get(1) + 1));
            JSONObject ourJSON = new JSONObject();
            ourJSON.put("challenge", googleJSON.get("challenge"));
            ourJSON.put("image", getRecaptchaImage(googleJSON.get("challenge").toString()));

            return ourJSON.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
            return e.getMessage();
        } catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    String getRecaptchaImage(String recaptchaChallenge) throws IOException
    {
        URL url = new URL("https://www.google.com/recaptcha/api/image?c=" + recaptchaChallenge);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
        String result = new String(Base64.encodeBase64(IOUtils.toByteArray(is)), "UTF-8");
        is.close();
        return result;
    }
}
