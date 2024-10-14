package Base;

import Pages.HomepagePage;
import Pages.LoginPage;
import Pages.PracticePage;
import Pages.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;

    //Da bi mogli da korisitmo ove stranice moramo da napravimo objekte i da ih deklarišemo u BeforeClass
    public HomepagePage homepagePage;
    public PracticePage practicePage;
    public LoginPage loginPage;
    public ProfilePage profilePage;
    public ExcelReader excelReader;

    @BeforeClass
    public void setUp() throws IOException {
        //Prebacujemo u Before Method da bi se svaki test odradio u novom browser-u
        /*WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));*/
        homepagePage = new HomepagePage(driver);
        practicePage = new PracticePage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        excelReader = new ExcelReader("TestData1.xlsx");

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
