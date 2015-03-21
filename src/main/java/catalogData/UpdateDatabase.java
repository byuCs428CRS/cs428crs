package catalogData;

import models.Semester;

import org.json.JSONException;

import database.DatabaseRegistrationStore;
import database.SemesterDAO;
import parser.catalog.CatalogParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 4/1/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateDatabase {
    public static void main(String[] arags) throws FileNotFoundException, UnsupportedEncodingException, JSONException {

        //String fileName = "UPDATE_DATABASE.txt";
    	//USES DYNAMIC! :)
        //String semesterCode = "20151"; // TODO - Find each semester code
        //yearTerm= 20141 & CreditType = A -> Winter
        //yearTerm= 20143 & CreditType = 1 -> Spring
        //yearTerm= 20143 & CreditType = 2 -> Summer
        //yearTerm= 20143 & CreditType = S -> Spring+Summer Block Class
        //yearTerm= 20145 & CreditType = A -> Fall
        //CreditTypes - A = All, S = Semester Block, 1 = Term 1, 2 = Term 2

        Scanner sc = new Scanner(System.in);
        String option = "";
        while(!(option.equals("p") || option.equals("a") || option.equals("u"))) {
            System.out.println("Please select an option - Pull data (p), Update ALL current years (a), OR Update only most recent semester (u)");
            option = sc.next();
        }
        sc.close();

        if(option.equals("p")){
            System.out.println("Use the main class in the Catalog Parser...");
        }
        if (option.equals("a")){
            List<String> semesterCodes = SemesterDownloader.getSemesterCodes();
            SemesterDAO sdao = new SemesterDAO(DatabaseRegistrationStore.getDB());
        	for(int i=0;i<semesterCodes.size();i++){
        		if(Calendar.getInstance().get(Calendar.YEAR)<=SemesterDownloader.getYear(semesterCodes.get(i))){
	        		Semester semester = new Semester();
	        		semester.setName(SemesterDownloader.getSemesterName(semesterCodes.get(i)));
	        		semester.setID(Integer.parseInt(semesterCodes.get(i)));
	        		try {
						semester.setCourses(CatalogParser.parseCourses(httpCourseDownloader.downloadCourses(semesterCodes.get(i))));
					} catch (IOException e) {
						System.out.println("failed to download and update database for " + semester.getName());
					}
	        		sdao.saveSemester(semester);
        		}
        		else
        		{
        			System.out.println("skipping old year " + SemesterDownloader.getYear(semesterCodes.get(i)));
        		}
        	}
            System.out.println("<<<<<<<<<<<<<<<< DATABASE UPDATED >>>>>>>>>>>>>>>>");
        }
        if (option.equals("u")){
            List<String> semesterCodes = SemesterDownloader.getSemesterCodes();
            SemesterDAO sdao = new SemesterDAO(DatabaseRegistrationStore.getDB());
	        String semesterName = SemesterDownloader.getSemesterName(semesterCodes.get(semesterCodes.size()-1));
	        Semester semester = new Semester();
    		semester.setName(semesterName);
    		semester.setID(Integer.parseInt(semesterCodes.get(semesterCodes.size()-1)));
    		sdao.deleteSemester(semester);
    		try {
				semester.setCourses(CatalogParser.parseCourses(httpCourseDownloader.downloadCourses(semesterCodes.get(semesterCodes.size()-1))));
			} catch (IOException e) {
				System.out.println("failed to download and update database for " + semesterName);
			}
    		sdao.saveSemester(semester);
            System.out.println("<<<<<<<<<<<<<<<< DATABASE UPDATED >>>>>>>>>>>>>>>>");
        }
    }

}
