package Skillswap.domain;

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
    @Override
    public boolean equals(Object o){
        if(o instanceof Studente altro){
            return this.request_id.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int var1 = 17;
        var1 = var1 * 31 + request_id.hashCode();
        return var1;
    }

    @Override
    public String toString(){
        return request_id;
    }
}