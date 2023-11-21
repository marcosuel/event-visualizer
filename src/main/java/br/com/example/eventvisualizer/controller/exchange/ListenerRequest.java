package br.com.example.eventvisualizer.controller.exchange;

public record ListenerRequest(
        String topic,
        String groupId,
        String bootstrapServers,
        String schemaRegistry,
        boolean updateConfigs
) {
}
