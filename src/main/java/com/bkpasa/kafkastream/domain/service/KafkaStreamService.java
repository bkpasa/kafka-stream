package com.bkpasa.kafkastream.domain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.bkpasa.kafkastream.domain.model.Alert;
import com.bkpasa.kafkastream.serde.JsonPOJODeserializer;
import com.bkpasa.kafkastream.serde.JsonPOJOSerializer;

public class KafkaStreamService implements IStreamService {

    private final static Logger LOG = LoggerFactory.getLogger(KafkaStreamService.class);

    private static final String alertByIdStoreName = "alert-ktable-store";
    private KafkaStreams streams = null;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.stream.tutorial.topic}")
    private String kafkaTopic;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        final StreamsBuilder builder = new StreamsBuilder();
        Map<String, Object> serdeProps = new HashMap<>();

        final Serializer<Alert> alertSerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", Alert.class);
        alertSerializer.configure(serdeProps, false);

        final Deserializer<Alert> alertDeserializer = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", Alert.class);
        alertDeserializer.configure(serdeProps, false);

        final Serde<Alert> alertSerde = Serdes.serdeFrom(alertSerializer, alertDeserializer);
        builder.table(kafkaTopic, Consumed.with(Serdes.Long(), alertSerde), Materialized.as(alertByIdStoreName));

        final Topology topology = builder.build();
        streams = new KafkaStreams(topology, props);
        streams.start();
    }

    @PreDestroy
    public void cleanup() {
        if (null != streams) {
            streams.close();
        }
    }

    @Override
    public List<Alert> getAlerts() {
        final ReadOnlyKeyValueStore<Long, Alert> store =
                streams.store(alertByIdStoreName, QueryableStoreTypes.keyValueStore());
        final KeyValueIterator<Long, Alert> range = store.all();
        final List<KeyValue<Long, Alert>> results = new ArrayList<>();
        while (range.hasNext()) {
            final KeyValue<Long, Alert> next = range.next();
            results.add(next);
        }
        return results.stream().map(m -> m.value).collect(Collectors.toList());
    }

}
