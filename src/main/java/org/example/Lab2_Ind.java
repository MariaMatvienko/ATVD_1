package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Lab2_Ind {
    private WebDriver chromeDriver;

    private static final String baseUrl = "https://archiveofourown.org/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }

    @BeforeMethod
    public void preconditions(){
        chromeDriver.get(baseUrl);
    }

    @Test
    public void applyTerms(){
        WebElement checkbox = chromeDriver.findElement(By.id("tos_agree"));
        checkbox.click();
        WebElement button = chromeDriver.findElement(By.id("accept_tos"));
        button.click();
    }

    @Test
    public void isMenuListExistAndHasAllFandoms(){
        WebElement menu = chromeDriver.findElement(By.className("menu"));
        Assert.assertNotNull(menu);
        WebElement AllFandomLink = chromeDriver.findElement(By.linkText("All Fandoms"));
        AllFandomLink.click();
        Assert.assertEquals(chromeDriver.getCurrentUrl(), baseUrl + "media");
    }

    @Test
    public void testSearch() {
        WebElement search = chromeDriver.findElement(By.id("site_search"));
        Assert.assertNotNull(search);
        String inputValue = "house of dragon";
        search.click();
        search.sendKeys(inputValue);
        Assert.assertEquals(search.getAttribute("value"), inputValue);
        search.sendKeys(Keys.ENTER);
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        chromeDriver.quit();
    }

}
