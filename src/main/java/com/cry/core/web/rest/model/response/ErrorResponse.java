package com.cry.core.web.rest.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    String requestId;
    Long timestamp;
    String code;
    String message;
    ArrayList<ApiValidationError> validationMessages;
    String trace;
}