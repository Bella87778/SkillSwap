package SkillSwap.domain;

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

    public String getID() {
        return offer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer altro)) {
            return false;
        }
        return this.offer_id.equals(altro.offer_id);
    }

    @Override
    public int hashCode() {
        return offer_id.hashCode();
    }

    @Override
    public String toString() {
        return "Offer{"
                + "id='" + offer_id + '\''
                + ", student_id='" + student_id + '\''
                + ", skill_id='" + skill_id + '\''
                + ", level='" + level + '\''
                + ", note='" + note + '\''
                + ", active='" + active + '\''
                + '}';
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(String skill_id) {
        this.skill_id = skill_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
