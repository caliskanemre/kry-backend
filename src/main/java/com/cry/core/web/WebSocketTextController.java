package com.cry.core.web;

import com.cry.core.messages.response.ContactDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebSocketTextController {

    private final SimpMessagingTemplate template;

    //We are using websocket just for sending message to client
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody List<ContactDTO> contactDTOList) {
        template.convertAndSend("/topic/message", contactDTOList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}