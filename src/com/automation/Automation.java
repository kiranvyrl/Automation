/**
 * Author Kiran V
 */
package com.automation;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Automation {

	public static void main(String[] args) {

		// Chrome driver
		System.setProperty("webdriver.chrome.driver", "C:\\misc\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		String baseUrl = "http://magazine.trivago.com/";

		// launch chrome and direct it to the Base URL
		driver.get(baseUrl);

		driver.findElement(By.className(" Cookie__button")).click();

		/*
		 * Step1: Search for any location on Room5
		 */
		WebElement search = driver.findElement(By.xpath("//div[@class='search-icon']"));
		search.click();
		WebElement searchText = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/input"));
		searchText.sendKeys("Germany");
		searchText.sendKeys(Keys.ENTER);

		WebElement searchClose = driver.findElement(By.xpath("//div[@class='search-icon open']"));
		searchClose.click();

		driver.manage().window().maximize();

		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");

		driver.findElement(By.xpath("//a[@href='/contact/']")).click();

		ArrayList<String> tabControl = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabControl.get(0));
		driver.switchTo().window(tabControl.get(1));

		/* Initialize the WebDriverWait, with 30 seconds of wait time. */
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loader loading']")));

		/*
		 * Step2: Contact form automation
		 */

		WebElement contactTextArea = driver
				.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div/div[1]/div[2]/div/textarea"));
		contactTextArea.sendKeys("Hello, Automation testing contact form. Thanks ");

		WebElement contactFirstName = driver
				.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div/div[1]/div[3]/div[1]/input"));
		contactFirstName.sendKeys("Test Test");

		WebElement contactEmail = driver.findElement(By.xpath("//*[@id=\"contact-email\"]"));
		contactEmail.sendKeys("test@test.com");

		WebElement contactCheckbox = driver.findElement(By.xpath("//*[@id=\"confirm\"]"));
		contactCheckbox.click();

		WebElement contactSubmit = driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div/div[1]/button/span"));
		contactSubmit.click();
		driver.close();
		driver.switchTo().window(tabControl.get(0));

		/*
		 * Step3: Newsletter Subscription
		 */

		WebElement newsLetterCheckbox = driver.findElement(By.xpath("//*[@id=\"confirm\"]"));
		newsLetterCheckbox.click();

		driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[7]/section/div/div/div[2]/div[3]/div[1]/input"))
				.sendKeys("test@test.com");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[7]/section/div/div/div[2]/div[3]/div[2]/button"))
				.click();

		/*
		 * Step4: Navigation to Destination
		 */

		driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div[1]")).click();

		/*
		 * Control test cases for 2 actions
		 */

		driver.get("http://magazine.trivago.com/");

		WebElement mostSearchTag = driver
				.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[3]/div/div/div[1]/div[1]/div/a/div/div"));
		Actions actions = new Actions(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mostSearchTag);
		actions.moveToElement(mostSearchTag).click().perform();
		mostSearchTag.click();

		wait.until(ExpectedConditions.urlContains("/theme/family-vacations/"));
		if (driver.getCurrentUrl().contains("/theme/family-vacations/")) {
			System.out.println("Case1: Trending Success");
		} else {
			System.out.println("Case1: Trending Failure");
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loader loading']")));
		WebElement findHotel = driver.findElement(By.xpath("//span[@class='green-btn']"));
		findHotel.click();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.get("https://www.trivago.com/?cip=1110000000003");
		driver.switchTo().window(tabs.get(0));
		driver.findElement(By.xpath("//*[@id=\"horus-querytext\"]")).sendKeys("Berlin");
		driver.findElement(By.xpath("//span[@class='horus-btn-search__label']")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\\\"horus-querytext\\\"]")));
		WebElement checkInDay = driver.findElement(By.xpath("//*[@id=\"horus-querytext\"]"));
		checkInDay.click();

		// close chrome browser
		driver.close();

	}
}
