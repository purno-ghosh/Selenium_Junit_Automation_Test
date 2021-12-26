import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Java_Junit_Testing {
    WebDriver driver;
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions ops=new ChromeOptions();
        ops.addArguments("--headed");
        driver=new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

//---get title of Instagram---//
@Test
    public void getTitle() {
        driver.get("https://www.instagram.com/");
       String title = driver.getTitle();
       System.out.println(title);
       Assert.assertTrue(title.contains("Instagram"));
    }

//---Check jagonews24.com image Exists or not---//

    @Test
    public void Image() throws InterruptedException {
        driver.get("https://www.jagonews24.com/");
        Boolean status = driver.findElement(By.xpath("(//img[@src='https://cdn.jagonews24.com/media/common/logo.png'])[2]")).isDisplayed();
        Assert.assertEquals(status, true);

    }
    //--- test testbox--- //
    @Test
    public void test_testbox() throws InterruptedException {

        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("purno ghosh");
        driver.findElement(By.id("submit")).click();
        sleep(2000);
        String text = driver.findElement(By.id("name")).getText();
        Assert.assertTrue(text.contains("purno ghosh"));

    }

    @Test
    public void clickOnButon() {

        driver.get("https://demoqa.com/buttons");
        Actions action = new Actions(driver);
        List<WebElement> list = driver.findElements(By.cssSelector("button"));

        //double click on a button
        action.doubleClick(list.get(1)).perform();
        String text = driver.findElement(By.id("doubleClickMessage")).getText();
        Assert.assertTrue(text.contains("You have done a double click"));

        ////right click on a button
        action.contextClick(list.get(2)).perform();
        String text2 = driver.findElement(By.id("rightClickMessage")).getText();
        Assert.assertTrue(text2.contains("You have done a right click"));

        list.get(3).click();
        String text3 = driver.findElement(By.id("dynamicClickMessage")).getText();
        Assert.assertTrue(text3.contains("You have done a dynamic click"));
    }
    //black color & car name will be chosen from DropDown

@Test
    public void Select_from_DropDwon() {
    driver.get("https://demoqa.com/select-menu");
    Select Value = new Select(driver.findElement(By.id("oldSelectMenu")));
    Value.selectByValue("5");
    Select cars = new Select(driver.findElement(By.id("cars")));
    if (cars.isMultiple()) {
        cars.selectByValue("volvo");
        cars.selectByValue("audi");

    }
}
//---------Select Date-----//

    @Test
    public void selectDate() {
        driver.get("http://leafground.com/pages/Calendar.html");
       // driver.findElement(By.id("datepicker")).click();
        driver.findElement(By.id("datepicker")).sendKeys("10/10/2000");
        driver.findElement(By.id("datepicker")).sendKeys(Keys.ENTER);
    }


    //------------------------Handle Alerts---------------------------//

    @Test
    public void handleAlertsbutton() throws InterruptedException {

        driver.get("http://leafground.com/pages/Alert.html");
        driver.findElement(By.xpath("//button[@onclick='normalAlert()'][contains(.,'Alert Box')]")).click();
        driver.switchTo().alert().accept();

        sleep(2000);

        driver.findElement(By.xpath("//button[contains(.,'Confirm Box')]")).click();
        driver.switchTo().alert().accept();
        String text = driver.findElement(By.id("result")).getText();
        Assert.assertTrue(text.contains("You pressed OK!"));

        driver.findElement(By.xpath("//button[contains(.,'Prompt Box')]")).click();
        driver.switchTo().alert().sendKeys("Purno Ghosh");
        driver.switchTo().alert().accept();
        String text2 = driver.findElement(By.id("result1")).getText();
        Assert.assertTrue(text2.contains("You should not have enjoyed learning at Purno Ghosh as compared to TestLeaf! Right?"));

    }
    //--------Handle Tabs---------------//

    @Test
    public void TabS() throws InterruptedException {
        driver.get("https://demoqa.com/links");
        driver.findElement(By.id("simpleLink")).click();
        sleep(2000);
        ArrayList<String> None = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(None.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        Boolean status = driver.findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']")).isDisplayed();
        Assert.assertEquals(true, status);
        driver.close();
        driver.switchTo().window(None.get(0));
    }


    @Test
    public void uploadImage_on_Google() {
        driver.get("https://www.google.com.bd/imghp?hl=en&authuser=0&ogbl");
        driver.findElement(By.xpath("//*[@id=\"sbtc\"]/div[2]/div[3]/div[2]/span")).click();
        driver.findElement(By.xpath("//a[contains(.,'Upload an image')]")).click();

        WebElement uploadElement = driver.findElement(By.xpath("//*[@id=\"awyMjb\"]"));
        uploadElement.sendKeys("D:\\IMG_0197.JPG");

    }

    @Test
    public void handleIframe() {
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }
    //collecting multiple data from table//

    @Test
    public void scrapData() {
        driver.get("http://leafground.com/pages/table.html");
        WebElement table = driver.findElement(By.id("table_id"));
        List<WebElement> allRows = table.findElements(By.xpath("//*[@id=\"table_id\"]/tbody/tr"));
        int i = 0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.xpath("//*[@id=\"table_id\"]/tbody/tr/td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num[" + i + "] " + cell.getText());
            }
        }
    }
    //collecting single data from table//
    @Test
    public void oneRow() {
        driver.get("http://leafground.com/pages/table.html");
        String text= driver.findElement(By.xpath("//*[@id=\"table_id\"]/tbody/tr[5]")).getText();
        System.out.println(text);

    }
    @Test
    public void takeScreenShot() throws IOException {
        driver.get("http://leafground.com/pages/table.html");
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }
    @Test
    public void keyboardEvents() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.sendKeys("I love Bangladesh").perform();
        Thread.sleep(5000);
    }


}
