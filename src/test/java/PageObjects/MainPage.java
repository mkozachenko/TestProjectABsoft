package PageObjects;

import Common.*;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import java.util.List;

import static PageObjects.ArticlePage.articleName;

public class MainPage extends BaseClass {
    Waits Waits = new Waits();
    Utility Utility = new Utility();
    Selectors Selectors = new Selectors();

    public MainPage(){
        Waits.waitForClickable(tagPopularListElement);
    }

    By tagPopularListElement = By.xpath("//div[@class='tag-list']//a");
    By headerFeedElement = By.xpath("//div[@class='feed-toggle']//ul/li/a");

    By articleListBlock = By.xpath("//div[@class='article-preview']"),
        relative_articleListTag = By.xpath(".//ul[@class='tag-list']/li"),
        relative_articleListOpenLink = By.xpath(".//a[@class='preview-link']");

    @Step("Get random tag from popular tags")
    public String getRandomTagName(){
        WebElement randomTag = (WebElement) Utility.getRandomElement(Selectors.multiSelector(tagPopularListElement));
        return randomTag.getText();
    }

    @Step("Get random link from article list on page")
    public String getRandomArticleLink(){
        WebElement randomLink = (WebElement) Utility.getRandomElement(Selectors.multiSelector(articleListBlock, relative_articleListOpenLink));
        return randomLink.getAttribute("href");
    }

    @Step("Click on popular tag <{tagName}>")
    public void clickPopularTag(String tagName){
        WebElement oldArticle = Selectors.selector(articleListBlock);
        Selectors.selectByText(tagPopularListElement, tagName).click();
        Waits.waitForNumberOfElementsToBe(headerFeedElement, 2);
        Waits.waitForStaleness(oldArticle);
        Waits.sleep(2000);
        WebElement headFilter = Selectors.selectByText(headerFeedElement, tagName);
        assertion.assertEquals(headFilter.getText(), tagName, "Error in feed filter by tag");
    }

    @Step("Check if <{tagName}> is present in list of tags in every article on the page")
    public void checkArticleTag(String tagName){
        Waits.waitForVisibility(articleListBlock);
        List<WebElement> articles = Selectors.multiSelector(articleListBlock);
        for (WebElement article : articles){
            boolean tagFound = false;
            String articleLink = null;
            List<WebElement> artTags = Selectors.multiSelector(article, relative_articleListTag);
            for (WebElement artTag : artTags){
                if (artTag.getText().equals(tagName)){
                    tagFound = true;
                }
            }
            if (!tagFound){
                articleLink = Selectors.selector(article, relative_articleListOpenLink).getAttribute("href");
            }
            softAssert.assertTrue(tagFound, "Tag <"+tagName+"> not found in article ["+articleLink+"]");
        }
        softAssert.assertAll("Errors in article tags:");
    }

    @Step
    public void openArticlePage(String articleLink){
        Selectors.selectByAttribute(articleListBlock, relative_articleListOpenLink, "href", articleLink).click();
        Waits.waitForVisibility(articleName);
    }

}
