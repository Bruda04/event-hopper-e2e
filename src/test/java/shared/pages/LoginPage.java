package shared.pages;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage{
    private WebDriver driver;
    private static String PAGE_URL = "http://localhost:4200/login";

    public static String organizer3Username = "organizer3@example.com";
    public static String organizer3Password = "Password123";

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Email']")
    private WebElement emailInput;

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Password']")
    private WebElement passwordInput;

    @FindBy(how = How.ID, using = "login_btn")
    private WebElement loginButton;



    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);

    }

    public void login(String email, String password) {
        emailInput.clear();
        emailInput.sendKeys(email);

        passwordInput.clear();
        passwordInput.sendKeys(password);

        loginButton.click();
    }

    public boolean isPageOpened() {
            return (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.visibilityOf(emailInput)).isDisplayed();
    }
}
