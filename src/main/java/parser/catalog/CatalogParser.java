package parser.catalog;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import catalogData.httpCourseDownloader;
import models.*;
import packages.Courses;

public class CatalogParser {
	
	//Flip flag for more printouts to help debug parser
	final private static boolean SHOULD_LOG = false;
	
	public static void main(String[] args)
	{
		try {
			System.out.println(parseCourses(httpCourseDownloader.downloadCourses("20155")).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	// Takes File object to were the UPDATE_DATABASE.txt file
	public static Courses parseCourses(File file) throws FileNotFoundException, IOException
	{
		return parseCourses(new FileInputStream(file));
	}
	
	// Takes string form of the input
	public static Courses parseCourses(String info) throws IOException
	{
		return parseCourses(new ByteArrayInputStream(info.getBytes()));
	}
	
	// Takes Generic InputStream
	@SuppressWarnings("resource")
	public static Courses parseCourses(InputStream stream) throws IOException
	{
		Scanner scan = new Scanner(new BufferedInputStream(stream)).useDelimiter("#");
		String tmp;
		int currentIndex = 0;
		Section currentSection = new Section();
		Course currentCourse = new Course();
		Courses allCourses = new Courses();
		boolean firstTime = true;
		while(scan.hasNext())
		{
			tmp = scan.next().trim();
			// These three if statements handle edge cases for t
			if(tmp.isEmpty())
			{
				//This check is for when there are no notes/outcomes, professor, or course Title Listed
				if(currentIndex == 14 || currentIndex == 8 || currentIndex == 7)
					currentIndex++;
				continue;
			}
			
			// These show up I After section number L means Lab section, N i think is study abroad
			if(currentIndex == 6 && (tmp.equals("L") || tmp.equals("N")))
				continue; 
				
			// This takes care of adding the R or other Letters to the end of course numbers
			// Like 498R
			if(currentIndex == 5 && tmp.length() == 1)
			{
				currentCourse.setCourseNumber(currentCourse.getCourseNumber() + tmp);
				continue;
			}
				
				
			switch (currentIndex)
			{
				case 0:
					simpleLog("Course Number Unique: " + tmp, SHOULD_LOG);
					if(!tmp.equalsIgnoreCase(currentCourse.getCourseID()))
					{
						if(!firstTime)
						{
							allCourses.addCourse(currentCourse);
							currentCourse = new Course();
							currentCourse.setCourseID(tmp);
							currentSection.setCourseID(tmp);
						}
						else
						{
							firstTime = false;
							currentCourse.setCourseID(tmp);
							currentSection.setCourseID(tmp);
						}
							
					}
					break;
				case 1:
					simpleLog("Title Code: " + tmp, SHOULD_LOG);
					currentCourse.setNewTitleCode(tmp);
					break;
				case 2:
					simpleLog("Dept Code: " + tmp, SHOULD_LOG);
					currentCourse.setDepartmentCode(tmp);
					break;
				case 3:
					simpleLog("Credit Type?: " + tmp, SHOULD_LOG);
					currentCourse.setRegistrationType(tmp);
					break;
				case 4:
					simpleLog("Course Number: " + tmp, SHOULD_LOG);
					currentCourse.setCourseNumber(tmp);
					break;
				case 5:
					simpleLog("Section Number: " + tmp, SHOULD_LOG);
					currentSection.setSectionID(tmp);
					break;
				case 6:
					simpleLog("DAY or NIGHT: " + tmp, SHOULD_LOG);
					currentSection.setSectionType(tmp);
					break;
				case 7:
					simpleLog("Course Title: " + tmp, SHOULD_LOG);
					currentCourse.setCourseName(tmp);	
					break;
				case 8:
					simpleLog("Prof Name: " + tmp, SHOULD_LOG);
					currentSection.setProfessor(tmp);
					break;
				case 9:
					simpleLog("Number of Credits: " + tmp, SHOULD_LOG);
					currentSection.setCredits(tmp);
                    currentCourse.setCredit(tmp); //add this so course can has it's own credit hours as well
					break;
				case 10:
					simpleLog("Days Taught: " + tmp, SHOULD_LOG);
					currentSection.setDaysTaught(tmp.split("\n"));
					break;
				case 11:
					simpleLog("Start Time/s: " + tmp,SHOULD_LOG);
					currentSection.setStartTimes(tmp.split("\n"));
					break;
				case 12:
					simpleLog("End Time/s: " + tmp, SHOULD_LOG);
					currentSection.setEndTimes(tmp.split("\n"));
					break;
				case 13:
					simpleLog("Location/s: " + tmp, SHOULD_LOG);
					currentSection.setLocations(tmp.split("\n"));
					break;
				case 14:
					simpleLog("Notes: " + tmp, SHOULD_LOG);
					//currentCourse.setOutcomes(Arrays.asList(tmp.split("\n")));
					break;
				case 15:
					simpleLog("How full is the class: " + tmp, SHOULD_LOG);
					if(tmp.equalsIgnoreCase("N/A")){
						currentSection.setSeatsAvailable("0");
						currentSection.setTotalSeats("0");
						currentSection.setWaitList("0");
						currentIndex++;
					}else{
						String[] seats = tmp.split("\\s*/\\s* ");
						currentSection.setSeatsAvailable(seats[0]);
						currentSection.setTotalSeats(seats[1]);
					}
					break;
				case 16:
					simpleLog("Weight List: " + tmp, SHOULD_LOG);
					currentSection.setWaitList(tmp);
					break;
				default:
					System.err.println("SHOULDN'T BE HERE");
			}
			currentIndex++;		
			if(currentIndex > 16)
			{
				currentIndex = 0;
				currentCourse.addSection(currentSection);
				currentSection = new Section();
				
			}
		}
		
		scan.close();
		stream.close();
		return allCourses;
	}
	
	private static void simpleLog(String log, boolean shouldLog)
	{
		if(shouldLog)
		{
			System.out.println(log);
		}
	}

}