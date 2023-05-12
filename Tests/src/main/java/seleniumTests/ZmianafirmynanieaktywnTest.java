package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTests.util.BasePage;

import java.time.Duration;

public class ZmianafirmynanieaktywnTest extends BasePage {
  private WebDriver driver = new FirefoxDriver();
  public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


  @Before
  public void setUp() {
    WebDriverManager.edgedriver().setup();
    driver.get(baseUrl);

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    WebElement loginElement = driver.findElement(By.name("username"));


    clickElement(loginElement);
    loginElement.sendKeys("admin");


    WebElement loginButtonElement = driver.findElement(By.id("login-logIn"));
    clickElement(loginButtonElement);
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void zmianafirmynanieaktywn() throws InterruptedException {

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/companies")));

    driver.findElement(By.id("navBtn-/companies")).click();
    driver.findElement(By.id("navMenu-/companies")).click();
    Thread.sleep(1000);

    driver.findElement(By.xpath("//*[@id=\"tableRow-1\"]/td[1]")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-edit")));
    driver.findElement(By.id("formButton-edit")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-cancel")));

    driver.findElement(By.id("mui-component-select-status")).click();

    driver.findElement(By.id("formSelect-status-opt-DISABLED")).click();

    driver.findElement(By.id("formButton-save")).click();

  }
}
