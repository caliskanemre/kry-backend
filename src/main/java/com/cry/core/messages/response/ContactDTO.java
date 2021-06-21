package com.cry.core.messages.response;

import com.cry.core.domain.model.Condition;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;


@Builder
@Data
public class ContactDTO {

    long id;
    String url;
    String name;
    Condition condition;
}
