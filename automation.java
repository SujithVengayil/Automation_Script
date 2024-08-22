simport org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class BookswagonTest 
{
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "Here You need to paste your webdriver path");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testBookswagonFlow() {
        // Step 1: Opening the URL
        driver.get("https://www.bookswagon.com/");

        // Step 2 & 3: Hover over 'Books' dropdown and click 'Historical fiction'
        WebElement booksDropdown = driver.findElement(By.xpath("/html/body/form/nav/div/div/div[1]/ul/li[1]/a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(booksDropdown).perform();

        WebElement historicalFiction = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Historical fiction")));
        historicalFiction.click();

        // Step 5: Sort results by "Title - A to Z"
        Select sortDropdown = new Select(driver.findElement(By.id("ddlSort")));
        sortDropdown.selectByVisibleText("Title - A to Z");


        // Step 6: Verify first book is "1918"
        WebElement firstBook = driver.findElement(By.xpath("/html/body/form/div[10]/div[1]/div[2]/div[3]/div[1]"));
        Assert.assertEquals(firstBook.getText(), "1918", "First book is not '1918'");

        // Step 7: Add "1918" to cart and verify cart count
        String cartPrice = driver.findElement(By.xpath("/html/body/form/header/div[2]/div/div[3]/ul/li[2]/a/img")).getText();
        driver.findElement(By.xpath("/html/body/form/div[10]/div[1]/div[2]/div[3]/div[1]/div[4]/div[5]/input[1]")).click();
        Assert.assertEquals(cartPrice .getText(), "1", "Cart count not updated to 1");

        // Step 8: Navigate to cart and verify book details
        driver.findElement(By.xpath("/html/body/form/header/div[2]/div/div[3]/ul/li[2]/a/img")).click();
        WebElement cartBookPrice = driver.findElement(By.cssSelector(".cart-item-price"));

        Assert.assertEquals(cartBookName.getText(), "1918", "Book name in cart doesn't match");
        Assert.assertEquals(cartBookPrice.getText(), bookPrice, "Book price in cart doesn't match");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}