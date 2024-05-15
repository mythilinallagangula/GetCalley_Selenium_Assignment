package com.TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ResolutionTesting {
	 
	public static void getScreenShot( String fileWithPath, WebDriver driver) throws IOException
	{
		TakesScreenshot scrshot=((TakesScreenshot) driver);
		File scrFile=scrshot.getScreenshotAs(OutputType.FILE);
		File desFile=new File(fileWithPath);
		FileUtils.copyFile(scrFile, desFile);
	}
	public static List<String> readUrlsFromExcel() throws IOException
	{
		FileInputStream file = new FileInputStream("D:\\MythiliWorkSpace\\URL.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		List<String> urls=new ArrayList<String>();
		urls.add(sheet.getRow(0).getCell(0).toString());
		urls.add(sheet.getRow(0).getCell(1).toString());
		urls.add(sheet.getRow(0).getCell(2).toString());
		urls.add(sheet.getRow(0).getCell(3).toString());
		urls.add(sheet.getRow(0).getCell(4).toString());

		return urls;
	}
	
	public static void closeBrowser(WebDriver driver) throws InterruptedException
	{
		Thread.sleep(5000);
		driver.close();
	}
	
	public static void openBrowser(WebDriver driver,int width, int height, String url) throws Exception
	{
		System.setProperty("webdriver.chrome.driver", "D://drivers//chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().setSize(new Dimension(width, width));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get(url);
	}
	
	public static void OpenUrlwithDifferentResolutions() throws Exception
	{
		List<String> Urls =  readUrlsFromExcel();
		List<Dimension> res=new ArrayList<Dimension>();
		res.add(new Dimension(1920,1080));
		res.add(new Dimension(1366,768));
		res.add(new Dimension(1536,864));
		res.add(new Dimension(360,640));
		res.add(new Dimension(414,896));
		res.add(new Dimension(375,667));
		int screenshotcount=1;
		for(String url:Urls)
		{			
			for(Dimension resolution:res)
			{				
				WebDriver driver=new ChromeDriver();
				String fileName = String.format("D:\\MythiliWorkSpace\\Screenshot_getcalley\\Screenshot_getcalley_%dX%d_%d.jpg", 
						resolution.getWidth(), resolution.getHeight(),screenshotcount);
				
				openBrowser(driver,resolution.getWidth(), resolution.getHeight(), url);
				getScreenShot(fileName, driver);
				closeBrowser(driver);
				screenshotcount++;
			}
		}
		
	}
	public static void main(String[] args) throws Exception {
		
		
		OpenUrlwithDifferentResolutions();
		
	}

}


