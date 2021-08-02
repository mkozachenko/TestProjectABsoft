package Common;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Waits extends BaseClass{

    private final Duration duration = Duration.of(explicitWaitTimeout, ChronoUnit.SECONDS);

    private Wait<WebDriver> baseFluent(WebDriver driver, Duration timeout, Duration poll){
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(poll)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(UnreachableBrowserException.class);
    }

    public void waitForClickable(By selector) {
        Duration polling = Duration.of(1, ChronoUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        Wait<WebDriver> fluentWait = baseFluent(getDriver(), duration, polling);
        fluentWait.until(ExpectedConditions.elementToBeClickable(selector));
        getDriver().manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
    }

    public void waitForStaleness(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), duration);
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public void waitForVisibility(By selector) {
        Duration polling = Duration.of(1, ChronoUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        Wait<WebDriver> fluentWait = baseFluent(getDriver(), duration, polling);
        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        getDriver().manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
    }
    public void waitForNumberOfElementsToBe(By selector, int count) {
        getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), duration);
        wait.until(ExpectedConditions.numberOfElementsToBe(selector, count));
        getDriver().manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
    }

    public void waitForVisibilityFluent(By selector) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(explicitWaitTimeout))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NullPointerException.class)
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public void sleep(long millisToWait) {
        try {
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
            System.err.println("Ожидание было прервано: \n"+e);
        }
    }
}
