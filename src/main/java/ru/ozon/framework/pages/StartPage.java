package ru.ozon.framework.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends BasePage {

    public static final String START_PAGE = "startPage";

    @FindBy(xpath = "//input[@name='search']")
    WebElement searchString;


    public ResultSearchPage findProduct(String name) {
        searchString.sendKeys(name);
        searchString.sendKeys(Keys.ENTER);

        return app.getResultSearchPage();
    }
}
