package com.bkpasa.kafkastream.domain.service;

import java.util.List;

import com.bkpasa.kafkastream.domain.model.Alert;

public interface IStreamService {

    public List<Alert> getAlerts();
}
