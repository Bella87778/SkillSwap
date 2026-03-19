package SkillSwap.domain;

public class Requests {

    private String request_id;
    private String student_id;
    private String skill_id;
    private String min_level;
    private String note;

    public Requests(String request_id, String student_id, String skill_id, String min_level, String note) {
        this.request_id = request_id;
        this.student_id = student_id;
        this.skill_id = skill_id;
        this.min_level = min_level;
        this.note = note;
    }

    public String getID() {
        return request_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requests altro)) {
            return false;
        }
        return this.request_id.equals(altro.request_id);
    }

    @Override
    public int hashCode() {
        return request_id.hashCode();
    }

    @Override
    public String toString() {
        return "Request{"
                + "id='" + request_id + '\''
                + ", student_id='" + student_id + '\''
                + ", skill_id='" + skill_id + '\''
                + ", min_level='" + min_level + '\''
                + ", note='" + note + '\''
                + '}';
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
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

    public String getMin_level() {
        return min_level;
    }

    public void setMin_level(String min_level) {
        this.min_level = min_level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
