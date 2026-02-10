package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

	@LocalServerPort
	private int serverPort;

	@Value("${app.baseUrl:http://localhost}")
	private String testBaseUrl;

	private String baseUrl;

	@BeforeEach
	void setupTest() {
		baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
	}

	@Test
	void userCanCreateProduct_andSeeItInList(ChromeDriver driver) {
		String productName = "Functional Test Product";
		String productQty = "42";

		// open create page
		driver.get(baseUrl + "/product/create");

		// fill form
		driver.findElement(By.id("nameInput")).sendKeys(productName);
		driver.findElement(By.id("quantityInput")).sendKeys(productQty);

		// submit
		driver.findElement(By.cssSelector("form")).submit();

		// wait until redirected to list and product appears
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		boolean present = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath(String.format("//td[text()='%s']", productName)))) != null;

		assertTrue(present, "Created product should appear in product list");
	}
}
