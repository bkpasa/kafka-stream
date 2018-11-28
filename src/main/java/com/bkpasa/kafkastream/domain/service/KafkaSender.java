package com.bkpasa.kafkastream.domain.service;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSender implements IKafkaSender {

    private final static Logger LOG = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    KafkaTemplate<Long, Object> kafkaTemplate;

    @Override
    public void send(String topic, Long key, Object value) {
        try {
            kafkaTemplate.send(topic, key, value).get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("could not send to kafka", e);
        }
    }

}
