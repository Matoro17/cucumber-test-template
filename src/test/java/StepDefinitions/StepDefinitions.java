package StepDefinitions;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class StepDefinitions {
    ChromeDriver driver;

    @Given("I access groceryCrud")
    public void iAccessGroceryCrud() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("disable-gpu");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.grocerycrud.com/v1.x/demo/my_boss_is_in_a_hurry/bootstrap");
    }

    @And("I swap theme to {string}")
    public void iSwapThemeTo(String theme) {
        Select e = new Select(driver. findElement(By.id("switch-version-select")));
        e.selectByVisibleText(theme);
    }

    @When("I Add a new costumer {string}")
    public void iAddANewCostumer(String name) {
        driver.findElement(By.xpath("//*[@id=\"gcrud-search-form\"]/div[1]/div[1]/a")).click();

        WebElement customerName = driver.findElement(By.id("field-customerName"));
        WebElement contactLastName = driver.findElement(By.id("field-contactLastName"));
        WebElement contactFirstName = driver.findElement(By.id("field-contactFirstName"));
        WebElement phone = driver.findElement(By.id("field-phone"));
        WebElement addressLine1 = driver.findElement(By.id("field-addressLine1"));
        WebElement addressLine2 = driver.findElement(By.id("field-addressLine2"));
        WebElement city = driver.findElement(By.id("field-city"));
        WebElement state = driver.findElement(By.id("field-state"));
        WebElement postalCode = driver.findElement(By.id("field-postalCode"));
        WebElement country = driver.findElement(By.id("field-country"));
        WebElement salesRepEmployeeNumber = driver.findElement(By.id("field-salesRepEmployeeNumber"));
        WebElement creditLimit = driver.findElement(By.id("field-creditLimit"));
        WebElement deleted = driver.findElement(By.id("field-deleted"));

        customerName.sendKeys("Teste Sicredi");
        contactLastName.sendKeys("Teste");
        contactFirstName.sendKeys(name);
        phone.sendKeys("51 9999-9999");
        addressLine1.sendKeys("Av Assis Brasil, 3970");
        addressLine2.sendKeys("Torre D");
        city.sendKeys("Porto Alegre");
        state.sendKeys("RS");
        postalCode.sendKeys("91000-000");
        country.sendKeys("Brasil");
        salesRepEmployeeNumber.sendKeys("Fixter");
        creditLimit.sendKeys("200");

        driver.findElement(By.id("form-button-save")).click();

    }

    @Then("Should respond with {string}")
    public void shouldRespondWith(String ExpectedText) {
        WebElement text = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"report-success\"]/p")));
        Assert.assertEquals(text.getText(), ExpectedText);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"report-success\"]/p/a[2]"))).click();
    }

    @When("I delete the costumer {string}")
    public void iDeleteTheCostumer(String name) throws InterruptedException {
        WebElement searchCustomerName = driver.findElement(By.name("customerName"));
        searchCustomerName.sendKeys("Teste Sicredi");
        searchCustomerName.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gcrud-search-form\"]/div[2]/table/tbody/tr[1]/td[2]/div[1]/div/button")));
        firstResult.click();
        WebElement delete = driver.findElement(By.xpath("//*[@id=\"gcrud-search-form\"]/div[2]/table/tbody/tr[1]/td[2]/div[1]/div/div/a[3]"));
        new Actions(driver)
                .scrollToElement(delete)
                .perform();
        delete.click();

        WebElement deleteConfirm = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div/div[3]/button[2]"));
        deleteConfirm.click();

        WebElement message = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/span[3]/p")));

        Assert.assertEquals("Your data has been successfully deleted from the database.", message.getText());
    }

    @Then("It shouldn't exists")
    public void itShouldnTExists() {

    }

    @And("Close browser")
    public void closeBrowser() {
        driver.close();
    }
}
