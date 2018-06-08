package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactInGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }

    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname("test1").withLastname("test2")
              .withHomephone("test3").withMail("test1.test2@test4")
              .inGroup(new GroupData().withName("test1")), true);
    }
  }

  @Test
  public void testAddContactInGroup() {
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();
    int selectedId = 0;
    ContactData selectedContact = new ContactData();
    for (ContactData contact : contacts) {
      if (!contact.getGroups().equals(groups)) {
        selectedId = contact.getId();
        selectedContact = contact;
      }
      if (selectedId != 0) {
        break;
      }
    }
    if (selectedId == 0) {
      selectedContact = new ContactData().withFirstname("test1")
              .withLastname("test2").withHomephone("test3").withMail("test1.test2@test4");
      app.contact().create(selectedContact, true);
    }

    groups.removeAll(selectedContact.getGroups());
    GroupData selectedGroup = groups.iterator().next();
    Groups before = selectedContact.getGroups();
    app.contact().returnHome();
    app.contact().selectContactById(selectedContact.getId());
    app.contact().addToGroup(selectedContact, selectedGroup.getName());
    ContactData finalSelectedContact = selectedContact;
    Groups after = app.db().contacts().stream().filter((c) -> c.equals(finalSelectedContact)).collect(Collectors.toList()).iterator().next().getGroups();
    before.add(selectedGroup);
    assertThat(after, equalTo(before.withAdded(selectedGroup)));
  }
}


