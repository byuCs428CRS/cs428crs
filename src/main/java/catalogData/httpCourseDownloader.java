package catalogData;

import models.Course;
import models.Section;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


//import java.io.*;


public class httpCourseDownloader {
	private static String[] DEPTS = {"A+HTG", "ACC", "AEROS", "AFRIK", "AM+ST", "ANES", "ANTHR", "ARAB", "ARTHC", "ASIAN", "ASL", "BIO", "BULGN", "BUS+M", "C+S", "CANT", "CE+EN", "CH+EN", "CHEM", "CHIN", "CL+CV", "CLSCS", "CM", "CMLIT", "CMPST", "COMD", "COMMS", "CPSE", "CSANM", "CZECH", "DANCE", "DIGHT", "DUTCH", "EC+EN", "ECE", "ECON", "EDLF", "EIME", "EL+ED", "ELANG", "EMBA", "ENG+T", "ENGL", "ESL", "EUROP", "EXSC", "FIN", "FINN", "FLANG", "FNART", "FPM", "FREN", "GEOG", "GEOL", "GERM", "GREEK", "HCOLL", "HEB", "HIST", "HLTH", "HONRS", "IAS", "ICLND", "IHUM", "INDES", "IP&T", "IR", "IS", "IT", "ITAL", "JAPAN", "KOREA", "LATIN", "LAW", "LFSCI", "LING", "LINGC", "LT+AM", "M+B+A", "M+COM", "MATH", "ME+EN", "MESA", "MFG", "MFHD", "MFT", "MIL+S", "MMBIO", "MTHED", "MUSIC", "NDFS", "NE+LG", "NES", "NEURO", "NORWE", "NURS", "ORG+B", "P+MGT", "P+POL", "PDBIO", "PETE", "PHIL", "PHSCS", "PHY+S", "PL+SC", "POLSH", "PORT", "PSYCH", "PWS", "RECM", "REL+A", "REL+C", "REL+E", "ROM", "RUSS", "SC+ED", "SCAND", "SFL", "SLAT", "SOC", "SOC+W", "SPAN", "STAC", "STAT", "STDEV", "SWED", "T+ED", "TECH", "TEE", "TELL", "TMA", "UNIV", "VA", "VAANM", "VADES", "VAEDU", "VAGD", "VAILL", "VAPHO", "VASTU", "WELSH", "WRTG", "WS"};
	//TODO - These DEPTS need to be updated instead of hardcoded

    public static String excutePost(String targetURL, String urlParameters)
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
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
	 
	 public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

         String fileName = "AMGData.txt";
		 PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		 
		 //getCourseData(writer);
		 
		 for(String dept : DEPTS){
			System.out.println(dept);
			
			//writer.println("<br>DEPARTMENT= " + dept + "<br>"); 
			
			String targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/searchXML.php";
			String urlParams = "SEMESTER=20135&CREDIT_TYPE=A&DEPT="+ dept +"&INST=&DESCRIPTION=&DAYFILTER=&BEGINTIME=&ENDTIME=&SECTION_TYPE=&CREDITS=&CREDITCOMP=&CATFILTER=&BLDG=";
			String out = excutePost(targetURL, urlParams);

			writer.println(out);

			
			//getCourseData(writer);

		 }
		writer.close();
        removeHtml(fileName);
	}


    public static void removeHtml(String fileName) throws FileNotFoundException {
        String htmlString = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
        htmlString=htmlString.replaceAll("<br>","\n");
        htmlString=htmlString.replaceAll("</li>","\n");
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");
        PrintWriter out = new PrintWriter("New" + fileName);
        //System.out.println(noHTMLString);
        out.println(noHTMLString);
        out.close();
        //CompareFile();
    }


    private static void CompareFile() throws Exception {

        File f1 = new File("D:\\CS340\\CS340\\Test\\src\\TestCatalog.txt");
        File f2 = new File("D:\\CS340\\CS340\\Test\\src\\catalog.txt");

        FileReader fR1 = new FileReader(f1);
        FileReader fR2 = new FileReader(f2);

        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = null;
        String line2 = null;
        int count =0;

        while (((line1 = reader1.readLine()) != null) &&((line2 = reader2.readLine()) != null)) {
            if (!line1.equalsIgnoreCase(line2)) {
                System.out.println("The files are DIFFERENT on line "+ count);
            } else {
                //   System.out.println("The files are identical on line "+ count);
            }
            count++;

        }
        reader1.close();
        reader2.close();
    }

    public static void getCourseData(PrintWriter writer, Section s, Course c) {
		String targetURL;
		String urlParams;
		//Individual Course HTTP requests here
		//Need to parse this Data
		//Outcomes, Catalog, Syllabus
		String courseID = c.getCourseID(); //"02859";
		String titleCode = c.getNewTitleCode(); //"006";
		//Catalog, Syllabus
		String section = s.getSectionID();//"052";
		String yearTerm = "20135";
		String creditType= "S";
		//Only Syllabus
		String department = c.getDepartment();//"A+HTG";
		String CAT = "100";
		
		//Outcomes
		targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getOutcomes.php";
		urlParams ="CUR_ID="+ courseID + "&TITLE_CODE=" + titleCode;
        System.out.println("\n\nURL:\n"+ targetURL +"?"+ urlParams);
		String outcomes = excutePost(targetURL, urlParams);
		
		writer.println("<br>OUTCOMES= " + urlParams + "<br>");
		writer.println(outcomes);
        System.out.println("OUTCOMES:\n" + outcomes);
		
		//CatalogInfo
		targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getCatalogInfo.php";
		urlParams = "CUR_ID=" + courseID +"&TITLE_CODE=" + titleCode + "&SECTION_NUM=" + section + "&YEAR_TERM=" + yearTerm + "&CREDIT_TYPE=" + creditType;
        System.out.println(targetURL +"?"+ urlParams);
        String catalogInfo = excutePost(targetURL, urlParams);
		
		writer.println("<br>CAT_INFO= " + urlParams + "<br>");
		writer.println(catalogInfo);
        System.out.println("CATALOG_INFO:\n" + catalogInfo);
		
		//Syllabus
		targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getSyllabus.php";
		urlParams = "CUR_ID=" + courseID + "&TITLE_CODE=" + titleCode + "&YEAR_TERM=" + yearTerm + "&SECTION=" + section + "&DEPT=" + department + "&CAT=" + CAT;
        System.out.println(targetURL +"?"+ urlParams);
        String syllabus = excutePost(targetURL, urlParams);
		
		writer.println("<br>SYLLABUS= " + urlParams + "<br>");
		writer.println(syllabus);
        System.out.println("SYLLABUS:\n" + syllabus);
	}

}
