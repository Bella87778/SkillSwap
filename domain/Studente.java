package SkillSwap.domain;

import java.util.LinkedList;

public class Studente {
    
    private String nome;
    private String student_id;
    private String classe;
    private String email;
    private int rating_avg;
    private int rating_count;

    public Studente(String nome, String student_id,String classe, String email, int rating_avg, int rating_count){
        this.nome = nome;
        this.student_id = student_id;
        this.classe = classe;
        this.email = email;
        this.rating_avg = rating_avg;
        this.rating_count = rating_count;
    }

    LinkedList <String> studenti = new LinkedList<>();

    @Override
    public boolean equals(Object o){
        if(o instanceof Studente altro){
            return this.student_id.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int var1 = 17;
        var1 = var1 * 31 + student_id.hashCode();
        return var1;
    }

    @Override
    public String toString(){
        return nome + student_id;
    }
}
