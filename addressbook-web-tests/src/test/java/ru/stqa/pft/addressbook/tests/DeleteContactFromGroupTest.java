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

public class DeleteContactFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1"));
    }

    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname("test1").withLastname("test2")
              .withHomephone("test3").withMail("test1.test2@test4")
              .inGroup(new GroupData().withName("test1")), true);
    }
  }

  @Test
  public void testDeleteContactFromGroup() {
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();
    int selectedId = 0;
    ContactData selectedContact = new ContactData();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() > 0) {
        selectedId = contact.getId();
        selectedContact = contact;
      }
      if (selectedId == 0) {
        selectedContact = new ContactData().withFirstname("test1")
                .withLastname("test2").withHomephone("test3").withMail("test1.test2@test4");
        app.contact().create(selectedContact, true);
      }
    }
    GroupData selectedGroup = selectedContact.getGroups().iterator().next();
    Groups before = selectedContact.getGroups();
    app.contact().returnHome();
    app.contact().selectGroup(selectedGroup.getName());
    app.contact().selectContactById(selectedContact.getId());
    app.contact().deleteFromGroup();
    ContactData finalSelectedContact = selectedContact;
    Groups after = app.db().contacts().stream().filter((c) -> c.equals(finalSelectedContact))
            .collect(Collectors.toList()).iterator().next().getGroups();
    before.add(selectedGroup);
    before.remove(selectedGroup);
    assertThat(after, equalTo(before.withOut(selectedGroup)));
  }
}


