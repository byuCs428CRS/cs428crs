package catalogData;

import parser.catalog.CatalogParser;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 4/1/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateDatabase {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        String fileName = "UPDATE_DATABASE.txt";
        String semesterCode = "20145"; // TODO - Find each semester code
        //yearTerm= 20141 & CreditType = A -> Winter
        //yearTerm= 20143 & CreditType = 1 -> Spring
        //yearTerm= 20143 & CreditType = 2 -> Summer
        //yearTerm= 20143 & CreditType = S -> Spring+Summer Block Class
        //yearTerm= 20145 & CreditType = A -> Fall
        //CreditTypes - A = All, S = Semester Block, 1 = Term 1, 2 = Term 2

        //A - All offering
       // httpCourseDownloader.createCourseDataFile(fileName, semesterCode);
        System.out.println("<<<<<<<<<<<<<<<< DOWNLOAD COMPLETE >>>>>>>>>>>>>>>>");
        CatalogParser.parseAndUpdateDatabase(fileName);
        System.out.println("<<<<<<<<<<<<<<<< DATABASE UPDATED >>>>>>>>>>>>>>>>");
    }

}
