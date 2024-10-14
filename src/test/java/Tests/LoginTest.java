package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Pages.HomepagePage;
import Pages.LoginPage;
import Pages.PracticePage;
import Pages.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class LoginTest extends BaseTest {//obavezno nasleđuje Base zbog podešavanja koje smo tamo kreirali
//ovde pravimo BeforeMethod i Test, ne kreiramo driver jer ga ne koristimo
    @BeforeMethod
    public void pageSetUp() throws IOException {

        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.navigate().to("https://practicetestautomation.com/");

        homepagePage = new HomepagePage(driver);
        practicePage = new PracticePage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        excelReader = new ExcelReader("TestData1.xlsx");
    }

    @Test //koristimo metode koje smo kreirali u Pages klasama i assert-ujemo
    public void userCanLogIn(){
        String username="student";
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername(username);
        loginPage.inputPassword("Password123");
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(profilePage.getLogOutButton().isDisplayed());//ovde proveravamo da li se element vidi
        Assert.assertTrue(profilePage.getMessage().isDisplayed());
        Assert.assertEquals(profilePage.getMessage().getText(), "Congratulations "+username+". You successfully logged in!");
    }

    @Test
    public void userCannotLogInWithInvalidUsername(){
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername("non student");
        loginPage.inputPassword("Password123");
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your username is invalid!");
    }

    @Test
    public void userCannotLogInWithInvalidPassword(){
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername("student");
        loginPage.inputPassword("123456");
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your password is invalid!");
    }

    @Test
    public void userCannotLogInWithEmptyFields(){
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername("");
        loginPage.inputPassword("");
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
    }

    @Test
    public void userCannotLogInWithNonCaseSensitiveUsername(){
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername("STUDENT");
        loginPage.inputPassword("Password123");
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your username is invalid!");
    }

    @AfterMethod
    public void method(){
        driver.quit();
    }

}
