package SkillSwap.service;

import java.time.LocalDateTime;
import java.util.Map;



public class ExchangeService {

    public static final String PROPOSED  = "PROPOSED";
    public static final String ACCEPTED  = "ACCEPTED";
    public static final String COMPLETED = "COMPLETED";
    public static final String CANCELLED = "CANCELLED";

    private final Map<String, Offer> offerte;
    private final Map<String, Requests> richieste;
    private final Map<String, Exchanges> scambi;

    public ExchangeService(Map<String, Offer> offerte,
                           Map<String, Requests> richieste,
                           Map<String, Exchanges> scambi) {
        this.offerte = offerte;
        this.richieste = richieste;
        this.scambi = scambi;
    }

    public Exchanges propose(String offerId, String requestId) {
        Offer offerta = offerte.get(offerId);
        if (offerta == null) {
            throw new IllegalArgumentException("Offerta non trovata: " + offerId);
        }

        Requests richiesta = richieste.get(requestId);
        if (richiesta == null) {
            throw new IllegalArgumentException("Richiesta non trovata: " + requestId);
        }

        if (!"true".equalsIgnoreCase(offerta.getActive())) {
            throw new IllegalStateException(
                "L'offerta " + offerId + " non e' attiva.");
        }

        if (offerta.getStudent_id().equals(richiesta.getStudent_id())) {
            throw new IllegalArgumentException(
                "Lo studente non puo' aprire uno scambio con se stesso.");
        }

        String exchangeId = generaId();
        Exchanges scambio = new Exchanges(
                exchangeId,
                offerId,
                requestId,
                PROPOSED,
                LocalDateTime.now(),
                null
        );

        scambi.put(exchangeId, scambio);
        return scambio;
    }

    public void accept(String exchangeId) {
        Exchanges scambio = getExchangeOException(exchangeId);
        verificaTransizione(scambio.getStatus(), PROPOSED, ACCEPTED);
        scambio.setStatus(ACCEPTED);
    }

    public void complete(String exchangeId) {
        Exchanges scambio = getExchangeOException(exchangeId);
        verificaTransizione(scambio.getStatus(), ACCEPTED, COMPLETED);
        scambio.setStatus(COMPLETED);
        scambio.setClosed_at(LocalDateTime.now());
    }

    public void cancel(String exchangeId) {
        Exchanges scambio = getExchangeOException(exchangeId);
        verificaTransizione(scambio.getStatus(), PROPOSED, CANCELLED);
        scambio.setStatus(CANCELLED);
        scambio.setClosed_at(LocalDateTime.now());
    }

    private Exchanges getExchangeOException(String exchangeId) {
        Exchanges scambio = scambi.get(exchangeId);
        if (scambio == null) {
            throw new IllegalArgumentException("Exchange non trovato: " + exchangeId);
        }
        return scambio;
    }

    private void verificaTransizione(String statoAttuale,
                                     String statoRichiesto,
                                     String statoDestino) {
        if (!statoAttuale.equals(statoRichiesto)) {
            throw new IllegalStateException(
                "Transizione non valida: stato attuale '" + statoAttuale
                + "', serve '" + statoRichiesto
                + "' per andare a " + statoDestino + ".");
        }
    }

    private String generaId() {
        int max = 0;
        for (String id : scambi.keySet()) {
            if (id.startsWith("E")) {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) {
                        max = n;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return "E" + (max + 1);
    }
}