package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    app.contact().returnHome();
    Set<ContactData> before = app.contact().all();
    app.contact().initContactCreation();
    ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withHomephone("test3").withMail("test1.test2@test4").withGroup("test1");
    app.contact().create(contact, true);
    app.contact().returnHome();
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals( before, after);
  }
}
