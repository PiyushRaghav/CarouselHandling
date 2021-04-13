package main;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class ProductNames {
	static WebDriver driver = null;

	public static void main(String[] args) {
		String cat1 = "Recommended For You";
		String cat2 = "Save big on mobiles & tablets";
		String cat3 = "Top picks in electronics";
		List<String> products_name = new ArrayList<String>();
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.noon.com/uae-en/");

		System.out.println("*****************product names of secton: " + cat1 + "*****************");
		products_name=getProductNames_SortedList(cat1);
		printList(products_name);
		products_name.clear();
		
		System.out.println("*****************product names of secton: " + cat2 + "*****************");
		products_name=getProductNames_SortedList(cat2);
		printList(products_name);
		products_name.clear();
		
		System.out.println("*****************product names of secton: " + cat3 + "*****************");
		products_name=getProductNames_SortedList(cat3);
		printList(products_name);
		driver.quit();
	}

	public static List<String> getProductNames_SortedList(String section_heading) {
		List<String> products_name = new ArrayList<String>();
		try {
			//proceeding only when category is available on the web page
			if (driver.findElement(By.xpath("//h3[text()='" + section_heading + "']")).isDisplayed()) {

				String product_xpath = "//*[text()='" + section_heading	+ "']/../../following-sibling::div//div[contains(@class,'swiper-slide')]//div[@data-qa='product-name']";
				String next_button_xpath = "//*[text()='" + section_heading	+ "']/../../following-sibling::div//div[contains(@class,'swiper-button-next')]";
				
				List<WebElement> products = driver.findElements(By.xpath(product_xpath));
				WebElement nextButton = driver.findElement(By.xpath(next_button_xpath));

				for (WebElement el : products) {
					//clicking on NEXT BUTTON if element is not displayed 
					if (!(el.isDisplayed())) {
						JavascriptExecutor ex = (JavascriptExecutor) driver;
						ex.executeScript("arguments[0].scrollIntoView()", nextButton);
						ex.executeScript("arguments[0].click();", nextButton);
						Thread.sleep(1000);
					}
					products_name.add(el.getText());
				}
				//default sorting: Alphabetically
				products_name.sort(null);
			} else {
				System.out.println(section_heading + "is Not Found!! moving to next Category...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products_name;
	}

	public static void printList(List<String> productlist) {
		System.out.println("Totl products: "+productlist.size());
		ListIterator<String> itr = productlist.listIterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
