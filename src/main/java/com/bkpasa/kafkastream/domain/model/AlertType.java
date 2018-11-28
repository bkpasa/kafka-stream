package com.bkpasa.kafkastream.domain.model;

public enum AlertType {

    LIABILITY("liability"), STAKE_DISTRIBUTION("stake_distribution");

    private String type;

    private AlertType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
