package task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SortSearchResultByValue {
	
public static WebDriver driver;
	
	@BeforeTest
	public void setUpDriver() {
		ChromeOptions o = new ChromeOptions();
		o.addArguments("--disable-notification");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(o);
	}
	
	@AfterTest
	public void afterrclass() {
		driver.quit();
	}
	
	@Test
	public void printPriceandName() throws InterruptedException {
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		
		Thread.sleep(5000);
		
		//search fot LG soundbar
		WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		WebElement searchbtn = driver.findElement(By.id("nav-search-submit-button"));
		searchBox.sendKeys("lg soundbar");
		searchbtn.click();
		
		Thread.sleep(5000);
	
		
		List<WebElement> allProduct = driver.findElements(By.xpath("//div[@class='puis-card-container s-card-container s-overflow-hidden aok-relative puis-include-content-margin puis puis-v37wyjnzl4u8m26eshvzoi5kqu s-latency-cf-section puis-card-border']"));
		Map<String, Double> details = new HashMap<String, Double>();
		
	
		//fetch all name and price of product from element which we store in list
		String price="0";
		for(WebElement temp : allProduct) {
		String name = temp.findElement(By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal']")).getText();
		
		try {
		 price = temp.findElement(By.xpath(".//span[@class='a-price-whole']")).getText().replace(",", "");
		}
		catch (Exception e) {
		   price = "0";
		}
		
		//convert price from string to double and put both name and value in hashmap
		double mainprice = Double.parseDouble(price);
		details.put(name, mainprice);
		
		}
		
		
		
		//here we sorting hashmap value by price
		List<Map.Entry<String, Double>> sortproduct = new ArrayList<Entry<String, Double>>(details.entrySet());
		Collections.sort(sortproduct, new Comparator<Map.Entry<String, Double>>(){
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		
		//for printing all items with name & price
		for(Map.Entry<String, Double> s : sortproduct) {
			System.out.println("Price of product ==> "+s.getValue()+" ::::::: name of product ==>> "+s.getKey());
			System.out.println();
		}
		
	}


}
