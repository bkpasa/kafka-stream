package com.bkpasa.kafkastream.domain.service;

public interface IKafkaSender {
    public void send(String topic, Long key, Object value);
}
