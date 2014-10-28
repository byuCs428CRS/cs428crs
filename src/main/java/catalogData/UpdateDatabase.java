package catalogData;

import org.json.JSONException;
import parser.catalog.CatalogParser;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 4/1/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateDatabase {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, JSONException {

        String fileName = "UPDATE_DATABASE.txt";
        String semesterCode = "20151"; // TODO - Find each semester code
        //yearTerm= 20141 & CreditType = A -> Winter
        //yearTerm= 20143 & CreditType = 1 -> Spring
        //yearTerm= 20143 & CreditType = 2 -> Summer
        //yearTerm= 20143 & CreditType = S -> Spring+Summer Block Class
        //yearTerm= 20145 & CreditType = A -> Fall
        //CreditTypes - A = All, S = Semester Block, 1 = Term 1, 2 = Term 2

        Scanner sc = new Scanner(System.in);
        String option = "";
        while(!(option.equals("p") || option.equals("b") || option.equals("u") || option.equals("a"))) {
            System.out.println("Please select an option - Pull data (p), build objects (b), Update database (u), or all (a)");
            option = sc.next();
        }

        if(option.equals("p") || option.equals("a")){
            httpCourseDownloader.createCourseDataFile(fileName, semesterCode);
            System.out.println("<<<<<<<<<<<<<<<< DOWNLOAD COMPLETE >>>>>>>>>>>>>>>>");
        }
        if (option.equals("u") || option.equals("a")){
            CatalogParser.parseAndUpdateDatabase(fileName, true);
            System.out.println("<<<<<<<<<<<<<<<< DATABASE UPDATED >>>>>>>>>>>>>>>>");
        }
        else if (option.equals("b")) {
            CatalogParser.parseAndUpdateDatabase(fileName, false);
            System.out.println("<<<<<<<<<<<<<<<< DATABASE NOT UPDATED >>>>>>>>>>>>>>>>");
        }
    }

}
