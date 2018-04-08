package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    app.contact().returnHome();
    List<ContactData> before = app.contact().list();
    app.contact().initContactCreation();
    ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withHomephone("test3").withMail("test1.test2@test4").withGroup("test1");
    app.contact().create(contact, true);
    app.contact().returnHome();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals( before, after);
  }
}
