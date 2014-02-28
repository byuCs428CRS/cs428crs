import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class httpRequest {
	private static String[] DEPTS = {"A+HTG", "ACC", "AEROS", "AFRIK", "AM+ST", "ANES", "ANTHR", "ARAB", "ARTHC", "ASIAN", "ASL", "BIO", "BULGN", "BUS+M", "C+S", "CANT", "CE+EN", "CH+EN", "CHEM", "CHIN", "CL+CV", "CLSCS", "CM", "CMLIT", "CMPST", "COMD", "COMMS", "CPSE", "CSANM", "CZECH", "DANCE", "DIGHT", "DUTCH", "EC+EN", "ECE", "ECON", "EDLF", "EIME", "EL+ED", "ELANG", "EMBA", "ENG+T", "ENGL", "ESL", "EUROP", "EXSC", "FIN", "FINN", "FLANG", "FNART", "FPM", "FREN", "GEOG", "GEOL", "GERM", "GREEK", "HCOLL", "HEB", "HIST", "HLTH", "HONRS", "IAS", "ICLND", "IHUM", "INDES", "IP&T", "IR", "IS", "IT", "ITAL", "JAPAN", "KOREA", "LATIN", "LAW", "LFSCI", "LING", "LINGC", "LT+AM", "M+B+A", "M+COM", "MATH", "ME+EN", "MESA", "MFG", "MFHD", "MFT", "MIL+S", "MMBIO", "MTHED", "MUSIC", "NDFS", "NE+LG", "NES", "NEURO", "NORWE", "NURS", "ORG+B", "P+MGT", "P+POL", "PDBIO", "PETE", "PHIL", "PHSCS", "PHY+S", "PL+SC", "POLSH", "PORT", "PSYCH", "PWS", "RECM", "REL+A", "REL+C", "REL+E", "ROM", "RUSS", "SC+ED", "SCAND", "SFL", "SLAT", "SOC", "SOC+W", "SPAN", "STAC", "STAT", "STDEV", "SWED", "T+ED", "TECH", "TEE", "TELL", "TMA", "UNIV", "VA", "VAANM", "VADES", "VAEDU", "VAGD", "VAILL", "VAPHO", "VASTU", "WELSH", "WRTG", "WS"};
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
			
		 PrintWriter writer = new PrintWriter("AMGData.txt", "UTF-8");
		 
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
	}

	private static void getCourseData(PrintWriter writer) {
		String targetURL;
		String urlParams;
		//Individual Course HTTP requests here
		//Need to parse this Data
		//Outcomes, Catalog, Syllabus
		String courseID = "02859";
		String titleCode = "006";
		//Catalog, Syllabus
		String section = "052";
		String yearTerm = "20135";
		String creditType= "S";
		//Only Syllabus
		String department = "A+HTG";
		String CAT = "100";
		
		//Outcomes
		targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getOutcomes.php";
		urlParams ="CUR_ID="+ courseID + "&TITLE_CODE=" + titleCode;
		String outcomes = excutePost(targetURL, urlParams);
		
		writer.println("<br>OUTCOMES= " + urlParams + "<br>");
		writer.println(outcomes);
		
		//CatalogInfo
		targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getCatalogInfo.php";
		urlParams = "CUR_ID=" + courseID +"&TITLE_CODE=" + titleCode + "&SECTION_NUM=" + section + "&YEAR_TERM=" + yearTerm + "&CREDIT_TYPE=" + creditType;
		String catalogInfo = excutePost(targetURL, urlParams);
		
		writer.println("<br>CAT_INFO= " + urlParams + "<br>");
		writer.println(catalogInfo);
		
		//Syllabus
		targetURL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getSyllabus.php";
		urlParams = "CUR_ID=" + courseID + "&TITLE_CODE=" + titleCode + "&YEAR_TERM=" + yearTerm + "&SECTION=" + section + "&DEPT=" + department + "&CAT=" + CAT;
		String syllabus = excutePost(targetURL, urlParams);
		
		writer.println("<br>SYLLABUS= " + urlParams + "<br>");
		writer.println(syllabus);
	}

}
