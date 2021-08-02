package PageObjects;

import Common.*;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import java.util.List;

public class ArticlePage extends BaseClass {
    Waits Waits = new Waits();
    Selectors Selectors = new Selectors();

    public static By articleName = By.xpath("//div[@class='article-page']/div[@class='banner']//h1");
    By tagList = By.xpath("//ul[@class='tag-list']/li");

    @Step("Check if <{tagName}> is present in list of tags in every article on the page")
    public void checkArticleTag(String tagName){
        Waits.waitForVisibilityFluent(tagList);
        boolean tagFound = false;
        String articleLink = null;
        List<WebElement> artTags = Selectors.multiSelector(tagList);
        for (WebElement artTag : artTags){
            if (artTag.getText().equals(tagName)){
                tagFound = true;
            }
        }
        if (!tagFound){
            articleLink = getDriver().getCurrentUrl();
        }
        softAssert.assertTrue(tagFound, "Tag <"+tagName+"> not found in article ["+articleLink+"]");
        softAssert.assertAll("Errors in article tags:");
    }

}
