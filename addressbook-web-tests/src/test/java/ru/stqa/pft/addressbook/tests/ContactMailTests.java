package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactMailTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().ContactPage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstname("test1").withLastname("test2").withHomephone("test3").withMail("test1.test2@test4").withGroup("test1"), true);
    }
  }

  @Test
  public void testContactEmail() {
    app.goTo().ContactPage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));


  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getMail(), contact.getMail2(), contact.getMail3())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactMailTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private static String cleaned(String email) {
    return email.replaceAll("\\s", "").replaceAll("[()]", "").replaceAll("\n", "");
  }


}
