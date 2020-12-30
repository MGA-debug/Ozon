package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ozon.framework.managers.InitializeManager;

import static ru.ozon.framework.managers.PageManager.getPageManager;


public class Hooks {

    @Before
    public void before() {
        InitializeManager.initFramework();
    }

    @After
    public void after() {
        InitializeManager.quitFramework();
    }
}
