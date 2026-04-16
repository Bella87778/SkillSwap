package SkillSwap.service;

import SkillSwap.domain.*;
import java.util.*;

public class MatchingService {

    public static class MatchResult {
        public final String offerId;
        public final String requestId;
        public final int score;
        public final String reason;

        public MatchResult(String offerId, String requestId, int score, String reason) {
            this.offerId = offerId;
            this.requestId = requestId;
            this.score = score;
            this.reason = reason;
        }

        @Override
        public String toString() {
            return "Match{"
                    + "offerId='" + offerId + '\''
                    + ", requestId='" + requestId + '\''
                    + ", score=" + score
                    + ", reason='" + reason + '\''
                    + '}';
        }
    }

    private final Map<String, Studente> studenti;
    private final Map<String, Offer> offerte;
    private final Map<String, Requests> richieste;

    public MatchingService(Map<String, Studente> studenti,
                           Map<String, Offer> offerte,
                           Map<String, Requests> richieste) {
        this.studenti = studenti;
        this.offerte = offerte;
        this.richieste = richieste;
    }

    public List<MatchResult> findOneWayMatches(String studentId) {
        List<MatchResult> risultati = new ArrayList<>();

        Studente offerente = studenti.get(studentId);
        if (offerente == null) {
            System.out.println("Studente non trovato: " + studentId);
            return risultati;
        }

        for (Offer offerta : offerte.values()) {
            if (!offerta.getStudent_id().equals(studentId)) continue;

            for (Requests richiesta : richieste.values()) {
                if (richiesta.getStudent_id().equals(studentId)) continue;

                int punteggio = 0;
                StringBuilder motivazione = new StringBuilder();

                if (offerta.getSkill_id().equals(richiesta.getSkill_id())) {
                    punteggio += 3;
                    motivazione.append("skill uguale; ");
                } else {
                    continue;
                }

                if (levelSufficiente(offerta.getLevel(), richiesta.getMin_level())) {
                    punteggio += 2;
                    motivazione.append("livello sufficiente; ");
                }

                Studente richiedente = studenti.get(richiesta.getStudent_id());
                if (richiedente != null && offerente.getClasse().equals(richiedente.getClasse())) {
                    punteggio += 1;
                    motivazione.append("stessa classe; ");
                }

                risultati.add(new MatchResult(
                        offerta.getOffer_id(),
                        richiesta.getRequest_id(),
                        punteggio,
                        motivazione.toString().trim()
                ));
            }
        }

        risultati.sort((a, b) -> b.score - a.score);
        return risultati;
    }

    public List<MatchResult> findSwapMatches(String studentId) {
        List<MatchResult> risultati = new ArrayList<>();

        if (!studenti.containsKey(studentId)) {
            System.out.println("Studente non trovato: " + studentId);
            return risultati;
        }

        for (Offer offertaA : offerte.values()) {
            if (!offertaA.getStudent_id().equals(studentId)) continue;

            for (Requests richiestaA : richieste.values()) {
                if (!richiestaA.getStudent_id().equals(studentId)) continue;

                for (Offer offertaB : offerte.values()) {
                    String studentB = offertaB.getStudent_id();
                    if (studentB.equals(studentId)) continue;

                    if (!offertaB.getSkill_id().equals(richiestaA.getSkill_id())) continue;

                    for (Requests richiestaB : richieste.values()) {
                        if (!richiestaB.getStudent_id().equals(studentB)) continue;
                        if (!richiestaB.getSkill_id().equals(offertaA.getSkill_id())) continue;

                        int scoreAB = calcolaScore(offertaA, richiestaB,
                                studenti.get(studentId), studenti.get(studentB));

                        int scoreBA = calcolaScore(offertaB, richiestaA,
                                studenti.get(studentB), studenti.get(studentId));

                        if (scoreAB > 0 && scoreBA > 0) {
                            int totale = scoreAB + scoreBA;
                            String reason = "SWAP: A offre " + offertaA.getSkill_id()
                                    + " a B (score " + scoreAB + "), "
                                    + "B offre " + offertaB.getSkill_id()
                                    + " a A (score " + scoreBA + ")";
                            risultati.add(new MatchResult(
                                    offertaA.getOffer_id(),
                                    richiestaA.getRequest_id(),
                                    totale,
                                    reason
                            ));
                        }
                    }
                }
            }
        }

        risultati.sort((a, b) -> b.score - a.score);
        return risultati;
    }

    private int calcolaScore(Offer offerta, Requests richiesta,
                              Studente offerente, Studente richiedente) {
        if (!offerta.getSkill_id().equals(richiesta.getSkill_id())) return 0;

        int punteggio = 3;
        if (levelSufficiente(offerta.getLevel(), richiesta.getMin_level())) punteggio += 2;
        if (offerente != null && richiedente != null
                && offerente.getClasse().equals(richiedente.getClasse())) punteggio += 1;
        return punteggio;
    }

    private boolean levelSufficiente(String offerLevel, String minLevel) {
        Map<String, Integer> ordine = new HashMap<>();
        ordine.put("beginner", 1);
        ordine.put("intermediate", 2);
        ordine.put("advanced", 3);
        ordine.put("expert", 4);

        int offer = ordine.getOrDefault(offerLevel.toLowerCase(), 0);
        int min   = ordine.getOrDefault(minLevel.toLowerCase(), 0);
        return offer >= min;
    }
}