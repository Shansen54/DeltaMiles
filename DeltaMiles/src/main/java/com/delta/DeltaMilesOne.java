package com.delta;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DeltaMilesOne {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
//		WebDriver driver = new FirefoxDriver();

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		driver.get("https://www.delta.com");

		WebElement originInput = driver.findElement(By.id("originCity"));
		originInput.clear();
		originInput.sendKeys("SLC");
		
		WebElement cookiesInfoButton = driver.findElement(By.id("ck-banner-close"));
		cookiesInfoButton.click();
				
		WebElement destInput = driver.findElement(By.id("destinationCity"));
		destInput.clear();
		destInput.sendKeys("ANC");
		
		WebElement flexibleDaysButton = driver.findElement(By.id("flexDaysBtn"));
		flexibleDaysButton.click();
		
		WebElement milesButton = driver.findElement(By.id("milesBtn"));
		milesButton.click();

		WebElement departInput = driver.findElement(By.id("departureDate"));
		departInput.sendKeys("06/06/2018");

		WebElement returnInput = driver.findElement(By.id("returnDate"));
		returnInput.sendKeys("06/13/2018");
		
		WebElement findFlightsButton = driver.findElement(By.id("findFlightsSubmit"));
		findFlightsButton.click();

		waitForLoad();
		
		driver.getTitle();
		System.out.println(driver.getTitle());

		WebElement findLowestFare = driver.findElement(By.cssSelector("span.awardCalCellPrice.lowestFare.awardCalCellPriceSmall"));	
		findLowestFare.getText();
		
		
		
	}
	
	public void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

}
