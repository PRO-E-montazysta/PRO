package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HappyPathMenagerTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl/login");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("username"))).click();

        driver.findElement(By.id("username")).sendKeys("manager1");


        driver.findElement(By.id("login-logIn")).click();

    }
    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void aViewOrdersListTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));
        driver.findElement(By.id("navBtn-/orders")).click();

        driver.findElement(By.id("navMenu-/orders")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void bViewOrderDetailsTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));
        driver.findElement(By.id("navBtn-/orders")).click();

        driver.findElement(By.id("navMenu-/orders")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void cEditOrderTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));
        driver.findElement(By.id("navBtn-/orders")).click();

        driver.findElement(By.id("navMenu-/orders")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-edit")));
        driver.findElement(By.id("formButton-edit")).click();

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Wymiana okna11");

        driver.findElement(By.id("plannedEnd")).sendKeys("23122023");
        Actions keyDown4 = new Actions(driver);
        keyDown4.sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
        driver.findElement(By.id("plannedEnd")).sendKeys("1600");
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-typeOfPriority")).click();
        Actions keyDown1 = new Actions(driver);
        keyDown1.sendKeys(Keys.chord(Keys.UP, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-specialistId")).click();
        Actions keyDown2 = new Actions(driver);
        keyDown2.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        driver.findElement(By.id("navBtn-/orders")).click();
        driver.findElement(By.id("navMenu-/orders")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Wymiana okna11");

        driver.findElement(By.id("tableFilter-submit")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void dViewFaultListTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/events")));
        driver.findElement(By.id("navBtn-/events")).click();

        driver.findElement(By.id("navMenu-/events")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void eViewFaultDetailsTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/events")));
        driver.findElement(By.id("navBtn-/events")).click();

        driver.findElement(By.id("navMenu-/events")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void fEditFaultTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/events")));
        driver.findElement(By.id("navBtn-/events")).click();

        driver.findElement(By.id("navMenu-/events")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-edit")));
        driver.findElement(By.id("formButton-edit")).click();

        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("YouWillNeverFixIt");
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-status")).click();
        Actions keyDown2 = new Actions(driver);
        keyDown2.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        driver.findElement(By.id("navBtn-/events")).click();
        driver.findElement(By.id("navMenu-/events")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("itemName")));
        driver.findElement(By.id("itemName")).click();
        driver.findElement(By.id("itemName")).clear();
        driver.findElement(By.id("itemName")).sendKeys("Test Tool 1");

        driver.findElement(By.id("tableFilter-submit")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void gViewAbsenceListTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/unavailabilities")));
        driver.findElement(By.id("navBtn-/unavailabilities")).click();

        driver.findElement(By.id("navMenu-/unavailabilities")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void hAddAbsenceTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/unavailabilities")));
        driver.findElement(By.id("navBtn-/unavailabilities")).click();
        driver.findElement(By.id("navMenu-/unavailabilities/new")).click();

        driver.findElement(By.id("mui-component-select-assignedToId")).click();
        Actions keyDown2 = new Actions(driver);
        keyDown2.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-typeOfUnavailability")).click();
        Actions keyDown3 = new Actions(driver);
        keyDown3.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("OversleptEveryDay");
        Thread.sleep(2000);

        driver.findElement(By.id("unavailableFrom")).click();
        Actions keyDown4 = new Actions(driver);
        keyDown4.sendKeys(Keys.chord(Keys.UP,Keys.ARROW_RIGHT, Keys.UP, Keys.DOWN,Keys.ARROW_RIGHT,Keys.UP, Keys.DOWN, Keys.ARROW_RIGHT)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("unavailableTo")).click();
        Actions keyDown5 = new Actions(driver);
        keyDown5.sendKeys(Keys.chord(Keys.UP, Keys.UP,Keys.ARROW_RIGHT, Keys.UP, Keys.DOWN,Keys.ARROW_RIGHT,Keys.UP, Keys.DOWN, Keys.ARROW_RIGHT)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        Thread.sleep(2000);

        driver.findElement(By.id("navBtn-/unavailabilities")).click();
        driver.findElement(By.id("navMenu-/unavailabilities")).click();


        Thread.sleep(5000);
        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);


    }

    @Test
    public void iEditAbsenceTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/unavailabilities")));
        driver.findElement(By.id("navBtn-/unavailabilities")).click();
        driver.findElement(By.id("navMenu-/unavailabilities")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-edit")));
        driver.findElement(By.id("formButton-edit")).click();

        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("I_QUIT");
        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        driver.findElement(By.id("navBtn-/unavailabilities")).click();
        driver.findElement(By.id("navMenu-/unavailabilities")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }

    @Test
    public void jDeleteAbsenceTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/unavailabilities")));
        driver.findElement(By.id("navBtn-/unavailabilities")).click();
        driver.findElement(By.id("navMenu-/unavailabilities")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-delete")));
        driver.findElement(By.id("formButton-delete")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-Usuń")));
        driver.findElement(By.id("dialogGlobal-Usuń")).click();

        driver.findElement(By.id("navBtn-/unavailabilities")).click();
        driver.findElement(By.id("navMenu-/unavailabilities")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }


}
