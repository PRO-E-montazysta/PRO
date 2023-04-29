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
public class ElementSeleniumTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl/login");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(":r0:"))).click();

        driver.findElement(By.id(":r0:")).sendKeys("warehouseManager1");

        driver.findElement(By.id(":r1:")).sendKeys("password");

        driver.findElement(By.cssSelector(".MuiButton-contained")).click();

    }
    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void addElementTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[6]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[6]/button")).click();

        driver.findElement(By.xpath("/html/body/div/header/div/div/div[6]/div/li[2]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("brama garażowa");



        driver.findElement(By.xpath("//*[@id=\":r3:\"]")).click();
        Thread.sleep(1000);
        Actions keyDown = new Actions(driver); keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"quantityInUnit\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"quantityInUnit\"]")).sendKeys("1");
        Thread.sleep(1000);

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/button[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

    @Test
    public void filterElementToolTestSelenium() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[6]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[6]/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[6]/div/li[1]")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[6]/div/li[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]")));
        driver.findElement(By.xpath("//*[@id=\"code\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"code\"]")).sendKeys("E|5b6c6263-9f4a-49d3-b859-741e1a201910");

//
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[3]/button[1]")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

    @Test
    public void zDeleteElementTypeTestSelenium() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[6]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[6]/button")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[6]/div/li[1]")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[6]/div/li[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("brama garażowa");
        Thread.sleep(2000);

//
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[3]/button[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/button[2]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

}
