package ru.ozon.framework.managers;

import java.util.concurrent.TimeUnit;

import static ru.ozon.framework.managers.DriverManager.getDriver;
import static ru.ozon.framework.managers.DriverManager.quitDriver;
import static ru.ozon.framework.managers.PageManager.getPageManager;
import static ru.ozon.framework.utils.PropertyConstant.*;

public class InitializeManager {
    public static TestPropManager props = TestPropManager.getTestPropManager();

    public static void initFramework() {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        getDriver().get(props.getProperty(APP_URL));
    }

    public static void quitFramework() {
        quitDriver();
        getPageManager().zeroingPage();
    }
}
