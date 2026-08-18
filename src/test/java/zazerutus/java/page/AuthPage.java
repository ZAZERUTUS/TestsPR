package zazerutus.java.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.testng.Assert;
import zazerutus.java.core.BasePage;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class AuthPage extends BasePage {

    private final SelenideElement emailField = $(byId("auth_email"));
    private final SelenideElement passwordField = $(byId("auth_password"));
    private final SelenideElement passwordRepeatField = $(byId("auth_repeatPassword"));
    private final SelenideElement continueRegBtn = $(byXpath("//*[@data-testid='formSignInSingUpButton']"));
    private final SelenideElement checkboxPrivacy = $(byXpath("(//*[@id='auth']//*[@class='ant-checkbox-input'])[1]"));
    private final SelenideElement checkboxMarketing = $(byXpath("(//*[@id='auth']//*[@class='ant-checkbox-input'])[2]"));
    private final SelenideElement signInWithGoogle = $(byXpath("//*[@data-testid='socialAuthInitButton']"));
    private final SelenideElement passwordRulesDescription = $(byId("auth_password_extra"));
    private final SelenideElement otpHeader = $(byText("Введите проверочный код"));
    private final SelenideElement submitOtpBtn = $(byXpath("//*[@data-testid='otpFormVerifyButton']"));
    private final SelenideElement errorText = $(byXpath("//*[@class='ant-form-item-explain-error']"));

    private final String xpathInnerBtnShowValue = "..//*[@aria-label='Show password']";
    private final String xpathInnerBtnHideValue = "..//*[@aria-label='Hide password']";

    private static final String ERROR_LOGIN_TEXT = "Неверный  email или пароль";
    private static final String ERROR_EMAIL_VALIDATION_TEXT = "Неверный формат эл. почты";


    public static final String MIN_LENGTH =
            "Мин. 8 символов";
    public static final String UPPERCASE =
            "Мин. 1 заглавная буква: A - Z";
    public static final String LOWERCASE =
            "Мин. 1 строчная буква: a - z";
    public static final String DIGIT =
            "Мин. 1 цифра: 0 - 9";
    public static final String SPECIAL_CHAR =
            "Мин. 1 спец. символ: .,-^$?*+()[]{}@<>";

    public static final List<String> RULES_LIST = List.of(
            MIN_LENGTH,
            UPPERCASE,
            LOWERCASE,
            DIGIT,
            SPECIAL_CHAR
    );

    @Step("Вводит емеил: {email}")
    public AuthPage fillEmailField(String email) {
        emailField.setValue(email);
        return this;
    }

    @Step("Проверить, что кнопка 'Продолжить' {clickable}")
    public AuthPage shouldBeContinueButtonClickable(boolean clickable) {
        shouldBeClickable(continueRegBtn, clickable);
        return this;
    }

    @Step("Проверить, что кнопка 'Продолжить с гугл' {clickable}")
    public AuthPage shouldBeContinueWithGoogleButtonClickable(boolean clickable) {
        shouldBeClickable(signInWithGoogle, clickable);
        return this;
    }

    @Step("Нажать продолжить при регистрации")
    public AuthPage clickContinue() {
        continueRegBtn.click();
        return this;
    }

    @Step("Клик на чекбокс 'Пользоватльское соглашение'")
    public AuthPage clickPrivacyCheckbox() {
        checkboxPrivacy.click();
        return this;
    }

    @Step("Клик на чекбокс 'Маркетинговые акции'")
    public AuthPage clickMarketingCheckbox() {
        checkboxMarketing.click();
        return this;
    }

    @Step("Проверяет рулы пароля на странице создания пароля")
    public AuthPage checkPasswordRules() {
        RULES_LIST.forEach(txt -> passwordRulesDescription.shouldHave(text(txt)));
        return this;
    }

    @Step("Вводит пароль ...")
    public AuthPage fillPasswordField(String pass) {
        passwordField.setValue(pass);
        return this;
    }

    @Step("Проверяет что отображение пароля - {isDisplay}")
    public AuthPage checkDisplayValuePassword(boolean isDisplay) {
        return checkDisplayValuePasswordField(passwordField, isDisplay);
    }

    @Step("Клик на показать/скрыть пароль для 1 поля. Сейчас - {isDisplay}")
    public AuthPage clickShowHidePassFirstField(boolean isDisplay) {
        return clickShowHidePassField(passwordField, isDisplay);
    }

    @Step("Вводит повторный пароль")
    public AuthPage fillRepeatPasswordField(String pass) {
        passwordRepeatField.setValue(pass);
        return this;
    }

    @Step("Проверяет что отображение пароля - {isDisplay}")
    public AuthPage checkDisplayValueRepeatPassword(boolean isDisplay) {
        return checkDisplayValuePasswordField(passwordRepeatField, isDisplay);
    }

    @Step("Клик на показать/скрыть пароль для 1 поля. Сейчас - {isDisplay}")
    public AuthPage clickShowHidePassSecondField(boolean isDisplay) {
        return clickShowHidePassField(passwordRepeatField, isDisplay);
    }

    @Step("Проверить валидность правил пароля")
    public AuthPage shouldHaveValidPasswordRules(String... rules) {
        for (String rule : rules) {
            passwordRulesDescription
                    .$$("li")
                    .findBy(text(rule))
                    .shouldHave(attributeMatching("class", ".*_valid_.*"));
        }
        return this;
    }

    @Step("Проверить невалидность правил пароля")
    public AuthPage shouldHaveInvalidPasswordRules(String... rules) {
        for (String rule : rules) {
            passwordRulesDescription
                    .$$("li")
                    .findBy(text(rule))
                    .shouldHave(attributeMatching("class", ".*_invalid.*"));
        }
        return this;
    }

    @Step("Открыта форма ввода ОТП кода")
    public AuthPage checkShowOtpForm() {
        otpHeader.shouldBe(visible);
        submitOtpBtn.shouldBe(visible);
        return this;
    }

    @Step("Видно сообщение об ошибке емейла/пароля") //Шаги идентичны почти. оставил раздельными так как проверки ошибок атомарны
    public AuthPage checkErrorLogin() {
        errorText.shouldBe(visible);
        errorText.shouldHave(text(ERROR_LOGIN_TEXT));
        return this;
    }

    @Step("Видно сообщение об ошибке емейла/пароля")
    public AuthPage checkErrorEmail() {
        errorText.shouldBe(visible);
        errorText.shouldHave(text(ERROR_EMAIL_VALIDATION_TEXT));
        return this;
    }


    private AuthPage checkDisplayValuePasswordField(SelenideElement element, boolean isDisplay) {
        element.$(byXpath(isDisplay ? xpathInnerBtnHideValue : xpathInnerBtnShowValue)).shouldBe(visible);
        element.shouldHave(attribute("type", isDisplay ? "text" : "password"));
        return this;
    }

    private AuthPage clickShowHidePassField(SelenideElement element, boolean isDisplay) {
        element.$(byXpath(isDisplay ? xpathInnerBtnHideValue : xpathInnerBtnShowValue)).click();
        return this;
    }
}
