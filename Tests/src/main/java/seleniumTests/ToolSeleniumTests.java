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
public class ToolSeleniumTests {
    private WebDriver driver;

    @Before
    public void setUp() {
//  options.addArguments("--remote-allow-origins=*");

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();



    }
    @After
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void addToolTestSelenium() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl");

        wait.until(ExpectedConditions.elementToBeClickable(By.id(":r0:"))).click();

        driver.findElement(By.id(":r0:")).sendKeys("warehouseManager1");

        driver.findElement(By.id(":r1:")).sendKeys("password");

        driver.findElement(By.cssSelector(".MuiButton-contained")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[2]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[2]/button")).click();

        driver.findElement(By.xpath("/html/body/div/header/div/div/div[2]/div/li[2]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("hammer");



        driver.findElement(By.xpath("//*[@id=\":r3:\"]")).click();
        Thread.sleep(1000);
        Actions keyDown = new Actions(driver); keyDown.sendKeys(Keys.chord(Keys.ENTER)).perform();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\":r4:\"]")).click();
        Thread.sleep(1000);
        keyDown.sendKeys(Keys.chord(Keys.DOWN,Keys.ENTER)).perform();
        Thread.sleep(1000);

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/button[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
//        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

    @Test
    public void filterListToolTestSelenium() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl");


        wait.until(ExpectedConditions.elementToBeClickable(By.id(":r0:"))).click();

        driver.findElement(By.id(":r0:")).sendKeys("warehouseManager1");

        driver.findElement(By.id(":r1:")).sendKeys("password");

        driver.findElement(By.cssSelector(".MuiButton-contained")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[2]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[2]/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[2]/div/li[1]")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[2]/div/li[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[3]")).click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(1000);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(2000);
//
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div")).click();
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[5]/button[1]")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

    @Test
    public void zDeleteToolTypeTestSelenium() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl");


        wait.until(ExpectedConditions.elementToBeClickable(By.id(":r0:"))).click();

        driver.findElement(By.id(":r0:")).sendKeys("warehouseManager1");

        driver.findElement(By.id(":r1:")).sendKeys("password");

        driver.findElement(By.cssSelector(".MuiButton-contained")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[2]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[2]/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[2]/div/li[1]")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[2]/div/li[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("hammer");
        Thread.sleep(2000);
//
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div")).click();
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[5]/button[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]")));
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

        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }
}
