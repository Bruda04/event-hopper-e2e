package shared.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.util.List;

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

    @FindBy(xpath = "//mat-error[contains(text(),'This time range overlaps with an existing activity.')]")
    private WebElement overlappingTimeError;

    @FindBy(xpath = "//mat-error[contains(text(),'Title is required')]")
    private WebElement titleRequiredError;

    @FindBy(xpath = "//mat-error[contains(text(),'Field is required')]")
    private WebElement maxParticipantsRequiredError;

    @FindBy(xpath = "//mat-error[contains(text(),'Date is required.')]")
    private WebElement dateRequiredError;

    @FindBy(xpath = "//mat-error[contains(text(),'Description is required')]")
    private WebElement descriptionRequiredError;

    @FindBy(xpath = "//mat-error[contains(text(),'City is required')]")
    private WebElement cityRequiredError;

    @FindBy(xpath = "//mat-error[contains(text(),'Address is required')]")
    private WebElement addressRequiredError;

    @FindBy(xpath = "//mat-error[contains(text(),'Event type is required.')]")
    private WebElement eventTypeRequiredError;

    @FindBy(how = How.CLASS_NAME, using = "error-image-message")
    private WebElement eventImageRequiredError;


    public CreateEventPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(heading))
                .isDisplayed();
    }

    public boolean isEventTypeDropdownValid() {
        eventTypeDropdown.click();

        List<WebElement> options = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("mat-option")));

        if (options.isEmpty()) {
            return false;
        }

        String firstOptionText = options.get(0).getText().trim();
        boolean hasAllInDropdown = firstOptionText.equalsIgnoreCase("All");

        if (!hasAllInDropdown) {
            System.out.println("Warning: First dropdown option is not 'All' (was: '" + firstOptionText + "')");
        }
        WebElement neutralArea = driver.findElement(By.tagName("body"));
        neutralArea.click();

        return hasAllInDropdown;
    }


    public void fillEventInfo(String title, String description, String city, String address,
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

    public void clickFinish() {
        finishButton.click();
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
        agendaStartTimeInput.sendKeys(start);
        if(Integer.parseInt(start.split(":")[0]) < 12){
            agendaStartTimeInput.sendKeys(Keys.ARROW_UP);
        }

        agendaEndTimeInput.clear();
        agendaEndTimeInput.sendKeys(end);
        if(Integer.parseInt(end.split(":")[0]) < 12){
            agendaEndTimeInput.sendKeys(Keys.ARROW_UP);
        }

        addActivityButton.click();


    }

    public boolean isOverlappingTimeErrorDisplayed() {
        try {
            return overlappingTimeError.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean allErrorMessagesDisplayed() {
        try {
            boolean allMessagesDisplayed = true;

            allMessagesDisplayed &= titleRequiredError.isDisplayed();
            allMessagesDisplayed &= maxParticipantsRequiredError.isDisplayed();
            allMessagesDisplayed &= dateRequiredError.isDisplayed();
            allMessagesDisplayed &= descriptionRequiredError.isDisplayed();
            allMessagesDisplayed &= cityRequiredError.isDisplayed();
            allMessagesDisplayed &= addressRequiredError.isDisplayed();
            allMessagesDisplayed &= eventTypeRequiredError.isDisplayed();
            allMessagesDisplayed &= eventImageRequiredError.isDisplayed();

           return allMessagesDisplayed;
        } catch (NoSuchElementException e) {
            return false;
        }
    }



}
