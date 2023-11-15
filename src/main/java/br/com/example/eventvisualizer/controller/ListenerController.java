package br.com.example.eventvisualizer.controller;

import br.com.example.eventvisualizer.service.KafkaListenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listener")
@RequiredArgsConstructor
public class ListenerController {
    private final KafkaListenerService listenerService;

    @PostMapping
    ResponseEntity<Object> registerListener(@RequestParam String topic) {
        listenerService.registerListener(topic);
        return ResponseEntity.ok().build();
    }

}
