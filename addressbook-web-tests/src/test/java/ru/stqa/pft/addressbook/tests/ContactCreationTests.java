package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "test1.test2@test4", "test1"), true);
    app.getContactHelper().enterContactData();
    app.getContactHelper().returnHome();
  }

}
