package com.cry.core.web.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private final String message;
    private final T data;
    private Long timestamp;
    private String trace;
    // Using a map will not cover cases where a field has multiple error messages related to it!
    // MultiValueMap would be a better idea here!
    private Map<String, String> errors;

    public ApiResponse(Long timestamp, String message, Exception ex) {
        this.timestamp = timestamp;
        this.data = null;
        this.message = message;
        if (ex != null) {
            this.trace = ExceptionUtils.getStackTrace(ex);
        }

    }

    public ApiResponse(Long timestamp, String message) {
        this.timestamp = timestamp;
        this.data = null;
        this.message = message;

    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Long timestamp, String message, T data) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = data;
    }

}
