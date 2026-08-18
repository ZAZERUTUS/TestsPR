package zazerutus.java.core;

import com.codeborne.selenide.Configuration;
import zazerutus.java.config.TestVarConfig;

public class DriverManager {

    public static void configure() {
        Configuration.browser = TestVarConfig.browser();
        Configuration.baseUrl = TestVarConfig.baseUrl();

        Configuration.headless = TestVarConfig.headless();

        Configuration.timeout = TestVarConfig.timeout();

        Configuration.browserSize = TestVarConfig.screenSize();

        Configuration.screenshots = true;
        Configuration.savePageSource = true;

        Configuration.reportsFolder = "target/selenide-reports";
    }
}
