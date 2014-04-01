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

        httpCourseDownloader.createCourseDataFile(fileName, semesterCode);
        System.out.println("<<<<<<<<<<<<<<<< DOWNLOAD COMPLETE >>>>>>>>>>>>>>>>");
        CatalogParser.parseAndUpdateDatabase(fileName);
        System.out.println("<<<<<<<<<<<<<<<< DATABASE UPDATED >>>>>>>>>>>>>>>>");
    }

}
