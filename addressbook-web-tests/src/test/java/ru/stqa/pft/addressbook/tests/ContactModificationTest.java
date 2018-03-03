package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModificator();
    app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "test1.test2@test4"));
    app.getContactHelper().submitContactModificator();
    app.getContactHelper().returnHome();
  }
}
