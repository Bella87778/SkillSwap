package SkillSwap.domain;

import java.time.LocalDateTime;

public class Exchanges {

    private String exchange_id;
    private String offer_id;
    private String request_id;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime closed_at;

    public Exchanges(String exchange_id, String offer_id, String request_id, String status, LocalDateTime created_at,
            LocalDateTime closed_at) {
        this.exchange_id = exchange_id;
        this.offer_id = offer_id;
        this.request_id = request_id;
        this.status = status;
        this.created_at = created_at;
        this.closed_at = closed_at;
    }

    public String getID() {
        return exchange_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exchanges altro)) {
            return false;
        }
        return this.exchange_id.equals(altro.exchange_id);
    }

    @Override
    public int hashCode() {
        return exchange_id.hashCode();
    }

    @Override
    public String toString() {
        return "Exchange{"
                + "id='" + exchange_id + '\''
                + ", offer_id='" + offer_id + '\''
                + ", request_id='" + request_id + '\''
                + ", status='" + status + '\''
                + ", created_at=" + created_at
                + ", closed_at=" + closed_at
                + '}';
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(LocalDateTime closed_at) {
        this.closed_at = closed_at;
    }
}
