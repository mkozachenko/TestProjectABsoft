package Common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.List;

public class Selectors extends BaseClass{
    private WebElement element;
    public static WebElement lastElement;
    private List<WebElement> elements;

    public WebElement selector(By selector) {
        element = null;
        try {
            element = getDriver().findElement(selector);
            lastElement = element;
        } catch (NoSuchElementException | NullPointerException noEl) {
            System.err.println("Элемент отсутствует: " + selector);
        } catch (StaleElementReferenceException stEx){
            System.err.println("Элемент не актуален (stale element reference): " + selector);
        }
        return element;
    }
    public WebElement selector(WebElement webElement, By locator){
        element = null;
        try {
            element = webElement.findElement(locator);
            lastElement = element;
        } catch (NoSuchElementException | NullPointerException noEl) {
            System.err.println("Элементы отсутствуют: \n1) "+webElement+"\n2) "+locator);
        } catch (StaleElementReferenceException stEx){
            System.err.println("Элементы не актуальны (stale element reference): \n1) "+webElement+"\n2) "+locator);
        }
        return element;
    }

    public List<WebElement> multiSelector(By selector) {
        elements = null;
        try {
            elements = getDriver().findElements(selector);
            lastElement = (elements!=null)?(!elements.isEmpty()?elements.get(0) : null):null;
        } catch (NoSuchElementException | IndexOutOfBoundsException exc) {
            System.err.println("Элементы отсутствуют: " + selector);
            System.err.println("Ошибка: \n" + exc);
        } catch (StaleElementReferenceException stEx){
            System.err.println("Элемент не актуален (stale element reference): " + selector);
        }
        return elements;
    }
    public List<WebElement> multiSelector(WebElement webElement, By locator) {
        elements = null;
        try {
            elements = webElement.findElements(locator);
            lastElement = (elements!=null)?(!elements.isEmpty()?elements.get(0) : null):null;
        } catch (NoSuchElementException | IndexOutOfBoundsException exc) {
            System.err.println("Элементы отсутствуют: \n1) "+webElement+"\n2) "+locator);
            System.err.println("Ошибка: \n" + exc);
        } catch (StaleElementReferenceException stEx){
            System.err.println("Элемент не актуален (stale element reference): \n1) "+webElement+"\n2) "+locator);
        }
        return elements;
    }
    public List<WebElement> multiSelector(By locator1, By locator2) {
        elements = null;
        try {
            elements = getDriver().findElements(new ByChained(locator1, locator2));
            lastElement = (elements!=null)?(!elements.isEmpty()?elements.get(0) : null):null;
        } catch (NoSuchElementException | IndexOutOfBoundsException exc) {
            System.err.println("Элементы отсутствуют: \n1) "+locator1+"\n2) "+locator2);
            System.err.println("Ошибка: \n" + exc);
        } catch (StaleElementReferenceException stEx){
            System.err.println("Элемент не актуален (stale element reference): \n1) "+locator1+"\n2) "+locator2);
        }
        return elements;
    }

    public WebElement selectByText(By listSelector, String text){
        element = null;
        for(WebElement item : multiSelector(listSelector)){
            if (item.getText().equalsIgnoreCase(text)) {
                element = item;
                lastElement = element;
                break;
            }
        }
        if(element == null){
            System.err.println("Элемент отсутствует:\nСелектор: " + listSelector+"\nТекст: "+text);
        }
        return element;
    }

    public WebElement selectByAttribute(By listSelector, By relativeSelector, String attrName, String attrValue){
        element = null;
        for(WebElement item : multiSelector(listSelector, relativeSelector)){
            String attrib = item.getAttribute(attrName);
            if(attrib.equalsIgnoreCase(attrValue)){
                element = item;
                lastElement = element;
                break;
            }
        }
        if(element == null){
            System.err.println("Элемент отсутствует:\nСелектор 1: " + listSelector+"\nСелектор 2: "+relativeSelector+"\nАттрибут: "+attrName+"\nЗначение: "+attrValue);
        }
        return element;
    }
}
