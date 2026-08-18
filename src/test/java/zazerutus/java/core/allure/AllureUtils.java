package zazerutus.java.core.allure;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;

public final class AllureUtils {

    public static void attachScreenshot() {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return;
        }

        byte[] screenshot = Selenide.screenshot(OutputType.BYTES);

        Allure.addAttachment(
                "Screenshot",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }

    public static void attachPageSource() {

        if (!WebDriverRunner.hasWebDriverStarted()) {
            return;
        }

        String pageSource = WebDriverRunner
                .getWebDriver()
                .getPageSource();

        Allure.addAttachment(
                "Page source",
                "text/html",
                pageSource,
                ".html"
        );
    }
}
