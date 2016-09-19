package pageClasses;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by neeraj.kadoo on 9/16/16.
 */
public class SearchPageFactory {

    WebDriver driver;

    public SearchPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Search button on homepage
    @FindBy(css = ".search")
    WebElement searchIcon;
    //Click on Search button
    public void clickSearchButton(){
    	searchIcon.click();   
    }
    
    //Search Text field
    @FindBy(name = "query")
    WebElement searchText;
    
    //Enter text in Search field
    public void enterSearchText(String text){
    	searchText.click();
		searchText.sendKeys(text);
    }
    
    //CLick on Search icon after entering search text
    @FindBy(css=".submit")
    WebElement submitSearch;
    //Click on the Search icon
    public void submitSearch(){
    	submitSearch.click();
    }
    
    //Look for search results
    @FindBy(css="div.results")
    WebElement searchResults;
    
    //Get Search results
    public WebElement getResults(){
		return searchResults;
    	
    }
    
}
