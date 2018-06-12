package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;


public class CreateProductTest extends BaseTest {

    @Test(dataProvider = "authorization")
    public void createNewProduct(String login, String pass) {

        actions.login(login, pass);
        actions.createProduct(ProductData.generate());
    }

    @Test(dependsOnMethods = {"createNewProduct"})
    public void checkTheProduct(){
        actions.goToStore();
        actions.checkTheNameOfTheProduct();
        actions.detailedInfoAboutProduct();
        actions.chekcAllFields();
    }
}
