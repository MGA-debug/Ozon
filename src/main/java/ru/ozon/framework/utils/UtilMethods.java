package ru.ozon.framework.utils;

import io.qameta.allure.Allure;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

import static ru.ozon.framework.pages.BasePage.convertValue;
import static ru.ozon.framework.pages.BasePage.widgetList;
import static ru.ozon.framework.utils.Product.*;


public class UtilMethods {

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String convertText(String text) {
        return text.replaceAll("\\s+"," ");
    }

    public static void attachFileS() {
        String text = "";
        text += String.format("%-90s%-11s%n","Товар","Цена");
        text +="-------------------------------------------------------------------------------------------------------"
                +"\n";
        for (Product product: productList)
            text += String.format("%-90s%-11s%n",product.getTitle(),product.getPrice());

        text +="-------------------------------------------------------------------------------------------------------"
                +"\n";
        productList.sort((o1, o2) -> o1.getPrice() - o2.getPrice());
        Collections.reverse(productList);
        text += String.format("Самый дорогой товар: "+"%-69s%-11s%n",productList.get(0).getTitle(),productList
                .get(0).getPrice());
        Allure.addAttachment("Вложение","text/plain",text);
        /*Очищаем список с фильтрами и список с товарами */
        clean(productList);
        clean(widgetList);
    }

    public static void getInformationAndAddProduct(WebElement element) {
        WebElement helper;
        String productTittle;
        int productPrice;
        helper = element.findElement(By.xpath(".//a/div/span[1]"));
        productPrice = convertValue(helper);
        productTittle = element.findElement(By.xpath("./div/div/div/div/a")).getText();

        addListProduct(productTittle, productPrice);
    }

    public static int orderOfElements(String order) {
        int i = 0;
        if (order.toLowerCase().contains("чётн"))
            i = 1;
        else if (!order.toLowerCase().contains("нечёт"))
            Assert.fail("Введите чётны(е|х)/нечётны(е|х)");
        return i;
    }

    public static String parseText(WebElement element) {
        return element.getText().replaceAll(" ","");
    }

    public static void clean(List<?> list) {
        list.clear();
    }
}



