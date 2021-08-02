package Tests;

import Common.BaseClass;
import PageObjects.ArticlePage;
import PageObjects.MainPage;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class TagsSuite extends BaseClass {

    @Test
    @Description("Assert that articles in tag filter contain the tag")
    public void checkTagFilteringArticlesList(){
        MainPage mainPage = new MainPage();
        String tagName = mainPage.getRandomTagName();
        mainPage.clickPopularTag(tagName);
        mainPage.checkArticleTag(tagName);
    }

    @Test
    @Description("Assert that selected article page contains selected tag")
    public void checkTagFilteringArticlePage(){
        MainPage mainPage = new MainPage();
        ArticlePage article = new ArticlePage();
        String tagName = mainPage.getRandomTagName();
        mainPage.clickPopularTag(tagName);
        String articleLink = mainPage.getRandomArticleLink();
        mainPage.openArticlePage(articleLink);
        article.checkArticleTag(tagName);
    }

}
