package SkillSwap.domain;

public class MatchResult {

    public String offer_id;
    public String request_id;
    public int score;
    public String motivazione;

    public MatchResult(String offer_id, String request_id, int score, String motivazione) {
        this.offer_id = offer_id;
        this.request_id = request_id;
        this.score = score;
        this.motivazione = motivazione;
    }
}