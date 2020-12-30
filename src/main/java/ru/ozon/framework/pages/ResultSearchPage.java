package ru.ozon.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static ru.ozon.framework.utils.UtilMethods.*;


public class ResultSearchPage extends BasePage {
    public static final String RESULT_SEARCH_PAGE = "resultSearchPage";

    @FindBy(xpath = "//div[contains(text(),'Цена')]/..//p")
    List<WebElement> priceElement;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']")
    WebElement widgetElement;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/..")
    WebElement brandsBlock;

    @FindBy(xpath = "//label")
    List<WebElement> filters;

    @FindBy(xpath = "//div[@data-widget='searchResultsV2']/div/div")
    List<WebElement> resultListProduct;

    @FindBy(xpath = "//a[@href='/cart']")
    WebElement bucket;

    @FindBy(xpath = "//div[normalize-space()='Корзина']")
    WebElement bucketHeader;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/..//span[contains(text(),'Посмотреть все')]")
    WebElement seeAllMaker;


    public ResultSearchPage selectFilter(String name, String value) {
        for (WebElement filter : filters) {
            String helper = name.toLowerCase();
            if (helper.contains("цена")) {
                selectPrice(name, value);
                return this;
            } else if (helper.contains("бренд")){
                selectMaker(value);
                return this;
            }
            if (selectCheckBox(filter, name, value)) return this;
        }
        Assert.fail("Опции с таким названием нет");
        return this;
    }

    public ResultSearchPage selectPrice(String condition, String value) {
        String helperValue = condition.toLowerCase();
        String priceFilter;
        String helpValue1;
        WebElement helper;

        for (WebElement element : priceElement) {
            if (helperValue.contains(element.getText())) {
                helper = element.findElement(By.xpath("./../input"));
                helper.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
                helper.sendKeys(value);
                helper.sendKeys(Keys.ENTER);
                priceFilter = element.getText()+" "+helper.getAttribute("value");
                checkWidget(priceFilter);

                widgetList.add(priceFilter);
                return this;
            }
        }
        Assert.fail("Ошибка в введенных данных");
        return this;
    }

    public boolean checkWidget(String filter) {
        int timeOut = 10;
        while (timeOut > 0) {
           if(parseText(widgetElement).contains(filter))
                return true;
            sleep(1000);
            timeOut--;
        }
        Assert.fail("Опция не выбрана");
        return false;
    }

    public ResultSearchPage addProductInBucket(String value, String sequence) {
        WebElement helper;
        int i = orderOfElements(sequence);
        int number = resultListProduct.size();  //?
        int count = 0;

        /* Проверка, что все фильтры подключены */
        if(!checkSelectedWidget()) return this;
        try {
            for (; i < resultListProduct.size(); i += 2) {
                /* Если нет кнопки добавить в корзину скипаем товар */
                if (!value.equalsIgnoreCase("все"))
                    number = Integer.parseInt(value);
                if (count == number) return this;
                helper = resultListProduct.get(i);
                if (checkVisibleElement(helper, "Express") ||
                        checkVisibleElement(helper, "Ozon Global")) continue;
                if (!checkVisibleElement(helper, "В корзину")) continue;
                addInBucket(helper);
                count++;
            }
            return this;
        } catch (NumberFormatException e) {
            Assert.fail("Введите 'все' или число");
        }
        Assert.fail("Ошибка в вводимых данных");
        return this;
    }


    private void addInBucket(WebElement helper) {
        WebElement addBucket;

        addBucket = helper.findElement(By.xpath(".//div[contains(text(),'В корзину')]"));
        scrollToElementJs(addBucket);
        addBucket.click();
        getInformationAndAddProduct(helper);
    }


    public BucketPage goToBucketPage() {
        bucket.click();
        waitUtilElementToBeVisible(bucketHeader);

        return app.getBucketPage();
    }

    private boolean selectCheckBox(WebElement element, String name, String condition) {
        WebElement helper;

        if (element.getText().contains(name)) {
            helper = element.findElement(By.xpath(".//input[@type='checkbox']/.."));
            if ((condition.equalsIgnoreCase("да") && !checkActiveCheckBox(helper)) ||
                    (condition.equalsIgnoreCase("нет") && checkActiveCheckBox(helper))) {
                enable(helper, name);
                return true;
            }
        }
        return false;
    }

    public ResultSearchPage selectMaker(String brand) {
        WebElement inputString;
        WebElement select;

        scrollToElementJs(brandsBlock);
        if (brandsBlock.getText().contains("Посмотреть все")) seeAllMaker.click();
        inputString = brandsBlock.findElement(By.xpath(".//div/div/input"));
        inputString.sendKeys(brand);
        if (!checkVisibleElement(brandsBlock,brand)) Assert.fail("Товаров бренда "+brand+" нет");
        select = brandsBlock.findElement(By.xpath(".//a//span"));
        enable(select, brand);
        return this;
    }



    /* Проверка для чекбоксов и радиокнопок на активность */
    private boolean checkActiveCheckBox(WebElement element) {
        /*Цвет включенного чекбокса */
        String activeButtonColor = "#005bff";
        String inputColor = Color.fromString(element.getCssValue("background-color")).asHex();
        return inputColor.equals(activeButtonColor);
    }

    /* Вопрос */
    private boolean checkSelectedWidget() {
        for (String widget : widgetList) {
            if (!parseText(widgetElement).contains(widget)) {
                System.out.println(parseText(widgetElement));
                for (String s: widgetList) {
                    System.out.println(s);
                }
                Assert.fail("Условие фильтрации " + widget + " не применилось");
            }

        }
        return true;
    }

    /*Включает чекбоксы и кнопки */
    public void enable(WebElement element, String name) {
        scrollToElementJs(element);
        element.click();
        if(checkWidget(name))
        widgetList.add(name);
    }
}


