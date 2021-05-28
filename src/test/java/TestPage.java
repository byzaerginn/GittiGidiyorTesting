import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestPage {

    public WebDriver driver;

    final static public Logger logger = Logger.getLogger(TestPage.class.getName());

    public static JavascriptExecutor js;

    @Before
     public void giris() throws InterruptedException{
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();


    }

    @Test
    public void Testing() throws InterruptedException{

        driver.get("https://www.gittigidiyor.com/");
        String controlUrl = driver.getCurrentUrl();
        Assert.assertEquals("https://www.gittigidiyor.com/", driver.getCurrentUrl());
        System.out.println("Anasayfa kontrolu sağlandı.");

        driver.get("https://www.gittigidiyor.com/uye-girisi?s=1");

        WebElement username=driver.findElement(By.id("L-UserNameField"));
        WebElement password=driver.findElement(By.id("L-PasswordField"));
        username.sendKeys("byzaerginn@gmail.com");
        password.sendKeys("beyer1071");
        driver.findElement(By.id("gg-login-enter")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gittigidiyor.com/");
        logger.info("Login yapıldı.");


        driver.findElement(By.cssSelector("[name='k']")).sendKeys("Bilgisayar");
        driver.findElement(By.cssSelector("[data-cy='search-find-button']")).click();
        System.out.println("Kelime aratıldı.");
        Thread.sleep(500);

        WebElement webElement = driver.findElement(By.xpath("//*[@id='best-match-right']/div[5]/ul/li[2]/a"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", webElement);
        System.out.println("2.sayfaya gidildi.");

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");

        System.out.println("2.sayfada oldugu kontrol edildi.");


        driver.findElement(By.cssSelector("product-id='673017705'")).click();
        System.out.println("Urun secildi.");

        js = ((JavascriptExecutor) driver);
        js.executeScript("window.scroll(0,550)");
        Thread.sleep(500);
        driver.findElement(By.id("add-to-basket")).click();
        System.out.println("Secilen urun sepete eklendi.");

        driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a/div[1]")).click();
        System.out.println("Sepete gidildi.");


        WebElement element = driver.findElement(By.cssSelector("#sp-price"));
        String listPrice = element.getAttribute("value");
        System.out.println("Secilen ürün fiyatı : " +listPrice);

        WebElement element1 = driver.findElement(By.cssSelector(".data-salePrice"));
        String basketPrice = element1.getAttribute("value");
        System.out.println("Sepet ürün fiyatı : " +basketPrice);
        Assert.assertEquals(listPrice,basketPrice);
       System.out.println("Urun karşılaştırması yapıldı.");

        Thread.sleep(500);

        driver.findElement(By.cssSelector("select.amount>:nth-child(2)")).click();
        System.out.println("Ürün adedi ikiye çıkarıldı.");

        driver.findElement(By.cssSelector(".btn-delete.btn-update-item.hidden-m")).click();

        System.out.println("Sepet boşaltıldı.");
        String BasketEmpty = driver.findElement(By.cssSelector("[class='cart-title']")).getText();
        Assertions.assertTrue(BasketEmpty.isEmpty());
        System.out.println(("Sepet kontrol edildi."));
    }
    @After
    public void quit(){

        driver.quit();
    }

}
