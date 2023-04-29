package seleniumTests;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class TaskListTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void distantdatelisttasktest() {
    driver.get("https://dev.emontazysta.pl/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(5)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(5)")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(1)")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("zlecenie1");
    driver.findElement(By.id("createdAtMin")).click();
    driver.findElement(By.id("createdAtMin")).click();
    driver.findElement(By.id("createdAtMin")).sendKeys("1957-01-05T12:29");
    driver.findElement(By.id("createdAtMax")).click();
    driver.findElement(By.id("createdAtMax")).click();
    driver.findElement(By.id("createdAtMax")).sendKeys("2325-12-09T03:31");
    driver.findElement(By.cssSelector(".filter")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiAutocomplete-popupIndicator > .MuiSvgIcon-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiAutocomplete-popupIndicator > .MuiSvgIcon-root")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.id("typeOfStatus-option-2")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiFormControl-root:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".filter"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".filter")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButton-contained")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
  }
  @Test
  public void manysymbolslisttasktest() {
    driver.get("https://dev.emontazysta.pl/");
    driver.manage().window().setSize(new Dimension(968, 1020));
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(5)")).click();
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(1)")).click();
    driver.findElement(By.id("name")).sendKeys("fubwusdaajfmopqwjrq9ofzf[0dAK;DUFISSMDPOAJSAUOIJP3[UJPijiouj[kfxl;gjw-049itr90--233l[p;gp;kjmrf9\';tol2kw43w43qq==-0rrkgtfp;vkre9ti2-3kltefg-]oo[r450tpl;-[f3jk5r12099--kr12043tt13irfvm0-13olltr09-423j132it0203-=-9o40490490494945949905r-=5t6-0320i1owmdlkmsdkflp\\gvf\'fds../bgv/e/f./wdfvg,?LNUIKL)O(K:htgporpgki0-4lgds-0=gf4kk3-0tgk43-09f-4g904-i93o0tdvsklpfvdplk[vcxbokm[fdsvbomkdfbmoikdfbmoikdsfgvmoifdsv;llszjkmvckjk>>>???\"\"lr=-");
    driver.findElement(By.id("createdAtMin")).click();
    driver.findElement(By.id("createdAtMin")).click();
    driver.findElement(By.id("createdAtMin")).sendKeys("2022-12-14T16:46");
    driver.findElement(By.id("createdAtMax")).click();
    driver.findElement(By.id("createdAtMax")).click();
    driver.findElement(By.id("createdAtMax")).sendKeys("2023-06-14T16:47");
    driver.findElement(By.cssSelector(".MuiButton-contained")).click();
  }
  @Test
  public void obscuresymbollisttasktest() {
    driver.get("https://dev.emontazysta.pl/");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(5)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(5)")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(1)")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("asdfaca...........======+++++++!@!@#$%$%$&$&#*()@)(&$&*(?><<\"}|:{}{");
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiAutocomplete-popupIndicator > .MuiSvgIcon-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiAutocomplete-popupIndicator > .MuiSvgIcon-root")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    js.executeScript("window.scrollTo(0,0)");
    driver.findElement(By.id("typeOfStatus-option-2")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained .MuiSvgIcon-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained .MuiSvgIcon-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".css-8ncuin"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.cssSelector(".css-8ncuin")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButton-contained")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
  }
  @Test
  public void wrongdatelisttasktest() {
    driver.get("https://dev.emontazysta.pl/");
    driver.manage().window().setSize(new Dimension(968, 1020));
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(5)")).click();
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".MuiAutocomplete-popupIndicator > .MuiSvgIcon-root")).click();
    driver.findElement(By.id("typeOfStatus-option-2")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("zlecenie1");
    driver.findElement(By.id("createdAtMin")).click();
    driver.findElement(By.id("createdAtMin")).sendKeys("2023-04-30T22:45");
    driver.findElement(By.id("createdAtMax")).click();
    driver.findElement(By.id("createdAtMax")).sendKeys("2022-12-13T19:46");
    driver.findElement(By.cssSelector(".MuiFormControl-root:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiButton-contained")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
  }
}
