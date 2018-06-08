package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().isEmpty()) {
      Groups groups = app.db().groups();
      GroupData groupData;

      if (groups.isEmpty()) {
        app.goTo().groupPage();
        groupData = new GroupData().withName("test1");
        app.group().create(groupData);
      } else {
        groupData = groups.iterator().next();
      }

      app.contact().returnHome();
      if (app.db().contacts().size() == 0) {
        app.contact().create(new ContactData().withFirstname("test1").withLastname("test2").withHomephone("test3").withMail("test1.test2@test4").inGroup(new GroupData().withName("test1")), true);
      }
    }
  }

  @Test
  public void testDeleteContactFromGroup() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    Groups list = app.db().groups();
    GroupData firstgroup = list.iterator().next();

    if (deletedContact.getGroups().isEmpty()) {
      ContactData addContact = before.iterator().next();
      app.contact().add(addContact);
    }
    app.contact().delete(deletedContact, firstgroup);
    assertThat(app.contact().count(),equalTo(before.size() - 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
  }
}
