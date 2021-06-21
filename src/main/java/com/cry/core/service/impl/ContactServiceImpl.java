package com.cry.core.service.impl;

import com.cry.core.messages.requests.ContactRequest;
import com.cry.core.messages.response.ContactDTO;
import com.cry.core.repository.ContactRepository;
import com.cry.core.service.ContactService;
import com.cry.core.service.HttpConnectorService;
import com.cry.core.service.model.Contact;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private static final String CONTACT_ENTITY_NAME= "Contact";

    private final HttpConnectorService httpConnectorService;
    private final ContactRepository contactRepository;

    @Override
    public ContactDTO create(ContactRequest contactRequest) {

        isValidURL(contactRequest.getUrl());
        Contact contact = new Contact();
        ContactDTO contactDTO = getContactDTO(contactRequest, contact);

        httpConnectorService.periodicallyChecker();
        return contactDTO;
    }

    @Override
    public ContactDTO update(final long id, final ContactRequest contactRequest) {

        Contact contact = contactRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_ENTITY_NAME));

        ContactDTO contactDTO = getContactDTO(contactRequest, contact);
        httpConnectorService.periodicallyChecker();
        return contactDTO;
    }


    @Override
    public void delete(final long id) {

        contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_ENTITY_NAME));

        contactRepository.deleteById(id);

        httpConnectorService.periodicallyChecker();
    }

    @Override
    public List<ContactDTO> getAllContacts() {

        List<ContactDTO> contactDTOList = new ArrayList<>();
        List<Contact> contactList = contactRepository.findAll();

        for(Contact contact : contactList){
            ContactDTO dto =ContactDTO.builder()
                    .url(contact.getUrl())
                    .name(contact.getName())
                    .build();
            contactDTOList.add(dto);
        }
        return contactDTOList;
    }

    @Override
    public ContactDTO findById(final long id) {

        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_ENTITY_NAME));

        return ContactDTO.builder()
                .url(contact.getUrl())
                .name(contact.getName())
                .build();
    }

    private ContactDTO getContactDTO(ContactRequest contactRequest, Contact contact) {
        contact.setName(contactRequest.getName());
        contact.setUrl(contactRequest.getUrl());
        Contact created = contactRepository.saveAndFlush(contact);

        return ContactDTO.builder()
                .url(created.getUrl())
                .name(created.getName())
                .id(created.getId())
                .build();
    }

    @SneakyThrows
    private void isValidURL(String url) {

        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new MalformedURLException("Wrong URL entered " + e.getMessage() );
        }
    }
}
