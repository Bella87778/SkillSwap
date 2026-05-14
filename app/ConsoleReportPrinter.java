package SkillSwap.app;
 
import SkillSwap.domain.*;
import SkillSwap.service.*;
import java.util.List;
import java.util.Map;
 
public class ConsoleReportPrinter {
 
    private static final int WIDTH        = 55;
    private static final char BORDER_CHAR = '=';
    private static final char SEP_CHAR    = '-';
 
    private final Map<String, Studente>  studenti;
    private final Map<String, Skills>    skills;
    private final Map<String, Offer>     offerte;
    private final Map<String, Requests>  richieste;
    private final Map<String, Exchanges> scambi;
    private final Map<String, Reviews>   recensioni;
 
    public ConsoleReportPrinter(Map<String, Studente>  studenti,
                                Map<String, Skills>    skills,
                                Map<String, Offer>     offerte,
                                Map<String, Requests>  richieste,
                                Map<String, Exchanges> scambi,
                                Map<String, Reviews>   recensioni) {
        this.studenti    = studenti;
        this.skills      = skills;
        this.offerte     = offerte;
        this.richieste   = richieste;
        this.scambi      = scambi;
        this.recensioni  = recensioni;
    }

    public void printStudentProfile(String studentId) {
        Studente s = studenti.get(studentId);
        if (s == null) {
            System.out.println("[ERRORE] Studente non trovato: " + studentId);
            return;
        }
 
        StringBuilder sb = new StringBuilder();
 
        sb.append(border(BORDER_CHAR)).append('\n');
        sb.append(centrato("PROFILO STUDENTE")).append('\n');
        sb.append(border(BORDER_CHAR)).append('\n');
 
        sb.append(riga("Nome",    s.getNome())).append('\n');
        sb.append(riga("ID",      s.getID())).append('\n');
        sb.append(riga("Classe",  s.getClasse())).append('\n');
        sb.append(riga("Email",   s.getEmail())).append('\n');
        sb.append(riga("Rating",  stelline(s.getRatingAvg())
                + " (" + s.getRatingAvg() + "/5, "
                + s.getRatingCount() + " voti)")).append('\n');
 
        sb.append(border(SEP_CHAR)).append('\n');
        sb.append(centrato("OFFERTE")).append('\n');
        sb.append(border(SEP_CHAR)).append('\n');
 
        boolean haOfferte = false;
        for (Offer o : offerte.values()) {
            if (!o.getStudent_id().equals(studentId)) continue;
            haOfferte = true;
            String nomeSkill = nomeSkill(o.getSkill_id());
            String stato     = "true".equalsIgnoreCase(o.getActive()) ? "[ATTIVA]" : "[NON ATTIVA]";
            sb.append("  ").append(o.getOffer_id()).append("  ")
              .append(nomeSkill).append("  livello: ").append(o.getLevel())
              .append("  ").append(stato).append('\n');
            if (o.getNote() != null && !o.getNote().isBlank()) {
                sb.append("      nota: ").append(o.getNote()).append('\n');
            }
        }
        if (!haOfferte) sb.append("  (nessuna offerta)\n");
 
        sb.append(border(SEP_CHAR)).append('\n');
        sb.append(centrato("RICHIESTE")).append('\n');
        sb.append(border(SEP_CHAR)).append('\n');
 
        boolean haRichieste = false;
        for (Requests r : richieste.values()) {
            if (!r.getStudent_id().equals(studentId)) continue;
            haRichieste = true;
            String nomeSkill = nomeSkill(r.getSkill_id());
            sb.append("  ").append(r.getRequest_id()).append("  ")
              .append(nomeSkill).append("  min livello: ").append(r.getMin_level()).append('\n');
            if (r.getNote() != null && !r.getNote().isBlank()) {
                sb.append("      nota: ").append(r.getNote()).append('\n');
            }
        }
        if (!haRichieste) sb.append("  (nessuna richiesta)\n");
 
        sb.append(border(SEP_CHAR)).append('\n');
        sb.append(centrato("RECENSIONI RICEVUTE")).append('\n');
        sb.append(border(SEP_CHAR)).append('\n');
 
        boolean haRecensioni = false;
        for (Reviews rv : recensioni.values()) {
            if (!rv.getRevieweeStudentId().equals(studentId)) continue;
            haRecensioni = true;
            Studente reviewer = studenti.get(rv.getReviewerStudentId());
            String da = reviewer != null ? reviewer.getNome() : rv.getReviewerStudentId();
            sb.append("  ").append(stelline(rv.getStars()))
              .append(" da ").append(da)
              .append("  [").append(rv.getExchangeId()).append(']').append('\n');
            if (rv.getComment() != null && !rv.getComment().isBlank()) {
                sb.append("      \"").append(rv.getComment()).append("\"\n");
            }
        }
        if (!haRecensioni) sb.append("  (nessuna recensione ricevuta)\n");
 
        sb.append(border(BORDER_CHAR));
        System.out.println(sb);
    }

    public void printMatches(String studentId,
                             List<MatchingService.MatchResult> matches,
                             String tipoMatch) {
        StringBuilder sb = new StringBuilder();
 
        Studente s = studenti.get(studentId);
        String nomeStudente = s != null ? s.getNome() : studentId;
 
        sb.append(border(BORDER_CHAR)).append('\n');
        sb.append(centrato("MATCH " + tipoMatch + " — " + nomeStudente)).append('\n');
        sb.append(border(BORDER_CHAR)).append('\n');
 
        if (matches == null || matches.isEmpty()) {
            sb.append("  Nessun match trovato.\n");
        } else {
            int pos = 1;
            for (MatchingService.MatchResult m : matches) {
                sb.append("  #").append(pos++).append('\n');
                sb.append("      Offerta  : ").append(m.offerId)
                  .append("  (").append(nomeSkillDaOfferta(m.offerId)).append(')').append('\n');
                sb.append("      Richiesta: ").append(m.requestId)
                  .append("  (").append(nomeSkillDaRichiesta(m.requestId)).append(')').append('\n');
                sb.append("      Score    : ").append(m.score).append('\n');
                sb.append("      Motivo   : ").append(m.reason).append('\n');
                sb.append(border(SEP_CHAR)).append('\n');
            }
        }
 
        sb.append(border(BORDER_CHAR));
        System.out.println(sb);
    }
 
    public void printExchangeDetails(String exchangeId) {
        Exchanges ex = scambi.get(exchangeId);
        if (ex == null) {
            System.out.println("[ERRORE] Exchange non trovato: " + exchangeId);
            return;
        }
 
        Offer offerta       = offerte.get(ex.getOffer_id());
        Requests richiesta  = richieste.get(ex.getRequest_id());
 
        String idOfferente  = offerta  != null ? offerta.getStudent_id()  : "?";
        String idRichiedente = richiesta != null ? richiesta.getStudent_id() : "?";
 
        Studente offerente   = studenti.get(idOfferente);
        Studente richiedente = studenti.get(idRichiedente);
 
        String nomeOfferente   = offerente   != null ? offerente.getNome()   : idOfferente;
        String nomeRichiedente = richiedente != null ? richiedente.getNome() : idRichiedente;
 
        StringBuilder sb = new StringBuilder();
 
        sb.append(border(BORDER_CHAR)).append('\n');
        sb.append(centrato("DETTAGLIO SCAMBIO " + exchangeId)).append('\n');
        sb.append(border(BORDER_CHAR)).append('\n');
 
        sb.append(riga("Stato",      badgeStato(ex.getStatus()))).append('\n');
        sb.append(riga("Offerta",    ex.getOffer_id()
                + "  (" + nomeSkillDaOfferta(ex.getOffer_id()) + "  — " + nomeOfferente + ')')).append('\n');
        sb.append(riga("Richiesta",  ex.getRequest_id()
                + "  (" + nomeSkillDaRichiesta(ex.getRequest_id()) + "  — " + nomeRichiedente + ')')).append('\n');
        sb.append(riga("Creato il",  ex.getCreated_at() != null
                ? ex.getCreated_at().toString() : "—")).append('\n');
        sb.append(riga("Chiuso il",  ex.getClosed_at() != null
                ? ex.getClosed_at().toString() : "—")).append('\n');
 
        sb.append(border(SEP_CHAR)).append('\n');
        sb.append(centrato("RECENSIONI")).append('\n');
        sb.append(border(SEP_CHAR)).append('\n');
 
        boolean haRec = false;
        for (Reviews rv : recensioni.values()) {
            if (!rv.getExchangeId().equals(exchangeId)) continue;
            haRec = true;
            Studente reviewer = studenti.get(rv.getReviewerStudentId());
            Studente reviewee = studenti.get(rv.getRevieweeStudentId());
            String da  = reviewer != null ? reviewer.getNome() : rv.getReviewerStudentId();
            String per = reviewee != null ? reviewee.getNome() : rv.getRevieweeStudentId();
            sb.append("  ").append(stelline(rv.getStars()))
              .append(" da ").append(da).append(" a ").append(per).append('\n');
            if (rv.getComment() != null && !rv.getComment().isBlank()) {
                sb.append("      \"").append(rv.getComment()).append("\"\n");
            }
        }
        if (!haRec) sb.append("  (nessuna recensione)\n");
 
        sb.append(border(BORDER_CHAR));
        System.out.println(sb);
    }

    public void printLeaderboard() {
        StringBuilder sb = new StringBuilder();
 
        sb.append(border(BORDER_CHAR)).append('\n');
        sb.append(centrato("CLASSIFICA STUDENTI")).append('\n');
        sb.append(border(BORDER_CHAR)).append('\n');
 
        sb.append(String.format("  %-4s %-20s %-8s %-6s %s%n",
                "Pos", "Nome", "Classe", "Voti", "Rating"));
        sb.append(border(SEP_CHAR)).append('\n');
 
        List<Studente> classifica = studenti.values().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getRatingAvg(), a.getRatingAvg());
                    return cmp != 0 ? cmp : Integer.compare(b.getRatingCount(), a.getRatingCount());
                })
                .toList();
 
        if (classifica.isEmpty()) {
            sb.append("  (nessuno studente registrato)\n");
        } else {
            int pos = 1;
            for (Studente s : classifica) {
                String medaglia = switch (pos) {
                    case 1 -> "🥇";
                    case 2 -> "🥈";
                    case 3 -> "🥉";
                    default -> "  ";
                };
                sb.append(String.format("  %s%-2d %-20s %-8s %-6d %s%n",
                        medaglia,
                        pos++,
                        s.getNome(),
                        s.getClasse(),
                        s.getRatingCount(),
                        stelline(s.getRatingAvg()) + " (" + s.getRatingAvg() + "/5)"));
            }
        }
 
        sb.append(border(BORDER_CHAR));
        System.out.println(sb);
    }

    private String border(char c) {
        return String.valueOf(c).repeat(WIDTH);
    }
 
    private String centrato(String testo) {
        int padding = Math.max(0, (WIDTH - testo.length()) / 2);
        return " ".repeat(padding) + testo;
    }
 
    private String riga(String etichetta, String valore) {
        return String.format("  %-13s: %s", etichetta, valore);
    }
 
    private String stelline(int n) {
        int stelle = Math.max(0, Math.min(5, n));
        return "★".repeat(stelle) + "☆".repeat(5 - stelle);
    }
 
    private String badgeStato(String stato) {
        return switch (stato.toUpperCase()) {
            case "PROPOSED"  -> "[PROPOSTO]";
            case "ACCEPTED"  -> "[ACCETTATO]";
            case "COMPLETED" -> "[COMPLETATO]";
            case "CANCELLED" -> "[ANNULLATO]";
            default          -> "[" + stato + "]";
        };
    }
 
    private String nomeSkillDaOfferta(String offerId) {
        Offer o = offerte.get(offerId);
        if (o == null) return offerId;
        return nomeSkill(o.getSkill_id());
    }
 
    private String nomeSkillDaRichiesta(String requestId) {
        Requests r = richieste.get(requestId);
        if (r == null) return requestId;
        return nomeSkill(r.getSkill_id());
    }
 
    private String nomeSkill(String skillId) {
        Skills sk = skills.get(skillId);
        return sk != null ? sk.getName() : skillId;
    }
}