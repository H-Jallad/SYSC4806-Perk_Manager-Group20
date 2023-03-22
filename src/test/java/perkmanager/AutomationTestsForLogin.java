package perkmanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationTestsForLogin {

    private WebDriver driver;
    private static SpringApplication app;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        app = new SpringApplication(PerkManagerApplication.class);
        app.run();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        driver = new RemoteWebDriver(new URL("http://localhost:5555"), capabilities);
    }
    @AfterEach
    public void tearDown(){
        driver.quit();
    }


    @Test
    public void testLogInPage() throws InterruptedException {
        driver.get("http://localhost:8080/login");
        assertEquals("Login", driver.getTitle());



        driver.findElement(By.cssSelector("a[href='/LandingPage']")).click();
        assertEquals("Perk Manager", driver.getTitle());

//
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div/button[3]")).click();
        assertEquals("Perk Manager", driver.getTitle());







    }







}
