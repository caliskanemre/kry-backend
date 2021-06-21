package com.cry.core.service;


import com.cry.core.messages.requests.ContactRequest;
import com.cry.core.messages.response.ContactDTO;

import java.util.List;

public interface ContactService {

    ContactDTO create(final ContactRequest contactRequest);

    ContactDTO update(final long id, final ContactRequest contactRequest);

    void delete(final long id);

    List<ContactDTO> getAllContacts();

    ContactDTO findById(final long id);

}