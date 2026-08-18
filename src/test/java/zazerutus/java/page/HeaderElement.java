package zazerutus.java.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.testng.Assert;
import zazerutus.java.core.BasePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class HeaderElement extends BasePage {

    private final SelenideElement header = $("header");
    private final SelenideElement logInBtn = $(byXpath("//*[@data-testid='headerSignInButton']"));
    private final SelenideElement registrationBtn = $(byXpath("//*[@data-testid='headerSignUpButton']"));


    private final String xpathAvatarLogo = ".//*[contains(@class, '-avatar-')]";
    private final String xpathWallet = ".//*[@data-testid='headerOpenWalletButton']";


    @Step("Клик на регистрацию")
    public HeaderElement clickOnRegistration() {
        registrationBtn.click();
        return this;
    }

    @Step("Клик на логин")
    public HeaderElement clickOnLogin() {
        logInBtn.click();
        return this;
    }

    @Step("Проерка хедера на авторизованное состояние")
    public HeaderElement checkAuthState() { //Тут собсна и ожидаем ввода ОТП кода (как введём - будет редирект сюда)

        header.$(byXpath(xpathAvatarLogo)).shouldBe(visible, Duration.ofSeconds(90));
        return this;
    }

    @Step("Проерка хедера на авторизованное состояние")
    public HeaderElement clickWallet() {
        header.$(byXpath(xpathWallet)).click();
        return this;
    }
}
