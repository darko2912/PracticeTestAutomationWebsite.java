package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseTest {

    WebDriver driver;

    WebElement usernameField;
    WebElement passwordField;
    WebElement submitButton;
    WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameField() {
        return driver.findElement(By.id("username"));
    }

    public WebElement getPasswordField() {
        return driver.findElement(By.id("password"));
    }

    public WebElement getSubmitButton() {
        return driver.findElement(By.id("submit"));
    }

    public WebElement getErrorMessage() {
        return driver.findElement(By.id("error"));
    }

    //--------------------------------------

    public void inputUsername(String username){//da možemo uvek da unosimo nove vrednosti
        getUsernameField().clear();
        getUsernameField().sendKeys(username);
    }

    public void inputPassword(String password){
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public void clickOnSubmitButton(){
        getSubmitButton().click();
    }
}
