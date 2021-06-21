package com.cry.core.service.impl;

import com.cry.core.domain.model.Condition;
import com.cry.core.messages.response.ContactDTO;
import com.cry.core.repository.ContactRepository;
import com.cry.core.service.HttpConnectorService;
import com.cry.core.service.model.Contact;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HttpConnectorServiceImpl implements HttpConnectorService {

    public static final String DESTINATION = "/topic/message";
    private final OkHttpClient httpClient = new OkHttpClient();

    private final ContactRepository contactRepository;
    private final SimpMessagingTemplate template;

    public void periodicallyChecker()  {
        List<Contact> changedContacts = new ArrayList<>();
        List<Contact> list = contactRepository.findAll();
        list.forEach(contact -> getUrlMessagePeriodically(changedContacts, contact));
        changedContactsToDTO(changedContacts);
    }


    private void getUrlMessagePeriodically(List<Contact> changedContacts,  Contact contact) {
        try {
            String condition = sendGet(contact.getUrl());
            if(contact.getCondition() == null || !contact.getCondition().toString().equals(condition)) {
                contact.setCondition(Condition.valueOf(condition));
            }
            changedContacts.add(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changedContactsToDTO(List<Contact> changedContacts) {
        contactRepository.saveAll(changedContacts);
        List<ContactDTO> changedDTOs = new ArrayList<>();
        changedContacts.forEach(changedContact ->
           changedDTOs.add(ContactDTO.builder()
                .url(changedContact.getUrl())
                .name(changedContact.getName())
                .id(changedContact.getId())
                .condition(changedContact.getCondition())
                .build()
           )
        );
        template.convertAndSend(DESTINATION, changedDTOs);
    }


    private String sendGet(String url) throws IOException, JSONException {

        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONObject jsonObject =null;
        try (Response response = httpClient.newCall(request).execute()) {
            jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.getString("data");
    }
}