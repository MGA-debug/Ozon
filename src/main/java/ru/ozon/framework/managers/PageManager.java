package ru.ozon.framework.managers;

import ru.ozon.framework.pages.BasePage;
import ru.ozon.framework.pages.BucketPage;
import ru.ozon.framework.pages.ResultSearchPage;
import ru.ozon.framework.pages.StartPage;

import java.util.HashMap;
import java.util.Map;

import static ru.ozon.framework.pages.BucketPage.BUCKET_PAGE;
import static ru.ozon.framework.pages.ResultSearchPage.RESULT_SEARCH_PAGE;
import static ru.ozon.framework.pages.StartPage.START_PAGE;


public class PageManager {
    private static PageManager pageManager;

    private Map<String, BasePage> usagePages = new HashMap<>();

    private PageManager() {
    }

    public static PageManager getPageManager() {
        if (pageManager == null)
            pageManager = new PageManager();
        return pageManager;
    }

    public StartPage getStartPage() {
        if (!usagePages.containsKey(START_PAGE))
            usagePages.put(START_PAGE, new StartPage());
        return (StartPage) usagePages.get(START_PAGE);
    }

    public ResultSearchPage getResultSearchPage() {
        if (!usagePages.containsKey(RESULT_SEARCH_PAGE))
            usagePages.put(RESULT_SEARCH_PAGE, new ResultSearchPage());

        return (ResultSearchPage) usagePages.get(RESULT_SEARCH_PAGE);
    }

    public BucketPage getBucketPage() {
        if (!usagePages.containsKey(BUCKET_PAGE))
            usagePages.put(BUCKET_PAGE, new BucketPage());

        return (BucketPage) usagePages.get(BUCKET_PAGE);
    }

    public void zeroingPage() {
        usagePages.clear();
    }
}

