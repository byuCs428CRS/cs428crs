package database;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Set;

public class SeleniumMain {

	//This logs you into BYU.edu and brings you 
	public static void main(String[] args) {
		
		// Create a new instance of the html unit driver
		WebDriver driver = new HtmlUnitDriver();

				
		//driver.get("https://cas.byu.edu/cas/login?service=http://andyetitcompiles.com");
		driver.get("https://home.byu.edu/webapp/mymap/register?pageId=t124b");
		//driver.get("https://gamma.byu.edu/ry/ae/prod/person/cgi/personSummary.cgi");
		//driver.get("https://cas.byu.edu");
				
		String title = driver.getTitle();
		System.out.println(title);
		
		//Login Code
		if(title.equals("Brigham Young University Sign-in Service")){
				
			WebElement name = driver.findElement(By.id("netid"));
			WebElement password = driver.findElement(By.id("password"));
			
			name.sendKeys("user");
			password.sendKeys("pass");
			password.submit();
			
			System.out.println(driver.getTitle());
		}
		
		//Print the new URL
		System.out.println(driver.getCurrentUrl());	
		
		//driver.get("https://my.byu.edu/NotificationPortlet/scripts/success.json");
		//driver.get("https://cas.byu.edu");
		
		//Cookie Grabbing test
		Set<Cookie> set = driver.manage().getCookies();
		System.out.println(set.size());
		for (Cookie c : set) {
			System.out.println(c);
		}
		
		
	}
}
