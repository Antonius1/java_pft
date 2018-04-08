package ru.stqa.pft.addressbook.tests;



import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    app.contact().returnHome();
    Contacts before = app.contact().all();
    app.contact().initContactCreation();
    ContactData contact = new ContactData().withFirstname("test1").withLastname("test2").withHomephone("test3").withMail("test1.test2@test4").withGroup("test1");
    app.contact().create(contact, true);
    app.contact().returnHome();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() {
    app.contact().returnHome();
    Contacts before = app.contact().all();
    app.contact().initContactCreation();
    ContactData contact = new ContactData().withFirstname("test1").withLastname("test2'").withHomephone("test3").withMail("test1.test2@test4").withGroup("test1");
    app.contact().create(contact, true);
    app.contact().returnHome();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}
