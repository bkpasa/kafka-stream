package com.bkpasa.kafkastream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bkpasa.kafkastream.domain.model.Alert;
import com.bkpasa.kafkastream.domain.service.IStreamService;
import com.bkpasa.kafkastream.domain.service.KafkaSender;

@RestController
public class AlertController {

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    IStreamService kafkaStreamService;

    @Value("${kafka.stream.tutorial.topic}")
    private String kafkaTopic;

    @RequestMapping("/alert")
    public Alert getAlert() {
        return new Alert(20L, 30L, 40L, "indicator exceeded", true, "indicator");
    }

    @RequestMapping(path = "/alerts", method = RequestMethod.GET)
    public List<Alert> getAlerts() {
        return kafkaStreamService.getAlerts();
    }

    @RequestMapping(path = "/alert", method = RequestMethod.POST)
    public Alert createAlert(@RequestBody Alert alert) {
        kafkaSender.send(kafkaTopic, alert.getId(), alert);
        return alert;
    }
}
