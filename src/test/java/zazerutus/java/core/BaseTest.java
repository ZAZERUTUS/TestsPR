package zazerutus.java.core;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import zazerutus.java.page.AuthPage;
import zazerutus.java.page.HeaderElement;

public abstract class BaseTest {

    protected AuthPage authPage;
    protected HeaderElement headerElement;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.configure();

        authPage = new AuthPage();
        headerElement = new HeaderElement();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
