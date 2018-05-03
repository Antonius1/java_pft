package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new ContactData().withFirstname("firstname1").withLastname("lastname 1").withGroup("test1")});
    list.add(new Object[]{new ContactData().withFirstname("firstname2").withLastname("lastname 2").withGroup("test2")});
    list.add(new Object[]{new ContactData().withFirstname("firstname3").withLastname("lastname 3").withGroup("test3")});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    app.contact().returnHome();
    Contacts before = app.contact().all();
    app.contact().initContactCreation();
    //File photo = new File("src/test/resources/stru.png");
    app.contact().create(contact, true);
    app.contact().returnHome();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }


  @Test(enabled = false)
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
