package database;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Set;

/**
 * @author: Nick Humrich
 * @date: 2/12/14
 */
public class SeleniumDriver {

  /*
  For local tests only
   */
  public static void main(String[] args) throws InterruptedException {
    SeleniumDriver d = new SeleniumDriver();
    d.selTest();
  }

  public void selTest() throws InterruptedException {

    WebDriver driver = new HtmlUnitDriver();
//    driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
//    driver.manage().
    driver.get("https://cas.byu.edu/cas/login?service=http://andyetitcompiles.com");
    try {
      System.out.println(driver.getCurrentUrl());
      WebElement un = driver.findElement(By.name("username"));
      WebElement pass = driver.findElement(By.name("password"));
//      List<WebElement> btn = driver.findElements(By.name("_eventId"));

      un.sendKeys("username");
      pass.sendKeys("pass");
      pass.submit();
//      btn.get(0).click();

//      driver.wait();

      Set<Cookie> set = driver.manage().getCookies();
      System.out.println(driver.getCurrentUrl());
      System.out.println(driver.getPageSource());
    }
    catch (NoSuchElementException e) {
      e.printStackTrace();
    }
    int i = 0;
  }
}
