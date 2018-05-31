package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {


  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().ContactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstname("test1").withLastname("test2").withHomephone("test3").withMail("test1.test2@test4").inGroup(new GroupData().withName("test1")), true);
    }
  }

  @Test
  public void testContactAddress() {
    app.goTo().ContactPage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(cleanedAddress(contactInfoFromEditForm.getAddress())));

  }

  public static String cleanedAddress(String address) {
    return address.replaceAll("\n", "");
  }
}
