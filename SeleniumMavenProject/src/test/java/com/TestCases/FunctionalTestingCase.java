package com.TestCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.record.ScreenRecorderUtil;

public class FunctionalTestingCase {
	public static WebDriver driver;
	public static void openBrowser() throws Exception
	{

		System.setProperty("webdriver.chrome.driver", "D://drivers//chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://demo.dealsdray.com/");
	}

	public static void getScreenShot(String fileWithPath) throws IOException
	{
		TakesScreenshot scrshot=((TakesScreenshot) driver);
		File scrFile=scrshot.getScreenshotAs(OutputType.FILE);
		File desFile=new File(fileWithPath);
		FileUtils.copyFile(scrFile, desFile);
	}

	public static void verifyLoginFunctionality(String username, String Password)
	{
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public static void importDemoDataXlsxFile(String filePath) throws Exception
	{
		driver.findElement(By.xpath("(//button[@type='button'])[1]")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		driver.findElement(By.xpath("//span[.='Orders']")).click();
		driver.findElement(By.xpath("//button[.='Add Bulk Orders']")).click();
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(filePath);
		WebElement import_Button =driver.findElement(By.xpath("//button[.='Import']"));
		import_Button.click();
		driver.findElement(By.xpath("//button[.='Validate Data']")).click();
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alt=driver.switchTo().alert();
		System.out.println(alt.getText());
		alt.accept();
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("scroll(0, 350);");
		getScreenShot("D:\\MythiliWorkSpace\\Screenshot.img.jpg");
	}

	public static void closeBrowser() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.close();
	}
	public static void main(String[] args) throws Exception {
		ScreenRecorderUtil.startRecord("main");
		openBrowser();
		verifyLoginFunctionality("prexo.mis@dealsdray.com", "prexo.mis@dealsdray.com");
		importDemoDataXlsxFile("D:\\MythiliWorkSpace\\demo-data.xlsx");
		ScreenRecorderUtil.stopRecord();
		closeBrowser();
	}
}
