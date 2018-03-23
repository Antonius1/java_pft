package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoContactPage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test1.test2@test4", "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModificator();
    app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "test1.test2@test4", null), false);
    app.getContactHelper().submitContactModificator();
    app.getContactHelper().returnHome();
  }
}
