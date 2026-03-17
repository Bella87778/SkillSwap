package Skillswap.domain;

import java.time.LocalDateTime;
import java.util.LinkedList;

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

    LinkedList<String> exchanges = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Studente altro) {
            return this.exchange_id.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int var1 = 17;
        var1 = var1 * 31 + exchange_id.hashCode();
        return var1;
    }

    @Override

    public String toString() {
        return exchange_id;
    }
}
