package SkillSwap.domain;

import java.util.LinkedList;

public class Offer {

    private String offer_id;
    private String student_id;
    private String skill_id;
    private String level;
    private String note;
    private String active;

    public Offer(String offer_id, String student_id, String skill_id, String level, String note, String active) {
        this.offer_id = offer_id;
        this.student_id = student_id;
        this.skill_id = skill_id;
        this.level = level;
        this.note = note;
        this.active = active;
    }

    LinkedList<String> offer = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Studente altro) {
            return this.offer_id.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int var1 = 17;
        var1 = var1 * 31 + offer_id.hashCode();
        return var1;
    }

    @Override
    public String toString() {
        return offer_id;
    }
}
