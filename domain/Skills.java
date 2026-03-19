package Skillswap.domain;

import java.util.LinkedList;

public class Skills {

    private String skill_id;
    private String name;
    private String category;

    public Skills(String skill_id, String name, String category) {
        this.skill_id = skill_id;
        this.name = name;
        this.category = category;
    }

    LinkedList<String> skills = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Studente altro) {
            return this.skill_id.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int var1 = 17;
        var1 = var1 * 31 + skill_id.hashCode();
        return var1;
    }

    @Override
    public String toString() {
        return name + skill_id;
    }
}
