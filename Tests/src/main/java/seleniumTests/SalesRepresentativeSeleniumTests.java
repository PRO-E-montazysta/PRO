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

public class SalesRepresentativeSeleniumTests {

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

        driver.findElement(By.id("username")).sendKeys("salesrepresentative1");


        driver.findElement(By.id("login-logIn")).click();

    }
    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void viewOrdersListTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));
        driver.findElement(By.id("navBtn-/orders")).click();

        driver.findElement(By.id("navMenu-/orders")).click();

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);
    }


    @Test
    public void addClientTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-clients/")));
        driver.findElement(By.id("navBtn-clients/")).click();

        driver.findElement(By.id("navMenu-/clients/new")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("PayingClient234");
        Thread.sleep(2000);

        driver.findElement(By.id("contactDetails")).click();
        driver.findElement(By.id("contactDetails")).sendKeys("+48511324567");
        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        Thread.sleep(2000);

        driver.findElement(By.id("navBtn-clients/")).click();
        driver.findElement(By.id("navMenu-clients")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("PayingClient234");
        driver.findElement(By.id("tableFilter-submit")).click();

        Thread.sleep(5000);
        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);


    }

    @Test
    public void editClientTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-clients/")));
        driver.findElement(By.id("navBtn-clients/")).click();

        driver.findElement(By.id("navMenu-clients")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("PayingClient234");
        driver.findElement(By.id("tableFilter-submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr/td[1]")));
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr/td[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-edit")));
        driver.findElement(By.id("formButton-edit")).click();

        driver.findElement(By.id("contactDetails")).click();
        driver.findElement(By.id("contactDetails")).clear();
        Thread.sleep(2000);
        driver.findElement(By.id("contactDetails")).sendKeys("notpayinganymore@payme.com");

        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        driver.findElement(By.id("navBtn-clients/")).click();
        driver.findElement(By.id("navMenu-clients")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("PayingClient234");

        Thread.sleep(5000);
        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);


    }

    @Test
    public void addOrderTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));
        driver.findElement(By.id("navBtn-/orders")).click();

        driver.findElement(By.id("navMenu-/orders/new")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("Wymiana okna");
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-clientId")).click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-typeOfPriority")).click();
        Actions keyDown2 = new Actions(driver);
        keyDown2.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-typeOfStatus")).click();
        Actions keyDown3 = new Actions(driver);
        keyDown3.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("plannedStart")).sendKeys("22122023");
        Actions keyDown4 = new Actions(driver);
        keyDown4.sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
        driver.findElement(By.id("plannedStart")).sendKeys("0800");
        Thread.sleep(2000);

        driver.findElement(By.id("plannedEnd")).sendKeys("22122023");
        Actions keyDown5 = new Actions(driver);
        keyDown5.sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
        driver.findElement(By.id("plannedEnd")).sendKeys("1600");
        Thread.sleep(2000);

        driver.findElement(By.id("mui-component-select-locationId")).click();
        Actions keyDown1 = new Actions(driver);
        keyDown1.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
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
        driver.findElement(By.id("name")).sendKeys("Wymiana okna");

        Thread.sleep(5000);
        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);


    }

    @Test
    public void editOrderTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/orders")));
        driver.findElement(By.id("navBtn-/orders")).click();

        driver.findElement(By.id("navMenu-/orders")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        driver.findElement(By.id("typeOfStatus")).click();
        driver.findElement(By.id("typeOfStatus")).clear();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.BACK_SPACE)).perform();
        driver.findElement(By.id("typeOfStatus")).click();
        Actions keyDown2 = new Actions(driver);
        keyDown2.sendKeys(Keys.chord(Keys.BACK_SPACE)).perform();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Test Order 1 - from Client 1");
        Thread.sleep(2000);

        driver.findElement(By.id("tableFilter-submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr/td[1]")));
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr/td[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-edit")));
        driver.findElement(By.id("formButton-edit")).click();

        driver.findElement(By.id("mui-component-select-typeOfStatus")).click();
        Actions keyDown1 = new Actions(driver);
        keyDown1.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);

        driver.findElement(By.id("formButton-save")).click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();



        driver.findElement(By.id("navBtn-/orders")).click();
        driver.findElement(By.id("navMenu-/orders")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("typeOfStatus")).click();
        driver.findElement(By.id("typeOfStatus")).clear();
        Actions keyDown3 = new Actions(driver);
        keyDown3.sendKeys(Keys.chord(Keys.BACK_SPACE)).perform();
        driver.findElement(By.id("typeOfStatus")).click();
        Actions keyDown4 = new Actions(driver);
        keyDown4.sendKeys(Keys.chord(Keys.BACK_SPACE)).perform();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Test Order 1 - from Client 1");
        Thread.sleep(2000);
        driver.findElement(By.id("tableFilter-submit")).click();

        Thread.sleep(5000);
        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);


    }


}
