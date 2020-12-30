package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.ozon.framework.managers.PageManager;

import static ru.ozon.framework.utils.UtilMethods.attachFileS;

public class ScenarioSteps {
    private PageManager app = PageManager.getPageManager();

    @Когда("Открыта стартовая страница")
    public void getStartPage() {
    app.getStartPage();
    }

    @Когда("^Выполнить поиск элемента '(.*)'$")
    public void findElement(String name) {
        app.getStartPage().findProduct(name);
    }

    @Когда("Перейти в корзину")
    public void goToBucket() {
        app.getResultSearchPage().goToBucketPage();
    }

    @Тогда("Совершить проверки на соответствие товаров в корзине")
    public void checkProduct() {
        app.getBucketPage().checkProductInBucket();
    }

    @Тогда("Очистить корзину")
    public void cleanBucket() {
        app.getBucketPage().cleanBucket();
    }

    @Тогда("Приложить файл к отчету")
    public void attachFile() {
        attachFileS();
    }

    @Тогда("^Установить фильтры по форме поле/значение:$")
    public void selectLimitPrice1(DataTable dataTable) {
        dataTable.cells().forEach(row -> {
            String nameFilter = row.get(0);
            String value = row.get(1);
            app.getResultSearchPage().selectFilter(nameFilter,value);
        });
    }

    @Когда("^Добавить \"(все|\\d+)\" (чётные|нечётные|чётных|нечётных|чётный|нечётный) товар(?:ов|а|ы) в корзину$")
    public void addProduct(String number, String condition) {
        app.getResultSearchPage().addProductInBucket(number,condition);
    }
}


