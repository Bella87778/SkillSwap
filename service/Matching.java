package SkillSwap.service;

import SkillSwap.domain.*;
import java.util.*;


public class Matching {

    private boolean levelSufficiente(int livelloOfferta, int livelloRichiesto) {
    return livelloOfferta >= livelloRichiesto;
    }

    public  String offer_id;
    public  String request_id;
    public  int score;

    public Matching(String offer_id, String request_id, int score){

        this.offer_id = offer_id;
        this.request_id = request_id;
        this.score = score;

    }
    
    @Override
    public String toString(){
        return "Match:"
                      + "offer_id:'" + offer_id + '\''
                      + "request_id:'" + request_id + '\''
                      + "score:'" + score + '\'';
    }

    private  Map<String, Studente> studenti;
    private  Map<String, Offer> offerte;
    private  Map<String, Requests> richieste;

    public Matching(Map<String, Studente> studenti, Map<String, Offer> offerte, Map<String, Requests> richieste){

        this.studenti = studenti;
        this.offerte = offerte;
        this.richieste = richieste;

    }

    public List<MatchResult> findOneWaymatches(String student_id){
        List<MatchResult> risultati = new ArrayList<>();
        
        Studente offerente = studenti.get(student_id);
        if(offerente == null){
            System.out.println("Studente non trovato: "+ student_id);
        return risultati;
        }
        for(Offer offerta : offerte.values()){
            if(!offerta.getStudent_id().equals(student_id)) continue;
                for(Requests richiesta : richieste.values()){
                    if (richiesta.getStudent_id().equals(student_id)) continue;

                    int punteggio = 0;
                    StringBuilder motivazione = new StringBuilder();

                    if(offerta.getSkill_id().equals(richiesta.getSkill_id())){
                        punteggio = punteggio + 3;
                        motivazione.append("Skill uguale");
                    }else{
                        continue;
                    }
                    if (levelSufficiente(
                        Integer.parseInt(offerta.getLevel()),
                        Integer.parseInt(richiesta.getMin_level())
                        )) {
                            punteggio += 2;
                    }

                    Studente richiedente = studenti.get(richiesta.getStudent_id());
                    if (richiedente != null && offerente.getClasse().equals(richiedente.getClasse())) {
                        
                        punteggio = punteggio + 1;

                        motivazione.append("Stessa classe");

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


}
