package com.delta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class DeltaMilesOne {

	public static void main(String[] args) {

		System.setProperty("org.apache.commons.logging.Log",
			    "org.apache.commons.logging.impl.Jdk14Logger");
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

		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		WebElement pageIsUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("input_origin_1")));
		System.out.println(pageIsUp.getText());
		
		driver.getTitle();
		System.out.println(driver.getTitle());
		
		WebElement lowestFare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.highlightlowest")));
		System.out.println(lowestFare.getText());
		String lft = lowestFare.getText();
		lft = lft.replaceAll("\\D+","");
		double lftnumber = Double.valueOf(lft);
		
		ExcelWrite(lftnumber);
		
	}
				
	private static final String EXCEL_FILE_LOCATION = "C:\\temp\\DeltaMiles.xls";

	public static void ExcelWrite(double lft)
	{
	
		// Instantiate a Date object
	    Date date = new Date();

	    // display time and date using simpleDateFormat()
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd kk:mm:ss");
	    System.out.println(ft.format(date));
	    String ftd = ft.format(date);

		if (Files.exists(Paths.get(EXCEL_FILE_LOCATION))) {
			
		     //2. Write to an existing Excel file
            Workbook workbook1 = null;
            try {

                java.io.File workbookeditable = new java.io.File(EXCEL_FILE_LOCATION);
                workbook1 = Workbook.getWorkbook(workbookeditable);

                Sheet sheet = workbook1.getSheet(0);
                Cell cell1 = sheet.getCell(0, 0);
                //test that cell has something in it.
               for (int i=1;  i <= 3000;  i++) {
            	   if (sheet.getCell(0, i) == null) {
   		            Label dateAndTime1 = new Label(0, i, ftd);
		            ((WritableSheet) sheet).addCell((WritableCell) dateAndTime1);

		            Number lowFare1 = new Number(1, i, lft);
		            ((WritableSheet) sheet).addCell(lowFare1);
	
		            //put in a writable line of code here
		            workbook1.close();

		            
            		return;   
            	   }
               }
            }	
            finally { 
			
			
		        //2. Create an Excel file
		        WritableWorkbook myFirstWbook = null;
		        try {
	        		java.io.File workbook = new java.io.File(EXCEL_FILE_LOCATION);
		            myFirstWbook = Workbook.createWorkbook(workbook);

		            // create an Excel sheet
		            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

		            // add something into the Excel sheet
		            Label label = new Label(0, 0, "Date Checked");
		            excelSheet.addCell(label);

		            Label dateAndTime = new Label(0, 1, ftd);
		            excelSheet.addCell((WritableCell) dateAndTime);

		            label = new Label(1, 0, "Lowest Miles");
		            excelSheet.addCell(label);

		            Number lowFare = new Number(1, 1, lft);
		            excelSheet.addCell(lowFare);

		            myFirstWbook.write();
		            myFirstWbook.close();
		        }
		      
		             catch (IOException | BiffException e) {
		                e.printStackTrace();
		            } finally {

		                if (workbook1 != null) {
		                	workbook1.close();
		                }

		            }
		            

		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (WriteException e) {
		            e.printStackTrace();
		        } finally {

		            if (myFirstWbook != null) {
		                try {
		                    myFirstWbook.close();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                } catch (WriteException e) {
		                    e.printStackTrace();
		                }
		            }


		        }

		    }
		
	}
}
