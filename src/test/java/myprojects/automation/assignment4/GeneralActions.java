package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import myprojects.automation.assignment4.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private ProductData newProduct;

    private By name = By.id("form_step1_name_1");
    private By createNewProduct = By.id("page-header-desc-configuration-add");
    private By quantityButton = By.id("tab_step3");
    private By quantityInput = By.id("form_step3_qty_0");
    private By priceButton = By.id("tab_step2");
    private By priceInput = By.id("form_step2_price_ttc");
    private By inServiceButton = By.className("switch-input ");
    private By popUpMessage = By.className("growl-message");
    private By closePopUpMessage = By.className("growl-close");
    private By catalogueMenu = By.id("subtab-AdminCatalog");
    private By catalogueProducts = By.id("subtab-AdminProducts");
    private By allProducts = By.cssSelector("#content > section > a");
    private By detailedInfoAboutProduct = By.cssSelector(".products.row > article:last-child * h1 > a");


    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        CustomReporter.log("Login as user - " + login);

        driver.navigate().to(Properties.getBaseAdminUrl());
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.name("submitLogin")).click();
        waitForContentLoad(By.id("main"));
    }

    public void goToTheProductsTab() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(this.catalogueMenu)).build().perform();
        waitForContentLoad(this.catalogueProducts);
        actions.moveToElement(driver.findElement(catalogueProducts)).click().build().perform();
        waitForContentLoad(By.id("main-div"));
    }

    public void createProduct(ProductData newProduct) {
        this.newProduct = newProduct;
        goToTheProductsTab();
        driver.findElement(this.createNewProduct).click();
        waitForContentLoad(name);

        WebElement name = driver.findElement(this.name);
        name.sendKeys(newProduct.getName());

        driver.findElement(this.quantityButton).click();

        WebElement qty = driver.findElement(this.quantityInput);
        qty.sendKeys(newProduct.getQty().toString());

        driver.findElement(this.priceButton).click();

        WebElement price = driver.findElement(this.priceInput);
        price.sendKeys(newProduct.getPrice());

        driver.findElement(this.inServiceButton).click();
        waitForContentLoad(this.popUpMessage);
        driver.findElement(this.closePopUpMessage).click();
    }


    public void goToStore() {
        driver.navigate().to(Properties.getBaseUrl());
        waitForContentLoad(By.id("content"));
        driver.findElement(this.allProducts).click();

    }


    public void checkTheNameOfTheProduct() {
        Assert.assertTrue(driver.findElement(By.cssSelector(".products.row > article:last-child * h1 > a"))
                .getText().contains(newProduct.getName()));
    }

    public void detailedInfoAboutProduct(){
        driver.findElement(this.detailedInfoAboutProduct).click();
    }

    public void chekcAllFields(){
        Assert.assertTrue(driver.findElement(By.cssSelector("h1.h1"))
                .getText().contains(newProduct.getName().toUpperCase()),"Name of the product is corrected");
        Assert.assertTrue(driver.findElement(By.cssSelector("span[content]"))
                .getText().contains(newProduct.getPrice()), "Price is corrected");
        Assert.assertTrue(driver.findElement(By.cssSelector(".product-quantities"))
                .getText().contains(newProduct.getQty().toString()), "Quantity is corrected");
    }




    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));

    }
}
