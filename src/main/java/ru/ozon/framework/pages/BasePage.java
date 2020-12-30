package ru.ozon.framework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ozon.framework.managers.PageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.ozon.framework.managers.DriverManager.getDriver;
import static ru.ozon.framework.managers.InitializeManager.props;
import static ru.ozon.framework.utils.PropertyConstant.IMPLICITLY_WAIT;

public class BasePage {

    /*Вопрос*/
    public static List<String> widgetList = new ArrayList<>();
    protected PageManager app = PageManager.getPageManager();

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 10, 1000);

    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    protected void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /* Можно ли в таких ситуациях игнорить эксепшен */
    protected boolean checkVisibleElement(WebElement element, String value) {
        boolean flag = false;
        getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        try {
            element.findElement(By.xpath(".//*[text()='" + value + "']"));
            flag = true;
        } catch (NoSuchElementException e) {
            flag = false;
        } finally {
            getDriver().manage()
                    .timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
        return flag;
    }

    protected boolean checkVisibleElement(By by) {
        boolean flag = false;
        getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        try {
            getDriver().findElement(by);
            flag = true;
        } catch (NoSuchElementException ignored) {

        } finally {
            getDriver().manage()
                    .timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
        return flag;
    }


    public static int convertValue(WebElement element) {
        return Integer.parseInt(element.getText().replaceAll("[^0-9,]", ""));
    }
}
