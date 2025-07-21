package shared.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;

public class CreateEventPage {
    private WebDriver driver;

    @FindBy(xpath = "//h3[contains(text(),'Create Event')]")
    private WebElement heading;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement imageUploadInput;

    @FindBy(css = "input[formcontrolname='title']")
    private WebElement titleInput;

    @FindBy(css = "textarea[formcontrolname='description']")
    private WebElement descriptionInput;

    @FindBy(css = "input[formcontrolname='city']")
    private WebElement cityInput;

    @FindBy(css = "input[formcontrolname='address']")
    private WebElement addressInput;

    @FindBy(css = "input[type='file']")
    private WebElement imageInput;

    @FindBy(css = "input[formcontrolname='numParticipants']")
    private WebElement participantsInput;

    @FindBy(css = "input[formcontrolname='date']")
    private WebElement dateInput;

    @FindBy(css = "mat-select[formcontrolname='eventTypes']")
    private WebElement eventTypeDropdown;

    @FindBy(xpath = "//mat-option")
    private WebElement firstDropdownOption;

    @FindBy(css = ".next-button")
    private WebElement nextButton;

    @FindBy(css = "button.event-button")
    private WebElement finishButton;

    // Agenda step
    @FindBy(css = "input[formcontrolname='name']")
    private WebElement agendaNameInput;

    @FindBy(css = "textarea[formcontrolname='description']")
    private WebElement agendaDescriptionInput;

    @FindBy(css = "input[formcontrolname='locationName']")
    private WebElement agendaLocationInput;

    @FindBy(css = "input[formcontrolname='startTime']")
    private WebElement agendaStartTimeInput;

    @FindBy(css = "input[formcontrolname='endTime']")
    private WebElement agendaEndTimeInput;

    @FindBy(how = How.ID, using = "add_activity_btn")
    private WebElement addActivityButton;





    public CreateEventPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(heading))
                .isDisplayed();
    }

    public void fillBasicInfo(String title, String description, String city, String address,
                              String date, String participants, String imagePath) {
        titleInput.sendKeys(title);
        descriptionInput.sendKeys(description);
        cityInput.sendKeys(city);
        addressInput.sendKeys(address);
        dateInput.sendKeys(date);
        participantsInput.sendKeys(participants);
        imageInput.sendKeys(new File(imagePath).getAbsolutePath());

        eventTypeDropdown.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(firstDropdownOption)).click();
    }

    public void clickNext() {
        nextButton.click();
    }

    public void uploadImage(String imagePath) {
        File file = new File(imagePath);
        imageUploadInput.sendKeys(file.getAbsolutePath());
    }



    public void fillAgenda(String name, String description, String location, String start, String end) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(agendaNameInput));

        agendaNameInput.clear();
        agendaNameInput.sendKeys(name);

        agendaDescriptionInput.clear();
        agendaDescriptionInput.sendKeys(description);

        agendaLocationInput.clear();
        agendaLocationInput.sendKeys(location);

        agendaStartTimeInput.clear();
        setTimeInput(agendaStartTimeInput, start);

        agendaEndTimeInput.clear();
        setTimeInput(agendaEndTimeInput, end);

        addActivityButton.click();
    }

    // Helper method to set time input correctly, including AM/PM suffix if needed
    private void setTimeInput(WebElement timeInput, String time) {
        // time must be exactly "HH:mm" in 24-hour format for <input type="time">

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));",
                timeInput, time);
    }





    public void clickFinish() {
        finishButton.click();
    }
}
