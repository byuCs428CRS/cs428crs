package catalogData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 4/4/14
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class DepartmentDownloader {

    public static List<String> deptNames;
    public static List<String> deptCodes;
    public static List<String> HTMLdeptCodes;
    public static Map<String, String> deptMap;
    public static String DEPARTMENT_AJAX_URL = "http://saasta.byu.edu/noauth/classSchedule/ajax/getClassesByYearterm.php";

    public static void main(String[] args) {
        downloadAndParseDepartments("20155");
    }

    public static Map<String, String> getDepartmentMap(String semesterCode){
        if (deptMap == null){
            downloadAndParseDepartments(semesterCode);
        }
        return deptMap;
    }

    public static List<String> getHTMLdeptCodes(String semesterCode) {
        if (HTMLdeptCodes == null){
            downloadAndParseDepartments(semesterCode);
        }
        return HTMLdeptCodes;
    }

    public static List<String> getDeptCodes(String semesterCode){
        if (deptCodes == null){
            downloadAndParseDepartments(semesterCode);
        }
        return deptCodes;
    }

    public static List<String> getDeptNames(String semesterCode){
        if (deptNames == null){
            downloadAndParseDepartments(semesterCode);
        }
        return deptNames;
    }

    private static void downloadAndParseDepartments(String semesterCode) {
        deptMap = new LinkedHashMap<String, String>();
        deptCodes = new ArrayList<String>();
        HTMLdeptCodes = new ArrayList<String>();
        deptNames = new ArrayList<String>();

        try{
            //Setup connection
            URL url = new URL(DEPARTMENT_AJAX_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setUseCaches (true);
            conn.setDoOutput(true);
            String content = "";
            content += "YEARTERM=" + URLEncoder.encode(semesterCode, "UTF-8");

            //Post with form data (YEARTERM=semesterCode)
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(content);
            os.flush();
            os.close();

            //parse XML response
            InputStream is = conn.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);

            //Get department nodes and add their names & descriptions to data structures
            NodeList nodes = doc.getChildNodes();
            Element class_meta_data = (Element) nodes.item(0);
            Element departmentsElem = (Element)class_meta_data.getChildNodes().item(0);
            NodeList departments = departmentsElem.getElementsByTagName("department");

            for (int i = 0; i < departments.getLength(); i++){
                Element department = (Element) departments.item(i);
                String departmentCode = department.getElementsByTagName("name").item(0).getTextContent();
                String htmlDeptCode = departmentCode.replace(" ", "+");
                String departmentName = department.getElementsByTagName("desc").item(0).getTextContent();

                deptCodes.add(departmentCode);
                HTMLdeptCodes.add(htmlDeptCode);
                deptNames.add(departmentName);
                deptMap.put(htmlDeptCode, departmentName);

            }
        }
        catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

}
