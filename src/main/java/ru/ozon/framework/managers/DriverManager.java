package ru.ozon.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static ru.ozon.framework.utils.PropertyConstant.CHROMEDRIVER_PATH;

public class DriverManager {

    private static WebDriver driver;
    public static TestPropManager props = TestPropManager.getTestPropManager();

    private DriverManager() {

    }

    public static WebDriver getDriver() {
        if (driver == null)
            initDriver();
        return driver;
    }

    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", props.getProperty(CHROMEDRIVER_PATH));
        driver = new ChromeDriver();
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}
