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

public class LoginTestWithExcel extends BaseTest {


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

    @Test (priority = 10)
    public void userCanLogIn() {
        String validUsername = excelReader.getStringData("Sheet1", 1,0);
        String validPassword = excelReader.getStringData("Sheet1", 1,1);
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(profilePage.getLogOutButton().isDisplayed());
        Assert.assertTrue(profilePage.getMessage().isDisplayed());
        Assert.assertEquals(profilePage.getMessage().getText(), "Congratulations "+validUsername+". You successfully logged in!");
    }

    @Test (priority = 20)
    public void userCannotLogInWithInvalidUsername(){
        for (int i=1; i <= excelReader.getLastRow("Sheet1"); i++) {

            String invalidUsername = excelReader.getStringData("Sheet1", i, 2);
            String validPassword = excelReader.getStringData("Sheet1", 1, 1);
            homepagePage.clickOnPracticeButton();
            practicePage.clickOnTestLoginPageButton();
            loginPage.inputUsername(invalidUsername);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnSubmitButton();
            Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
            Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
            Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your username is invalid!");
        }
    }

    @Test (priority = 30)
    public void userCannotLogInWithInvalidPassword(){
        for (int i=1; i <= excelReader.getLastRow("Sheet1"); i++) {

            String validUsername = excelReader.getStringData("Sheet1", 1, 0);
            String invalidPassword = excelReader.getStringData("Sheet1", i, 3);
            homepagePage.clickOnPracticeButton();
            practicePage.clickOnTestLoginPageButton();
            loginPage.inputUsername(validUsername);
            loginPage.inputPassword(invalidPassword);
            loginPage.clickOnSubmitButton();
            Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
            Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
            Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your password is invalid!");
        }
    }

    @Test (priority = 40)
    public void userCannotLogInWithInvalidPasswordAndUsername(){
        for (int i=1; i <= excelReader.getLastRow("Sheet1"); i++) {

            String invalidUsername = excelReader.getStringData("Sheet1", i, 2);
            String invalidPassword = excelReader.getStringData("Sheet1", i, 3);
            homepagePage.clickOnPracticeButton();
            practicePage.clickOnTestLoginPageButton();
            loginPage.inputUsername(invalidUsername);
            loginPage.inputPassword(invalidPassword);
            loginPage.clickOnSubmitButton();
            Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
            Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
            Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your username is invalid!");
        }
    }
    @Test (priority = 50)
    public void userCannotLogInWithEmptyFields(){
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername("");
        loginPage.inputPassword("");
        loginPage.clickOnSubmitButton();
        Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage().getText(), "Your username is invalid!");
    }

    @Test(priority = 60)
    public void userCanLogout(){
        String validUsername = excelReader.getStringData("Sheet1", 1,0);
        String validPassword = excelReader.getStringData("Sheet1", 1,1);
        homepagePage.clickOnPracticeButton();
        practicePage.clickOnTestLoginPageButton();
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnSubmitButton();
        profilePage.clickOnLogOutButton();
        Assert.assertTrue(loginPage.getSubmitButton().isDisplayed());
    }

    @AfterMethod
    public void method(){
        driver.quit();
    }
}
