package shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.How.*;

public class MyEventsPage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//h2")
    private WebElement heading;

    @FindBy(how = CLASS_NAME, using = "create-button")
    private WebElement createButton;


    public MyEventsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }

    public boolean hasAtLeastOneEvent() {
        try {
            WebElement hasNoEventsError = driver.findElement(By.xpath("//p[text()='You have not created any events yet.']"));
            return !hasNoEventsError.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }




    public void clickCreateButton() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }
}
