package Common;

import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.*;
import org.testng.*;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Listener extends BaseClass implements ITestListener{

    @Attachment(value = "Скриншот", type = "image/png")
    private byte[] saveScreenshotAsFile(WebDriver driver) {
        byte[] screenBytes = null;
        try {
            File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            screenBytes = Files.readAllBytes(screen.toPath());
        } catch (Exception e) {
            //при открытом алерте скриншот можно сделать роботом, но он заскринит экран по факту, а не окно браузера
            System.err.println("Error while adding screenshot:\n"+e);
        }
        return screenBytes;
    }

    @Attachment(value = "Лог консоли браузера", type = "text/plain")
    private String webConsole(int logLevel) {
        String log = "";
        Predicate<LogEntry> filter = entry -> entry.getLevel().intValue() >= logLevel;
        LogEntries entries = getDriver().manage().logs().get(LogType.BROWSER);
        List<LogEntry> entryList = entries.getAll().stream().filter(filter).collect(Collectors.toList());
        switch (cap.getBrowserName()) {
            case "chrome":
                if (entryList.size() > 0) {
                    for(Object logEntry : entryList){
                        log = log+"\n"+logEntry.toString();
                    }
                }
                break;
        }
        return log;
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            webConsole(Level.SEVERE.intValue());
        } catch (UnhandledAlertException | NullPointerException alEx) {
            System.err.println("Error while saving test data: Save browser console\n" + alEx.getMessage());
            alEx.printStackTrace();
        }
        try {
            saveScreenshotAsFile(getDriver());
        } catch (UnhandledAlertException | NullPointerException alEx) {
            System.err.println("Error while saving test data: Save screenshot\n" + alEx.getMessage());
            alEx.printStackTrace();
        }
    }
}
