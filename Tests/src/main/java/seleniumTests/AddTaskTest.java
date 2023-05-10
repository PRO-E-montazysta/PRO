package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class AddTaskTest {
  private WebDriver driver;
  public WebDriverWait wait;

  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new FirefoxDriver();
    WebDriverManager.firefoxdriver().setup();
    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();

    driver.get("https://dev.emontazysta.pl/login");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    WebElement loginElement = driver.findElement(By.name("username"));

    loginElement.click();
    loginElement.sendKeys("salesrepresentative1");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
    WebElement passwordElement = driver.findElement((By.id("password")));
    passwordElement.clear();
    passwordElement.sendKeys("password");


    WebElement loginButtonElement = driver.findElement(By.id("login-logIn"));
    loginButtonElement.click();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void manySymbolsAddTaskTest() throws InterruptedException {

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));

    driver.findElement(By.id("navBtn-/orders")).click();
    wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/orders/new"))));
    driver.findElement(By.id("navMenu-/orders/new")).click();

    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("fubwusdaajfmopqwjrq9ofzf[0dAK;DUFISSMDPOAJSAUOIJP3[UJPijiouj[kfxl;gjw-049itr90--233l[p;gp;kjmrf9\';tol2kw43w43qq==-0rrkgtfp;vkre9ti2-3kltefg-]oo[r450tpl;-[f3jk5r12099--kr12043tt13irfvm0-13olltr09-423j132it0203-=-9o40490490494945949905r-=5t6-0320i1owmdlkmsdkflp\\gvf\'fds../bgv/e/f./wdfvg,?LNUIKL)O(K:htgporpgki0-4lgds-0=gf4kk3-0tgk43-09f-4g904-i93o0tdvsklpfvdplk[vcxbokm[fdsvbomkdfbmoikdfbmoikdsfgvmoifdsv;llszjkmvckjk>>>???\"\"lr=-");

   driver.findElement(By.id("mui-component-select-typeOfPriority")).click();
   driver.findElement(By.id("formSelect-typeOfPriority-opt-NORMAL")).click();
    driver.findElement(By.tagName("body")).click();
    driver.findElement(By.id("mui-component-select-typeOfStatus")).click();
    driver.findElement(By.id("formSelect-typeOfStatus-opt-PLANNED")).click();
    driver.findElement(By.tagName("body")).click();

    driver.findElement(By.id("plannedStart")).click();
    driver.findElement(By.id("plannedStart")).sendKeys("25032023");
    driver.findElement(By.id("plannedStart")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedStart")).sendKeys("1200");

    driver.findElement(By.id("plannedEnd")).click();
    driver.findElement(By.id("plannedEnd")).sendKeys("14112024");
    driver.findElement(By.id("plannedEnd")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedEnd")).sendKeys("0839");

    driver.findElement(By.id("mui-component-select-clientId")).click();

    driver.findElement(By.id("formSelect-clientId-opt-1")).click();


    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.id("mui-component-select-foremanId")).click();
    driver.findElement(By.id("formSelect-foremanId-opt-6")).click();

    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.id("mui-component-select-locationId")).click();
    driver.findElement(By.id("formSelect-locationId-opt-2")).click();

    driver.findElement(By.cssSelector("body")).click();

    driver.findElement(By.id("mui-component-select-managerId")).click();
    driver.findElement(By.id("formSelect-managerId-opt-16")).click();

    driver.findElement(By.cssSelector("body")).click();

    driver.findElement(By.id("mui-component-select-specialistId")).click();
    driver.findElement(By.id("formSelect-specialistId-opt-12")).click();

    driver.findElement(By.cssSelector("body")).click();

    driver.findElement(By.id("mui-component-select-salesRepresentativeId")).click();
    driver.findElement(By.id("formSelect-salesRepresentativeId-opt-15")).click();

    driver.findElement(By.cssSelector("body")).click();

    driver.findElement(By.id("formButton-save")).click();


  }
  @Test
  public void obscureSymbolAddTaskTest() throws InterruptedException {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));

    driver.findElement(By.id("navBtn-/orders")).click();
    wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/orders/new"))));
    driver.findElement(By.id("navMenu-/orders/new")).click();

    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys(">?><>?\"{}::{{+_+_)(*^#@#^&*(~)_)(*#$%^&*()_)(*&$%%%%%%%^&&**()_???");

    driver.findElement(By.id("mui-component-select-typeOfPriority")).click();
    driver.findElement(By.id("formSelect-typeOfPriority-opt-NORMAL")).click();
    driver.findElement(By.tagName("body")).click();
    driver.findElement(By.id("mui-component-select-typeOfStatus")).click();
    driver.findElement(By.id("formSelect-typeOfStatus-opt-PLANNED")).click();
    driver.findElement(By.tagName("body")).click();

    driver.findElement(By.id("plannedStart")).click();
    driver.findElement(By.id("plannedStart")).sendKeys("25032023");
    driver.findElement(By.id("plannedStart")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedStart")).sendKeys("1200");

    driver.findElement(By.id("plannedEnd")).click();
    driver.findElement(By.id("plannedEnd")).sendKeys("14112024");
    driver.findElement(By.id("plannedEnd")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedEnd")).sendKeys("0839");

    driver.findElement(By.id("mui-component-select-clientId")).click();

    driver.findElement(By.id("formSelect-clientId-opt-1")).click();


    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.id("mui-component-select-foremanId")).click();
    driver.findElement(By.id("formSelect-foremanId-opt-6")).click();


    driver.findElement(By.id("mui-component-select-locationId")).click();
    driver.findElement(By.id("formSelect-locationId-opt-2")).click();

    driver.findElement(By.id("mui-component-select-managerId")).click();
    driver.findElement(By.id("formSelect-managerId-opt-16")).click();

    driver.findElement(By.id("mui-component-select-specialistId")).click();
    driver.findElement(By.id("formSelect-specialistId-opt-12")).click();

    driver.findElement(By.id("mui-component-select-salesRepresentativeId")).click();
    driver.findElement(By.id("formSelect-salesRepresentativeId-opt-15")).click();

    driver.findElement(By.id("formButton-save")).click();
    Thread.sleep(3000);

  }
  @Test
  public void wrongDateAddTaskTest() throws InterruptedException {

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));

    driver.findElement(By.id("navBtn-/orders")).click();
    wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/orders/new"))));
    driver.findElement(By.id("navMenu-/orders/new")).click();

    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("zlecenie ze złą datą");

    driver.findElement(By.id("mui-component-select-typeOfPriority")).click();
    driver.findElement(By.id("formSelect-typeOfPriority-opt-NORMAL")).click();
    driver.findElement(By.tagName("body")).click();
    driver.findElement(By.id("mui-component-select-typeOfStatus")).click();
    driver.findElement(By.id("formSelect-typeOfStatus-opt-PLANNED")).click();
    driver.findElement(By.tagName("body")).click();

    driver.findElement(By.id("plannedStart")).click();
    driver.findElement(By.id("plannedStart")).sendKeys("25032023");
    driver.findElement(By.id("plannedStart")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedStart")).sendKeys("1200");

    driver.findElement(By.id("plannedEnd")).click();
    driver.findElement(By.id("plannedEnd")).sendKeys("14112024");
    driver.findElement(By.id("plannedEnd")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedEnd")).sendKeys("0839");

    driver.findElement(By.id("mui-component-select-clientId")).click();

    driver.findElement(By.id("formSelect-clientId-opt-1")).click();


    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.id("mui-component-select-foremanId")).click();
    driver.findElement(By.id("formSelect-foremanId-opt-6")).click();


    driver.findElement(By.id("mui-component-select-locationId")).click();
    driver.findElement(By.id("formSelect-locationId-opt-2")).click();

    driver.findElement(By.id("mui-component-select-managerId")).click();
    driver.findElement(By.id("formSelect-managerId-opt-16")).click();

    driver.findElement(By.id("mui-component-select-specialistId")).click();
    driver.findElement(By.id("formSelect-specialistId-opt-12")).click();

    driver.findElement(By.id("mui-component-select-salesRepresentativeId")).click();
    driver.findElement(By.id("formSelect-salesRepresentativeId-opt-15")).click();

    driver.findElement(By.id("formButton-save")).click();
    Thread.sleep(3000);

  }
  @Test
  public void distantDateAddTaskTest() throws InterruptedException {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));

    driver.findElement(By.id("navBtn-/orders")).click();
    wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/orders/new"))));
    driver.findElement(By.id("navMenu-/orders/new")).click();

    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Zlecenie1");

    driver.findElement(By.id("mui-component-select-typeOfPriority")).click();
    driver.findElement(By.id("formSelect-typeOfPriority-opt-NORMAL")).click();
    driver.findElement(By.tagName("body")).click();
    driver.findElement(By.id("mui-component-select-typeOfStatus")).click();
    driver.findElement(By.id("formSelect-typeOfStatus-opt-PLANNED")).click();
    driver.findElement(By.tagName("body")).click();

    driver.findElement(By.id("plannedStart")).click();
    driver.findElement(By.id("plannedStart")).sendKeys("25032023");
    driver.findElement(By.id("plannedStart")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedStart")).sendKeys("1200");

    driver.findElement(By.id("plannedEnd")).click();
    driver.findElement(By.id("plannedEnd")).sendKeys("14112024");
    driver.findElement(By.id("plannedEnd")).sendKeys(Keys.ARROW_RIGHT);
    driver.findElement(By.id("plannedEnd")).sendKeys("0839");

    driver.findElement(By.id("mui-component-select-clientId")).click();

    driver.findElement(By.id("formSelect-clientId-opt-1")).click();


    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.id("mui-component-select-foremanId")).click();
    driver.findElement(By.id("formSelect-foremanId-opt-6")).click();


    driver.findElement(By.id("mui-component-select-locationId")).click();
    driver.findElement(By.id("formSelect-locationId-opt-2")).click();

    driver.findElement(By.id("mui-component-select-managerId")).click();
    driver.findElement(By.id("formSelect-managerId-opt-16")).click();

    driver.findElement(By.id("mui-component-select-specialistId")).click();
    driver.findElement(By.id("formSelect-specialistId-opt-12")).click();

    driver.findElement(By.id("mui-component-select-salesRepresentativeId")).click();
    driver.findElement(By.id("formSelect-salesRepresentativeId-opt-15")).click();

    driver.findElement(By.id("formButton-save")).click();
    Thread.sleep(3000);

    }
  }
