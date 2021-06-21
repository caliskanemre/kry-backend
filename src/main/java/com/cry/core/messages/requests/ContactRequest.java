package com.cry.core.messages.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ContactRequest {
    @NotEmpty
    String url;
    @NotEmpty
    String name;
}