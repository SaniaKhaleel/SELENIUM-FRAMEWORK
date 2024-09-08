package rahulshettyacademy.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;

//Then, you can create an instance of Actions

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		String productName = "ZARA COAT 3";
		ChromeDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		//driver.findElement(By.id("userEmail")).sendKeys("webtesting.sania@gmail.com");
		//driver.findElement(By.cssSelector("#userPassword")).sendKeys("Sania123");
		driver.findElement(By.id("userEmail")).sendKeys("darshaahmed836@gmail.com");
		driver.findElement(By.cssSelector("#userPassword")).sendKeys("Demo@123");
		driver.findElement(By.id("login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		WebElement product = products.stream()
				.filter(p -> p.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		product.findElement(By.cssSelector(".card-body .btn:last-of-type")).click();
		System.out.println("Product added to cart: " + productName);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		driver.findElement(By.cssSelector("[routerlink='/dashboard/cart']")).click();

		List<WebElement> cardproducts = driver.findElements(By.cssSelector(".cartSection h3"));
		boolean match = cardproducts.stream()
				.anyMatch(cardproduct -> cardproduct.getText().equalsIgnoreCase(productName));

		Assert.assertTrue(match, "The product " + productName + " was not found in the cart.");
		driver.findElement(By.cssSelector(".totalRow button")).click();

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[contains(@class, 'ta-item')][2]")).click();

		WebElement submitButton = driver.findElement(By.cssSelector(".action__submit"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
		submitButton.click();

		driver.findElement(By.cssSelector(".hero-primary"));
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();

	}
}