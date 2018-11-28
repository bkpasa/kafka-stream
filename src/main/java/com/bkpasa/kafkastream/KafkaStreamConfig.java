package com.bkpasa.kafkastream;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.bkpasa.kafkastream.domain.service.IStreamService;
import com.bkpasa.kafkastream.domain.service.KafkaSender;
import com.bkpasa.kafkastream.domain.service.KafkaStreamService;
import com.bkpasa.kafkastream.serde.JsonPOJOSerializer;

@Configuration
public class KafkaStreamConfig {

    @Bean
    public IStreamService kafkaStreamService()
    {
        return new KafkaStreamService();
    }

    // kafka
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonPOJOSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<Long, Object> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }
    // end kafka

    @Bean
    public KafkaSender kafkaSender() {
        return new KafkaSender();
    }
}
