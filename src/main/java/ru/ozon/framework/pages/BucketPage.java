package ru.ozon.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ozon.framework.utils.Product;

import java.util.Collections;
import java.util.List;

import static ru.ozon.framework.utils.Product.*;
import static ru.ozon.framework.utils.UtilMethods.convertText;

public class BucketPage extends BasePage {

    public static final String BUCKET_PAGE = "bucketPage";

    @FindBy(xpath = "//div[@id='split-Main-0']/div/div/a/span")
    List<WebElement> productListInBucket;

    @FindBy(xpath = "//div[@data-widget='header']")
    WebElement bucketHeaderBlock;

    @FindBy(xpath = "//div[@delete_button_name='Удалить выбранные']")
    WebElement cleanBlock;

    @FindBy(xpath = "//div[@class='modal-content']//button")
    WebElement acceptDelete;

    @FindBy(xpath = "//h1")
    WebElement bucketHeader;

    @FindBy(xpath = "//span[contains(text(),'Общая стоимость')]/..")
    WebElement totalPriceInBucket;


    public boolean checkDisplay() {
        String count = String.valueOf(productListInBucket.size());
        return convertText(bucketHeaderBlock.getText()).contains("Корзина " + count);
    }


    public BucketPage checkProductInBucket() {
        WebElement helper;
        String tittle;
        int price = 0;

        for (WebElement element : productListInBucket) {
            tittle = element.getText();
            helper = element.findElement(By.xpath("./../../../div/div/div/span"));
            if (helper.getCssValue("font-size").contains("15"))
                price = convertValue(helper);
            Collections.reverse(productList);
            for (Product product : productList) {
                if (tittle.equals(product.getTitle())) {
                    if (price != product.getPrice()) {
                        Assert.fail("Ошибка в данных :Товар : " + tittle + price + ", цена: " + product.getPrice());
                    }
                }
            }
        }
        /*Проверка на корректное отображение количества товаров в заголовке корзины */
        if (!checkDisplay()) {
            System.out.println(productListInBucket.size());
            System.out.println(productList.size());
            Assert.fail("Отображается неверное количетсво товаров в корзине");
        }
        /*Проверка итоговой суммы в корзине */
        Assert.assertEquals("Ошибка в итоговой стоимости товаров", totalProductPrice(),
                convertValue(totalPriceInBucket));
        return this;
    }

    public BucketPage cleanBucket() {
        WebElement selectAll;
        WebElement clean = cleanBlock.findElement(By.xpath(".//span"));

        /* Проверка есть ли кнопка удалить выбранные, если нет жмем 'Выбрать все' */
        if (!checkVisibleElement(By.xpath("//div[@delete_button_name='Удалить выбранные']"))) {
            selectAll = cleanBlock.findElement(By.xpath(".//input"));
            selectAll.click();
            waitUtilElementToBeVisible(clean);
        }
        clean.click();
        waitUtilElementToBeVisible(acceptDelete);
        acceptDelete.click();

        /* Проверка, что появился заголовок в котором содержится 'Корзина пуста' */
        if (checkCleanBucket()) return this;

        Assert.fail("Корзина на очистилась");
        return this;
    }


    private boolean checkCleanBucket() {
        boolean flag = false;
        try {
            waitUtilElementToBeVisible(bucketHeader);
            flag = true;
        } catch (NoSuchElementException ignored) {
        }
        return flag;
    }
}


