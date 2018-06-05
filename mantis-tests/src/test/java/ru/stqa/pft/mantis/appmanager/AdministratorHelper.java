package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

import static org.openqa.selenium.By.*;


public class AdministratorHelper extends HelperBase {
  public AdministratorHelper(ApplicationManager app) {
    super(app);
  }


  public void login() {
    app.getDriver();
    wd.findElement(id("username")).sendKeys("administrator");
    click(cssSelector("input[value='Login']"));
    wd.findElement(id("password")).sendKeys("root");
    click(cssSelector("input[value='Login']"));
  }

  public void changePass() {
    click(linkText("Manage"));
    click(linkText("Manage Users"));
    click(linkText("user1"));
    //click(cssSelector("input[value='Reset Password']"));
    click(xpath("//form[@id='manage-user-reset-form']/fieldset/span/input"));
  }


  public void finish(String confirmationLink) {
    wd.get(confirmationLink);
    wd.findElement(name("password")).sendKeys("pass");
    wd.findElement(name("password_confirm")).sendKeys("pass");
    //click(By.xpath("//span[@class='submit-button']/button"));
    click(By.xpath("//button/span[@class='bigger-110']"));
  }
}
