package cashwise.base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BasePage is the base class for all page classes in the application.
 * It contains common methods and properties used across different pages.
 */

public abstract class BasePage {

    protected WebDriver driver;
    protected final Logger logger = LogManager.getLogger(this.getClass());
    private final int DEFAULT_WAIT_TIME = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, DEFAULT_WAIT_TIME);
    }

    protected WebElement waitForVisibility(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not visible after " + timeoutInSeconds + " seconds: " + locator);
            throw e;
        }
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        return waitForElementToBeClickable(locator, DEFAULT_WAIT_TIME);
    }

    protected WebElement waitForElementToBeClickable(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable after " + timeoutInSeconds + " seconds: " + locator);
            throw e;
        }
    }

    protected WebElement waitForElementPresence(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not present after " + timeoutInSeconds + " seconds: " + locator);
            throw e;
        }
    }

    protected void click(By locator) {
        int attempts = 0;
        int maxRetries = 3;
        while (attempts < maxRetries) {
            try {
                WebElement element = waitForElementToBeClickable(locator);
                element.click();
                logger.info("Clicked on element: " + locator);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                logger.warn("Attempt " + attempts + " to click element failed. Retrying...");
                sleep(500);
            } catch (Exception e) {
                logger.error("Failed to click on element: " + locator, e);
                throw e;
            }
        }
        throw new RuntimeException("Failed to click on element after " + maxRetries + " attempts: " + locator);
    }

    protected void enterText(By locator, String text) {
        try {
            WebElement element = waitForVisibility(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '" + text + "' into element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to enter text into element: " + locator, e);
            throw e;
        }
    }

    protected String getText(By locator) {
        try {
            WebElement element = waitForVisibility(locator);
            String text = element.getText();
            logger.info("Retrieved text '" + text + "' from element: " + locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator, e);
            throw e;
        }
    }

    protected void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: " + locator, e);
            throw e;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean isDisplayed = element.isDisplayed();
            logger.info("Element displayed status for " + locator + ": " + isDisplayed);
            return isDisplayed;
        } catch (NoSuchElementException e) {
            logger.warn("Element not found: " + locator);
            return false;
        }
    }

    protected void waitForPageToLoad() {
        waitForPageToLoad(DEFAULT_WAIT_TIME);
    }

    protected void waitForPageToLoad(int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely");
    }

    protected void selectByVisibleText(By locator, String text) {
        try {
            WebElement dropdownElement = waitForVisibility(locator);
            Select select = new Select(dropdownElement);
            select.selectByVisibleText(text);
            logger.info("Selected option '" + text + "' from dropdown: " + locator);
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown: " + locator, e);
            throw e;
        }
    }

    protected void selectByValue(By locator, String value) {
        try {
            WebElement dropdownElement = waitForVisibility(locator);
            Select select = new Select(dropdownElement);
            select.selectByValue(value);
            logger.info("Selected option with value '" + value + "' from dropdown: " + locator);
        } catch (Exception e) {
            logger.error("Failed to select option by value from dropdown: " + locator, e);
            throw e;
        }
    }

    protected void acceptAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            logger.info("Alert accepted");
        } catch (TimeoutException e) {
            logger.warn("No alert present to accept");
        } catch (Exception e) {
            logger.error("Failed to accept alert", e);
            throw e;
        }
    }

    protected void dismissAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            logger.info("Alert dismissed");
        } catch (TimeoutException e) {
            logger.warn("No alert present to dismiss");
        } catch (Exception e) {
            logger.error("Failed to dismiss alert", e);
            throw e;
        }
    }

    protected String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            logger.info("Retrieved alert text: " + alertText);
            return alertText;
        } catch (NoAlertPresentException e) {
            logger.warn("No alert present to get text from");
            return null;
        } catch (Exception e) {
            logger.error("Failed to get alert text", e);
            throw e;
        }
    }

    protected void switchToFrame(By locator) {
        try {
            WebElement frameElement = waitForVisibility(locator);
            driver.switchTo().frame(frameElement);
            logger.info("Switched to frame: " + locator);
        } catch (Exception e) {
            logger.error("Failed to switch to frame: " + locator, e);
            throw e;
        }
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.info("Switched back to default content");
    }

    protected void waitForTextToBePresentInElement(By locator, String text, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            logger.info("Text '" + text + "' is present in element: " + locator);
        } catch (TimeoutException e) {
            logger.error("Text '" + text + "' not present in element after " + timeoutInSeconds + " seconds: " + locator);
            throw e;
        }
    }

    protected boolean waitForElementToBeInvisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            logger.info("Element invisibility status for " + locator + ": " + isInvisible);
            return isInvisible;
        } catch (Exception e) {
            logger.error("Failed to wait for element to be invisible: " + locator, e);
            throw e;
        }
    }

    protected void waitForTitleToContain(String titleSubstring, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.titleContains(titleSubstring));
            logger.info("Page title contains: " + titleSubstring);
        } catch (TimeoutException e) {
            logger.error("Page title did not contain '" + titleSubstring + "' after " + timeoutInSeconds + " seconds");
            throw e;
        }
    }

    protected void waitForAttributeToContain(By locator, String attribute, String value, int timeoutInSeconds) {
        try {
            WebElement element = waitForVisibility(locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.attributeContains(element, attribute, value));
            logger.info("Attribute '" + attribute + "' of element " + locator + " contains value: " + value);
        } catch (TimeoutException e) {
            logger.error("Attribute '" + attribute + "' of element " + locator + " did not contain '" + value + "' after " + timeoutInSeconds + " seconds");
            throw e;
        }
    }

    protected void clickByJS(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("Clicked on element using JavaScript: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click on element using JavaScript: " + locator, e);
            throw e;
        }
    }

    protected void switchToWindowByTitle(String windowTitle) {
        String originalWindow = driver.getWindowHandle();
        boolean windowFound = false;

        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(windowTitle)) {
                windowFound = true;
                logger.info("Switched to window with title: " + windowTitle);
                break;
            }
        }

        if (!windowFound) {
            driver.switchTo().window(originalWindow);
            logger.error("Window with title '" + windowTitle + "' not found");
            throw new NoSuchWindowException("Window with title '" + windowTitle + "' not found");
        }
    }

    protected void uploadFile(By locator, String filePath) {
        try {
            WebElement uploadElement = waitForVisibility(locator);
            uploadElement.sendKeys(filePath);
            logger.info("Uploaded file: " + filePath + " to element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to upload file to element: " + locator, e);
            throw e;
        }
    }

    protected void takeScreenshot(String filePath) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(filePath));
            logger.info("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to take a screenshot", e);
            throw new RuntimeException("Failed to take a screenshot", e);
        }
    }

    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread sleep was interrupted", e);
            throw new RuntimeException("Thread sleep was interrupted", e);
        }
    }
}