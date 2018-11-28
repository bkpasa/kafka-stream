package com.bkpasa.kafkastream.domain.model;

public class AlertKey {
    private Long eventId;
    private Long marketId;

    public AlertKey() {

    }

    public AlertKey(Long eventId, Long marketId) {
        super();
        this.eventId = eventId;
        this.marketId = marketId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        result = prime * result + ((marketId == null) ? 0 : marketId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AlertKey other = (AlertKey) obj;
        if (eventId == null) {
            if (other.eventId != null) {
                return false;
            }
        } else if (!eventId.equals(other.eventId)) {
            return false;
        }
        if (marketId == null) {
            if (other.marketId != null) {
                return false;
            }
        } else if (!marketId.equals(other.marketId)) {
            return false;
        }
        return true;
    }

}
