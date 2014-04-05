package catalogData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

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

    private static List<String> getDepartmentStrings() {
        // Create a new instance of the html unit driver
        WebDriver driver =  new FirefoxDriver();

        //Catalog URL
        String CATALOG_URL = "http://saasta.byu.edu/noauth/classSchedule/index.php?yearTerm=20135&creditType=A";

        //Navigate to the Page
        driver.get(CATALOG_URL);

        String title = driver.getTitle();
        System.out.println(title);

        //Check the department Box
        WebElement checkbox = driver.findElement(By.id("departmentCheck"));
        checkbox.click();

        //Grab the dropdown
        WebElement deptsSelect = driver.findElement(By.id("departmentInput"));

        //Grab the dropdown options
        List<WebElement> options = driver.findElements(By.xpath("//select[@id='departmentInput']/option"));


        List<String> departments = new ArrayList<String>();
        //Grab the HTML for each option
        for(WebElement option : options){
            if(option.getText().equals("Choose . . ."))
                continue;

            departments.add(option.getText());
        }


        driver.close();
        System.out.println(departments);
        return departments;
    }

}
