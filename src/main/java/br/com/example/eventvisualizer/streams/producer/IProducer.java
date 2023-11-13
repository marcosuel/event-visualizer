package br.com.example.eventvisualizer.streams.producer;

public interface IProducer<T> {
    void produceEvent(T event);
}
