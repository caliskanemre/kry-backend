package com.cry.core.web;


import com.cry.core.messages.requests.ContactRequest;
import com.cry.core.messages.response.ContactDTO;
import com.cry.core.service.ContactService;
import com.cry.core.web.rest.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/core")
@Validated
public class ContactController {

    public static final String SUCCESS = "SUCCESS";
    public static final String DATA_DELETED = "Data deleted";



    private final ContactService contactService;

    public ContactController(
            ContactService contactService
    ){
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse<ContactDTO>> createContact(
            @Valid @RequestBody ContactRequest request
    ) {
        final ContactDTO created = contactService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>( SUCCESS, created));
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<ApiResponse<ContactDTO>> updateContact(
            @PathVariable long id,
            @Valid @RequestBody ContactRequest request
    ) {
        final ContactDTO created = contactService.update(id,request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>( SUCCESS, created));
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<ApiResponse<String>> deleteContact(
            @PathVariable long id
    ) {
        contactService.delete(id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(SUCCESS, DATA_DELETED));
    }
    
    @GetMapping("/contacts")
    public ResponseEntity<ApiResponse<List<ContactDTO>>> getContacts(
    ) {
        final List<ContactDTO> contactDTOList = contactService.getAllContacts();

        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, contactDTOList));
    }

    @GetMapping("/mockServiceOK")
    public ResponseEntity<ApiResponse<String>> mockServiceOk(
    ) {
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, "OK"));
    }


    @GetMapping("/mockServiceFail")
    public ResponseEntity<ApiResponse<String>> mockServiceFail(
    ) {
        return ResponseEntity.ok(new ApiResponse<>(SUCCESS, "FAIL"));
    }



}
