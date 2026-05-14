package SkillSwap.storage;

import SkillSwap.domain.Reviews;
import SkillSwap.domain.Skills;
import SkillSwap.domain.Studente;
import SkillSwap.service.Exchanges;
import SkillSwap.service.Offer;
import SkillSwap.service.Requests;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileStorage {

    private static final String FILE_STUDENTS  = "students.csv";
    private static final String FILE_SKILLS    = "skills.csv";
    private static final String FILE_OFFERS    = "offers.csv";
    private static final String FILE_REQUESTS  = "requests.csv";
    private static final String FILE_EXCHANGES = "exchanges.csv";
    private static final String FILE_REVIEWS   = "reviews.csv";

    private final Path dataDir;

    public FileStorage(Path dataDir) {
        this.dataDir = dataDir;
    }

    public FileStorage(String dataDir) {
        this(Paths.get(dataDir));
    }

    public SkillSwapState load() {
        Map<String, Studente>  studenti   = loadStudents();
        Map<String, Skills>    skills     = loadSkills();
        Map<String, Offer>     offerte    = loadOffers(studenti, skills);
        Map<String, Requests>  richieste  = loadRequests(studenti, skills);
        Map<String, Exchanges> scambi     = loadExchanges(offerte, richieste);
        Map<String, Reviews>   recensioni = loadReviews(scambi, studenti);

        return new SkillSwapState(studenti, skills, offerte, richieste, scambi, recensioni);
    }

    private Map<String, Studente> loadStudents() {
        Map<String, Studente> map = new LinkedHashMap<>();
        for (String[] f : leggiRighe(FILE_STUDENTS, 6)) {
            try {
                String id          = f[0];
                String nome        = f[1];
                String classe      = f[2];
                String email       = f[3];
                int    ratingAvg   = parseIntSafe(f[4]);
                int    ratingCount = parseIntSafe(f[5]);
                map.put(id, new Studente(nome, id, classe, email, ratingAvg, ratingCount));
            } catch (Exception e) {
                warn("students.csv", f, e);
            }
        }
        return map;
    }

    private Map<String, Skills> loadSkills() {
        Map<String, Skills> map = new LinkedHashMap<>();
        for (String[] f : leggiRighe(FILE_SKILLS, 3)) {
            try {
                map.put(f[0], new Skills(f[0], f[1], f[2]));
            } catch (Exception e) {
                warn("skills.csv", f, e);
            }
        }
        return map;
    }

    private Map<String, Offer> loadOffers(Map<String, Studente> studenti,
                                           Map<String, Skills>   skills) {
        Map<String, Offer> map = new LinkedHashMap<>();
        for (String[] f : leggiRighe(FILE_OFFERS, 6)) {
            try {
                String offerId    = f[0];
                String studentId  = f[1];
                String skillId    = f[2];
                String level      = f[3];
                String note       = f[4];
                String active     = f[5];

                if (!studenti.containsKey(studentId))
                    System.err.println("[WARN] offers.csv: studente " + studentId + " non trovato");
                if (!skills.containsKey(skillId))
                    System.err.println("[WARN] offers.csv: skill " + skillId + " non trovata");

                map.put(offerId, new Offer(offerId, studentId, skillId, level, note, active));
            } catch (Exception e) {
                warn("offers.csv", f, e);
            }
        }
        return map;
    }

    private Map<String, Requests> loadRequests(Map<String, Studente> studenti,
                                                Map<String, Skills>   skills) {
        Map<String, Requests> map = new LinkedHashMap<>();
        for (String[] f : leggiRighe(FILE_REQUESTS, 5)) {
            try {
                String requestId = f[0];
                String studentId = f[1];
                String skillId   = f[2];
                String minLevel  = f[3];
                String note      = f[4];

                if (!studenti.containsKey(studentId))
                    System.err.println("[WARN] requests.csv: studente " + studentId + " non trovato");
                if (!skills.containsKey(skillId))
                    System.err.println("[WARN] requests.csv: skill " + skillId + " non trovata");

                map.put(requestId, new Requests(requestId, studentId, skillId, minLevel, note));
            } catch (Exception e) {
                warn("requests.csv", f, e);
            }
        }
        return map;
    }

    private Map<String, Exchanges> loadExchanges(Map<String, Offer>    offerte,
                                                  Map<String, Requests> richieste) {
        Map<String, Exchanges> map = new LinkedHashMap<>();
        for (String[] f : leggiRighe(FILE_EXCHANGES, 6)) {
            try {
                String exchangeId = f[0];
                String offerId    = f[1];
                String requestId  = f[2];
                String status     = f[3];
                LocalDateTime createdAt = parseDateTimeSafe(f[4]);
                LocalDateTime closedAt  = parseDateTimeSafe(f[5]);

                if (!offerte.containsKey(offerId))
                    System.err.println("[WARN] exchanges.csv: offerta " + offerId + " non trovata");
                if (!richieste.containsKey(requestId))
                    System.err.println("[WARN] exchanges.csv: richiesta " + requestId + " non trovata");

                map.put(exchangeId,
                        new Exchanges(exchangeId, offerId, requestId, status, createdAt, closedAt));
            } catch (Exception e) {
                warn("exchanges.csv", f, e);
            }
        }
        return map;
    }

    private Map<String, Reviews> loadReviews(Map<String, Exchanges> scambi,
                                              Map<String, Studente>  studenti) {
        Map<String, Reviews> map = new LinkedHashMap<>();
        for (String[] f : leggiRighe(FILE_REVIEWS, 7)) {
            try {
                String reviewId    = f[0];
                String exchangeId  = f[1];
                String reviewerId  = f[2];
                String revieweeId  = f[3];
                int    stars       = parseIntSafe(f[4]);
                String comment     = f[5];
                LocalDateTime createdAt = parseDateTimeSafe(f[6]);

                if (!scambi.containsKey(exchangeId))
                    System.err.println("[WARN] reviews.csv: exchange " + exchangeId + " non trovato");
                if (!studenti.containsKey(reviewerId))
                    System.err.println("[WARN] reviews.csv: reviewer " + reviewerId + " non trovato");
                if (!studenti.containsKey(revieweeId))
                    System.err.println("[WARN] reviews.csv: reviewee " + revieweeId + " non trovato");

                map.put(reviewId,
                        new Reviews(reviewId, exchangeId, reviewerId, revieweeId, stars, comment, createdAt));
            } catch (Exception e) {
                warn("reviews.csv", f, e);
            }
        }
        return map;
    }

    public void save(SkillSwapState state) {
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile creare la cartella dati: " + dataDir, e);
        }

        saveStudents(state.getStudenti());
        saveSkills(state.getSkills());
        saveOffers(state.getOfferte());
        saveRequests(state.getRichieste());
        saveExchanges(state.getScambi());
        saveReviews(state.getRecensioni());
    }

    private void saveStudents(Map<String, Studente> map) {
        List<String> righe = new ArrayList<>();
        for (Studente s : map.values()) {
            righe.add(join(s.getID(), s.getNome(), s.getClasse(),
                    s.getEmail(), s.getRatingAvg(), s.getRatingCount()));
        }
        scriviAtomico(FILE_STUDENTS, righe);
    }

    private void saveSkills(Map<String, Skills> map) {
        List<String> righe = new ArrayList<>();
        for (Skills sk : map.values()) {
            righe.add(join(sk.getID(), sk.getName(), sk.getCategory()));
        }
        scriviAtomico(FILE_SKILLS, righe);
    }

    private void saveOffers(Map<String, Offer> map) {
        List<String> righe = new ArrayList<>();
        for (Offer o : map.values()) {
            righe.add(join(o.getOffer_id(), o.getStudent_id(), o.getSkill_id(),
                    o.getLevel(), o.getNote(), o.getActive()));
        }
        scriviAtomico(FILE_OFFERS, righe);
    }

    private void saveRequests(Map<String, Requests> map) {
        List<String> righe = new ArrayList<>();
        for (Requests r : map.values()) {
            righe.add(join(r.getRequest_id(), r.getStudent_id(), r.getSkill_id(),
                    r.getMin_level(), r.getNote()));
        }
        scriviAtomico(FILE_REQUESTS, righe);
    }

    private void saveExchanges(Map<String, Exchanges> map) {
        List<String> righe = new ArrayList<>();
        for (Exchanges ex : map.values()) {
            righe.add(join(ex.getExchangeId(), ex.getOffer_id(), ex.getRequest_id(),
                    ex.getStatus(),
                    ex.getCreated_at() != null ? ex.getCreated_at().toString() : "",
                    ex.getClosed_at()  != null ? ex.getClosed_at().toString()  : ""));
        }
        scriviAtomico(FILE_EXCHANGES, righe);
    }

    private void saveReviews(Map<String, Reviews> map) {

        List<String> righe = new ArrayList<>();
        for (Reviews rv : map.values()) {
            righe.add(join(rv.getID(), rv.getExchangeId(),
                    rv.getReviewerStudentId(), rv.getRevieweeStudentId(),
                    rv.getStars(), rv.getComment(),
                    rv.getCreatedAt() != null ? rv.getCreatedAt().toString() : ""));
        }
        scriviAtomico(FILE_REVIEWS, righe);
    }

    private List<String[]> leggiRighe(String nomeFile, int minCampi) {
        Path path = dataDir.resolve(nomeFile);
        List<String[]> risultato = new ArrayList<>();

        if (!Files.exists(path)) return risultato;

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] campi = linea.split(";", -1);
                if (campi.length < minCampi) {
                    System.err.println("[WARN] " + nomeFile
                            + ": riga ignorata (attesi " + minCampi
                            + " campi, trovati " + campi.length + "): " + linea);
                    continue;
                }
                risultato.add(campi);
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Impossibile leggere " + nomeFile + ": " + e.getMessage());
        }

        return risultato;
    }

    private void scriviAtomico(String nomeFile, List<String> righe) {
        Path target = dataDir.resolve(nomeFile);
        Path tmp    = dataDir.resolve(nomeFile + ".tmp");

        try (BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String riga : righe) {
                bw.write(riga);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore scrittura su " + tmp + ": " + e.getMessage(), e);
        }

        try {
            Files.move(tmp, target,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ex) {
            try {
                Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e2) {
                throw new RuntimeException("Errore rename " + tmp + " → " + target, e2);
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore rename " + tmp + " → " + target, e);
        }
    }

    private String join(Object... valori) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valori.length; i++) {
            if (i > 0) sb.append(';');
            sb.append(valori[i] != null ? valori[i] : "");
        }
        return sb.toString();
    }

    private int parseIntSafe(String s) {
        if (s == null || s.isBlank()) return 0;
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    private LocalDateTime parseDateTimeSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try { return LocalDateTime.parse(s.trim()); }
        catch (Exception e) { return null; }
    }

    private void warn(String file, String[] campi, Exception e) {
        System.err.println("[WARN] " + file + ": riga ignorata → "
                + String.join(";", campi) + " — " + e.getMessage());
    }
}