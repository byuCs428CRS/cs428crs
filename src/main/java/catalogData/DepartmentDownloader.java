package catalogData;

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

    public static void main(String[] args) {

        System.out.println(getDeptNames());



    }

    public static Map<String, String> getDepartmentMap(){
        if (deptMap == null){
            populateDeptData();
        }
        return deptMap;
    }
    public static List<String> getHTMLdeptCodes() {
        if (HTMLdeptCodes == null){
            populateDeptData();
        }
        return HTMLdeptCodes;
    }


    private static void populateDeptData() {
        List<String> depts = getDepartmentStrings();
        parseDeptStrings(depts);
    }

    private static void parseDeptStrings(List<String> depts) {
        deptMap = new LinkedHashMap<String, String>();
        deptCodes = new ArrayList<String>();
        HTMLdeptCodes = new ArrayList<String>();
        deptNames = new ArrayList<String>();

        for(String s : depts){
            int splitPnt = s.indexOf(" - ") ;
            String deptCode = s.substring(0, splitPnt);
            String deptName = s.substring(splitPnt + 3);
            String htmlDeptCode = deptCode.replaceAll(" ", "+");

            deptCodes.add(deptCode);
            HTMLdeptCodes.add(htmlDeptCode);
            deptNames.add(deptName);
            deptMap.put(htmlDeptCode, deptName);
        }
    }

    public static List<String> getDeptCodes(){
        if (deptCodes == null){
            populateDeptData();
        }
        return deptCodes;
    }

    public static List<String> getDeptNames(){
        if (deptNames == null){
            populateDeptData();
        }
        return deptNames;
    }

    //This class needs to be rewritten - Find a way to get the departments list
    private static List<String> getDepartmentStrings() {
        // Create a new instance of the html unit driver
//        WebDriver driver =  new FirefoxDriver();
//
//        //Catalog URL
//        String CATALOG_URL = "http://saasta.byu.edu/noauth/classSchedule/index.php?yearTerm=20151&creditType=A";
//
//        //Navigate to the Page
//        driver.get(CATALOG_URL);
//
//        String title = driver.getTitle();
//        System.out.println(title);
//
//        //Check the department Box
//        WebElement checkbox = driver.findElement(By.id("departmentCheck"));
//        checkbox.click();
//
//        //Grab the dropdown
//        WebElement deptsSelect = driver.findElement(By.id("departmentInput"));
//
//        //Grab the dropdown options
//        List<WebElement> options = driver.findElements(By.xpath("//select[@id='departmentInput']/option"));


        String[] departmentsStrings = new String[]{"A HTG - American Heritage",
                "ACC - Accounting",
                "AEROS - Aerospace Studies",
                "AFRIK - Afrikaans",
                "AM ST - American Studies",
                "ANES - Ancient Near Eastern Studies",
                "ANTHR - Anthropology",
                "ARAB - Arabic",
                "ARTHC - Art History and Curatorial Studies",
                "ASIAN - Asian Studies",
                "ASL - American Sign Language",
                "BIO - Biology",
                "BUS M - Business Management",
                "C S - Computer Science",
                "CE EN - Civil and Environmental Engineering",
                "CFM - Construction and Facilities Management",
                "CH EN - Chemical Engineering",
                "CHEM - Chemistry and Biochemistry",
                "CHIN - Chinese - Mandarin",
                "CL CV - Classical Civilization",
                "CLSCS - Classics",
                "CMLIT - Comparative Literature",
                "CMPST - Comparative Studies",
                "COMD - Communication Disorders",
                "COMMS - Communications",
                "CPSE - Counseling Psychology and Special Education",
                "CSANM - Computer Science Animation",
                "DANCE - Dance",
                "DANSH - Danish",
                "DIGHT - Digital Humanities and Technology",
                "EC EN - Electrical and Computer Engineering",
                "ECE - Early Childhood Education",
                "ECON - Economics",
                "EDLF - Educational Leadership and Foundations",
                "EIME - Educational Inquiry, Measurement, and Evaluation",
                "EL ED - Elementary Education",
                "ELANG - English Language",
                "ENG T - Engineering Technology",
                "ENGL - English",
                "ESL - English as a Second Language",
                "EUROP - European Studies",
                "EXSC - Exercise Sciences",
                "FHSS - Family, Home, and Social Sciences",
                "FIN - Finance",
                "FINN - Finnish",
                "FLANG - Foreign Language Courses",
                "FNART - Fine Arts",
                "FREN - French",
                "GEOG - Geography",
                "GEOL - Geological Sciences",
                "GERM - German",
                "GREEK - Greek (Classical)",
                "HCOLL - Humanities College",
                "HEB - Hebrew",
                "HIST - History",
                "HLTH - Health Science",
                "HONRS - Honors Program",
                "HUNG - Hungarian",
                "IAS - International and Area Studies",
                "ICLND - Icelandic",
                "IHUM - Interdisciplinary Humanities",
                "INDES - Industrial Design",
                "IP&amp;T - Instructional Psychology and Technology",
                "IS - Information Systems",
                "IT - Information Technology",
                "ITAL - Italian",
                "JAPAN - Japanese",
                "KOREA - Korean",
                "LATIN - Latin (Classical)",
                "LAW - Law",
                "LFSCI - Life Sciences",
                "LING - Linguistics",
                "LINGC - Linguistics Computing",
                "LITHU - Lithuanian",
                "LT AM - Latin American Studies",
                "M B A - Master of Business Administration",
                "M COM - Management Communication",
                "MATH - Mathematics",
                "ME EN - Mechanical Engineering",
                "MESA - Middle East Studies/Arabic",
                "MFG - Manufacturing",
                "MFHD - Marriage, Family, and Human Development",
                "MFT - Marriage and Family Therapy",
                "MIL S - Military Science",
                "MMBIO - Microbiology and Molecular Biology",
                "MTHED - Mathematics Education",
                "MUSIC - Music",
                "NDFS - Nutrition, Dietetics, and Food Science",
                "NE LG - Near Eastern Languages",
                "NEURO - Neuroscience",
                "NORWE - Norwegian",
                "NURS - Nursing",
                "ORG B - Organizational Behavior",
                "P MGT - Public Management",
                "PDBIO - Physiology and Developmental Biology",
                "PETE - Physical Education Teacher Education",
                "PHIL - Philosophy",
                "PHSCS - Physics and Astronomy",
                "PHY S - Physical Science",
                "POLI - Political Science",
                "POLSH - Polish",
                "PORT - Portuguese",
                "PSYCH - Psychology",
                "PWS - Plant and Wildlife Sciences",
                "RECM - Recreation Management",
                "REL A - Rel A - Ancient Scripture",
                "REL C - Rel C - Church History and Doctrine",
                "REL E - Rel E - Religious Education",
                "ROM - Romanian",
                "RUSS - Russian",
                "SC ED - Secondary Education",
                "SCAND - Scandinavian Studies",
                "SFL - School of Family Life",
                "SLAT - Second Language Teaching",
                "SLN - Slovenian",
                "SOC - Sociology",
                "SOC W - Social Work",
                "SPAN - Spanish",
                "STAC - Student Activities",
                "STAT - Statistics",
                "STDEV - Student Development",
                "SWED - Swedish",
                "T ED - Teacher Education",
                "TECH - Technology",
                "TEE - Technology and Engineering Education",
                "TELL - Teaching English Language Learners",
                "TEST - Test",
                "TMA - Theatre and Media Arts",
                "UKRAI - Ukrainian",
                "UNIV - University Requirements",
                "VA - Visual Arts",
                "VAANM - Visual Arts - Animation",
                "VADES - Visual Arts - Design",
                "VAEDU - Visual Arts - Education",
                "VAGD - Visual Arts - Graphic Design",
                "VAILL - Visual Arts - Illustration",
                "VAPHO - Visual Arts - Photography",
                "VASTU - Visual Arts - Studio",
                "WELSH - Welsh",
                "WRTG - Writing",
                "WS - Womens Studies"};
        ArrayList<String> departments = new ArrayList<String>();
                 for(String dep : departmentsStrings){
                     departments.add(dep);

                 }
//        //Grab the HTML for each option
//        for(WebElement option : options){
//            if(option.getText().equals("Choose . . ."))
//                continue;
//
//            departments.add(option.getText());
//        }
//
//
//        driver.close();
        System.out.println(departments);
        return departments;
    }

}
