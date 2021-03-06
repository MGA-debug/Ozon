package ru.ozon.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropManager {
    private final Properties properties = new Properties();
    private static TestPropManager testPropManager = null;

    private TestPropManager() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/" +
                            System.getProperty("env", "application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TestPropManager getTestPropManager() {
        if (testPropManager == null)
            testPropManager = new TestPropManager();
        return testPropManager;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
