package zazerutus.java.core;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import zazerutus.java.config.TestVarConfig;
import zazerutus.java.page.AuthPage;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class BasePage {

    @Step("Проверка редиректа на внутренний URL: {expectedPath}")
    public BasePage shouldBeRedirectedTo(String expectedPath) {
        Selenide.webdriver().shouldHave(urlContaining(TestVarConfig.baseUrl() + expectedPath));

        return this;
    }

    protected void shouldBeClickable(SelenideElement elem, boolean clickable) {
        if (clickable) {
            elem.shouldBe(enabled);
        } else {
            elem.shouldBe(disabled);
        }
    }
}
