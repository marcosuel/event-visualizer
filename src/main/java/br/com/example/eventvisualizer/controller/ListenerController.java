package br.com.example.eventvisualizer.controller;

import br.com.example.eventvisualizer.controller.exchange.ListenerRequest;
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
    ResponseEntity<Object> registerListener(@RequestBody ListenerRequest request) {
        listenerService.registerListener(request);
        return ResponseEntity.ok().build();
    }

}
