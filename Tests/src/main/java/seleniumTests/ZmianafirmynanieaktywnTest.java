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

  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void zmianafirmynanieaktywn() throws InterruptedException {
    driver.get(baseUrl);

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    WebElement loginElement = driver.findElement(By.name("username"));


    clickElement(loginElement);
    loginElement.sendKeys("manager1");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\":r1:\"]")));
    WebElement passwordElement = driver.findElement(By.xpath("//*[@id=\":r1:\"]"));
    passwordElement.sendKeys(getPassword());

    WebElement loginButtonElement = driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[3]/div[1]/button"));
    clickElement(loginButtonElement);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiButtonBase-root:nth-child(2)")));

    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(1)")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiTableRow-hover:nth-child(1) > .MuiTableCell-root:nth-child(1)")));

    driver.findElement(By.cssSelector(".MuiTableRow-hover:nth-child(1) > .MuiTableCell-root:nth-child(1)")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiButton-containedPrimary")));

    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-containedPrimary"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiButton-containedPrimary")));

    driver.findElement(By.cssSelector(".MuiButton-containedPrimary")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(":r8:")));

    {
      WebElement element = driver.findElement(By.id(":r8:"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiBackdrop-root")));
    Thread.sleep(5000);

    {
      WebElement element = driver.findElement(By.cssSelector(".MuiBackdrop-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiMenuItem-root:nth-child(3)")));

    driver.findElement(By.cssSelector(".MuiMenuItem-root:nth-child(3)")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiButton-contained")));


    driver.findElement(By.cssSelector(".MuiButton-contained")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiButton-text:nth-child(1)")));

    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-text:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButton-text:nth-child(1)")).click();


  }
}
