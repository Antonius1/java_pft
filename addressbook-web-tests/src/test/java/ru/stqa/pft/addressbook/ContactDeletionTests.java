package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactDeletionTests extends TestBase {

    

    @Test
    public void testContactDeletion() {
        gotoContactPage();
        selectContact();
        deleteSelectedContacts();
        returnHome();
    }

}
