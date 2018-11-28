package com.bkpasa.kafkastream.domain.model;

public class Alert {

    Long id;
    Long eventId;
    Long marketId;

    String description;
    Boolean enabled;
    String alertType;

    // default contructor for deserialization
    public Alert() {

    }
    public Alert(Long id, Long eventId, Long marketId, String description, Boolean enabled, String alertType) {
        super();
        this.id = id;
        this.eventId = eventId;
        this.marketId = marketId;
        this.description = description;
        this.enabled = enabled;
        this.alertType = alertType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}
