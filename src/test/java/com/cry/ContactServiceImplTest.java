package com.cry;

import com.cry.core.messages.requests.ContactRequest;
import com.cry.core.messages.response.ContactDTO;
import com.cry.core.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
public class ContactServiceImplTest {

    @Autowired
    ContactService contactService;

    @Test
    @Transactional
    @Rollback
    public void given_ContactRequest_When_Create_Then_ReturnContactDTO() {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.test.com");
        request.setName("testName");
        ContactDTO contactDTO = contactService.create(request);

        assertNotNull(contactDTO);
    }

    @Test
    @Transactional
    @Rollback
    public void given_ContactRequest_When_createContact_Then_ContactDTO() {

        ContactRequest request = new ContactRequest();
        request.setName("testName");
        request.setUrl("http://www.test.com");
        ContactDTO dto1 = contactService.create(request);

        ContactRequest request2 = new ContactRequest();
        request2.setName("testName2");
        request2.setUrl("http://www.test2.com");
        ContactDTO dto2 = contactService.create(request2);

        assertEquals(request.getName(), dto1.getName());
        assertEquals(request.getUrl(), dto1.getUrl());
        assertEquals(request2.getName(), dto2.getName());
        assertEquals(request2.getUrl(), dto2.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void given_ContactRequest_When_Update_Then_ReturnContactDTO() {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.test.com");
        request.setName("testName");
        ContactDTO contactDTO = contactService.create(request);

        ContactRequest updateRequest = new ContactRequest();
        updateRequest.setUrl("http://www.updateTest.com");
        updateRequest.setName("updateName");
        ContactDTO updatedDTO = contactService.update(contactDTO.getId(),updateRequest);

        assertEquals(updateRequest.getName(), updatedDTO.getName());
        assertEquals(updateRequest.getUrl(), updatedDTO.getUrl());
    }
    @Test(expected = EntityNotFoundException.class)
    @Transactional
    @Rollback
    public void given_nonExistContact_When_Update_Then_Exception() {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.test.com");
        request.setName("testName");
        ContactDTO contactDTO = contactService.create(request);

        ContactRequest updateRequest = new ContactRequest();
        updateRequest.setUrl("http://www.updateTest.com");
        updateRequest.setName("updateName");
        ContactDTO updatedDTO = contactService.update(Long.MAX_VALUE,updateRequest);
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    @Rollback
    public void given_id_When_delete_Then_ReturnContactDTO() {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.test.com");
        request.setName("testName");
        ContactDTO contactDTO = contactService.create(request);

        contactService.delete(contactDTO.getId());
        contactService.findById(contactDTO.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void given_ContactRequest_When_GetAllContacts_Then_ListOfContacts() {

        ContactRequest request = new ContactRequest();
        request.setName("testName");
        request.setUrl("http://www.test.com");
        contactService.create(request);

        assertTrue(contactService.getAllContacts().size()>0);
    }
}
