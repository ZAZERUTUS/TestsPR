package zazerutus.java.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zazerutus.java.config.TestVarConfig;
import zazerutus.java.config.UrlVars;
import zazerutus.java.core.BaseTest;
import zazerutus.java.page.AuthPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthFlowTest extends BaseTest {
    private String pass = TestVarConfig.getPassword();
    private String email = TestVarConfig.getEmail();

    @Test(description = "Успешный флоу регистрации")
    public void happyRegistration() {

        open("");

        headerElement.clickOnRegistration();
        authPage
                .fillEmailField(email)
                .clickPrivacyCheckbox()
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .checkPasswordRules()
                .fillPasswordField(pass)
                .fillRepeatPasswordField(pass)
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .checkShowOtpForm();

        headerElement
                .checkAuthState()
                .shouldBeRedirectedTo(UrlVars.PATH_KYC);

    }

    @Test(description = "Регистрация существующего емеил")
    public void repeatRegistration() {

        open("");

        headerElement.clickOnRegistration();
        authPage
                .fillEmailField(email)
                .clickPrivacyCheckbox()
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .checkPasswordRules()
                .fillPasswordField(pass)
                .fillRepeatPasswordField(pass)
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .checkShowOtpForm();

        headerElement
                .checkAuthState()
                .shouldBeRedirectedTo(UrlVars.PATH_KYC);

    }

    @Test(description = "Авторизация с некорректным паролем")
    public void loginWithIncorrectPassword() {

        open("");

        headerElement.clickOnLogin();
        authPage
                .fillEmailField(email)
                .fillPasswordField(pass + "test")
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .checkErrorLogin();
    }

    @Test(description = "Успешный флоу авторизации", dependsOnMethods = "happyRegistration")
    public void happyLogin() {

        open("");

        headerElement.clickOnLogin();
        authPage
                .fillEmailField(email)
                .fillPasswordField(pass)
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .checkShowOtpForm();

        headerElement
                .checkAuthState()
                .clickWallet()
                .shouldBeRedirectedTo(UrlVars.PATH_WALLET);
    }

    @Test(description = "Отображение/сокрытие пароля")
    public void hideShowPassword() {

        open(UrlVars.PATH_REGISTRATION);
        authPage
                .fillEmailField(email)
                .clickPrivacyCheckbox()
                .shouldBeContinueButtonClickable(true)
                .clickContinue()
                .fillPasswordField(pass)
                .fillRepeatPasswordField(pass)
                .checkDisplayValuePassword(false)
                .clickShowHidePassFirstField(false)
                .checkDisplayValuePassword(true)
                .checkDisplayValueRepeatPassword(false)
                .clickShowHidePassSecondField(false)
                .checkDisplayValueRepeatPassword(true);

        open(UrlVars.PATH_LOGIN);
        authPage
                .fillPasswordField(pass)
                .checkDisplayValuePassword(false)
                .clickShowHidePassFirstField(false)
                .checkDisplayValuePassword(true);
    }

    @Test(description = "Проверка обязательности заполнения формы при регистрации")
    public void checkRequiredCheckbox() {

        open(UrlVars.PATH_REGISTRATION);

        authPage
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(false)
                .clickPrivacyCheckbox()
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(true)
                .clickMarketingCheckbox()
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(true)
                .clickPrivacyCheckbox()
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(false)
                .fillEmailField(email)
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(false)
                .clickPrivacyCheckbox()
                .shouldBeContinueButtonClickable(true)
                .shouldBeContinueWithGoogleButtonClickable(true)
                .clickContinue()
                .checkPasswordRules();
    }

    @Test(description = "Проверка обязательности заполнения формы при логине")
    public void checkRequiredLoginField() {

        open(UrlVars.PATH_LOGIN);

        authPage
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(true)
                .fillEmailField(email)
                .shouldBeContinueButtonClickable(false)
                .shouldBeContinueWithGoogleButtonClickable(true)
                .fillPasswordField(pass)
                .shouldBeContinueButtonClickable(true)
                .shouldBeContinueWithGoogleButtonClickable(true);

    }

    @Test(description = "Не валидность прароля", dataProvider = "passwordValidation")
    public void passwordValidationTest(
            String password,
            String[] validRules,
            String[] invalidRules
    ) {
        open("");
        headerElement.clickOnRegistration();
        authPage
                .fillEmailField("zazerutus@gmail.com")
                .clickPrivacyCheckbox()
                .clickContinue()
                .checkPasswordRules()
                .fillPasswordField(password)
                .fillRepeatPasswordField(password)
                .shouldHaveValidPasswordRules(validRules)
                .shouldHaveInvalidPasswordRules(invalidRules);
    }

    @Test(
            description = "Авторизация с некорректным email",
            dataProvider = "invalidEmails"
    )
    public void loginWithIncorrectEmail(String email, String description) {

        open(UrlVars.PATH_LOGIN);
        authPage
                .fillEmailField(email)
                .fillPasswordField(pass)
                .shouldBeContinueButtonClickable(false)
                .checkErrorEmail();
    }

    @Test(
            description = "Регистрация с некорректным email",
            dataProvider = "invalidEmails"
    )
    public void registrationWithIncorrectEmail(String email, String description) {

        open(UrlVars.PATH_REGISTRATION);

        authPage
                .fillEmailField(email)
                .shouldBeContinueButtonClickable(false)
                .clickPrivacyCheckbox()
                .checkErrorEmail();
    }

    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() { //дескрипшен просто для ясности)))
        return new Object[][]{
                {"test", "email без @"},
                {"test@", "email без домена"},
                {"@example.com", "email без local-part"},
                {"test@.com", "пустая часть домена"},
                {"test@example", "домен без TLD"},
                {"test..test@example.com", "две точки подряд"},
                {".test@example.com", "точка в начале local-part"},
                {"test.@example.com", "точка в конце local-part"},
                {"test@example,com", "запятая вместо точки"}
        };
    }

    @DataProvider(name = "passwordValidation")
    public Object[][] passwordValidationData() {
        return new Object[][]{
                {
                        "Password1!",
                        new String[]{
                                AuthPage.MIN_LENGTH,
                                AuthPage.UPPERCASE,
                                AuthPage.LOWERCASE,
                                AuthPage.DIGIT,
                                AuthPage.SPECIAL_CHAR
                        },
                        new String[]{}
                },
                {
                        "password1!",
                        new String[]{
                                AuthPage.MIN_LENGTH,
                                AuthPage.LOWERCASE,
                                AuthPage.DIGIT,
                                AuthPage.SPECIAL_CHAR
                        },
                        new String[]{
                                AuthPage.UPPERCASE
                        }
                },
                {
                        "PASSWORD1!",
                        new String[]{
                                AuthPage.MIN_LENGTH,
                                AuthPage.UPPERCASE,
                                AuthPage.DIGIT,
                                AuthPage.SPECIAL_CHAR
                        },
                        new String[]{
                                AuthPage.LOWERCASE
                        }
                },
                {
                        "Pass",
                        new String[]{
                                AuthPage.UPPERCASE,
                                AuthPage.LOWERCASE
                        },
                        new String[]{
                                AuthPage.MIN_LENGTH,
                                AuthPage.DIGIT,
                                AuthPage.SPECIAL_CHAR
                        }
                }
        };
    }
}
