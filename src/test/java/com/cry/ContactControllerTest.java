package com.cry;

import com.cry.core.messages.requests.ContactRequest;
import com.cry.core.messages.response.ContactDTO;
import com.cry.core.service.ContactService;
import com.cry.core.web.ContactController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ContactService contactService;


    @Test
    public void givenContactRequest_when_CreateContact_thenSuccess()
            throws Exception {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.google.com");
        request.setName("google");


        mvc.perform(post("/api/v1/core/contact")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void givenContactRequest_when_UpdateContact_thenSuccess()
            throws Exception {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.google.com");
        request.setName("google");

        ContactDTO contactDTO = ContactDTO.builder()
                .id(99)
                .url("url.com")
                .name("test name")
                .build();

        given(contactService.create(request)).willReturn(contactDTO);
        ContactDTO dto = contactService.create(request);

        mvc.perform(put("/api/v1/core/contact/"+ dto.getId())
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void given_Id_when_DeleteContact_thenSuccess() throws Exception {

        ContactRequest request = new ContactRequest();
        request.setUrl("http://www.google.com");
        request.setName("google");

        ContactDTO contactDTO = ContactDTO.builder()
                .id(99)
                .url("url.com")
                .name("test name")
                .build();

        given(contactService.create(request)).willReturn(contactDTO);
        ContactDTO dto = contactService.create(request);

        mvc.perform(delete("/api/v1/core/contact/"+ dto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void given_void_when_getAllContact_thenSuccess() throws Exception {

        mvc.perform(get("/api/v1/core/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
