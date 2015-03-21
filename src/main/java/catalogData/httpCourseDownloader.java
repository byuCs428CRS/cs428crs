package catalogData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class httpCourseDownloader {

    private static String executePost(String targetURL, String urlParameters)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      
	      boolean firstLine = true;
	      while((line = rd.readLine()) != null) {
	       
	    	if (firstLine)
	    		firstLine = false;
	    	else
	    		response.append('\r');
	    	response.append(line);
	      }
	      
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
          System.out.println("Continuing :)");
	      return "";

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
	 
    public static String downloadCourses(String semesterCode) throws FileNotFoundException, UnsupportedEncodingException {
        
        StringBuilder writer = new StringBuilder();
        String creditType = "A"; //Figure out what this means, also "S"
        
        for(String dept : DepartmentDownloader.getHTMLdeptCodes(semesterCode)){
           System.out.println(dept);
           
           String targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/searchXML.php";
           String urlParams = "SEMESTER=" + semesterCode + "&CREDIT_TYPE=" + creditType + "&DEPT="+ dept +"&INST=&DESCRIPTION=&DAYFILTER=&BEGINTIME=&ENDTIME=&SECTION_TYPE=&CREDITS=&CREDITCOMP=&CATFILTER=&BLDG=";
           String out = executePost(targetURL, urlParams);


           writer.append(out);
        }
        return removeHtmlFromFile(writer.toString());
       
    }


    private static String removeHtmlFromFile(String rawInput) throws FileNotFoundException {
        String htmlString = rawInput;
        htmlString=htmlString.replaceAll("<br>","\n");
        htmlString=htmlString.replaceAll("</li>","\n");
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");
        return noHTMLString;
    }
}
