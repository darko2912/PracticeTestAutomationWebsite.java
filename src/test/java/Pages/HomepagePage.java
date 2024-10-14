package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomepagePage extends BaseTest {

    WebDriver driver; //Kreiramo ga i ovde da bismo mogli odraditi konstruktor

    WebElement practiceButton; //elementi koje koristimo na ovoj stranici

    public HomepagePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getPracticeButton() {
        return driver.findElement(By.id("menu-item-20")); //ovako podešavamo seter, pronalazimo element
    }

    //-------------------------ovde pišemo sve akcije koje primenjujemo na elemente na ovoj stranici

    public void clickOnPracticeButton(){
        getPracticeButton().click();
    }
}
