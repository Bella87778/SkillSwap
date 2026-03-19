package SkillSwap.domain;

public class Skills {

    private String skill_id;
    private String name;
    private String category;

    public Skills(String skill_id, String name, String category) {
        this.skill_id = skill_id;
        this.name = name;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skills altro)) {
            return false;
        }
        return this.skill_id.equals(altro.skill_id);
    }

    @Override
    public int hashCode() {
        return skill_id.hashCode();
    }

    @Override
    public String toString() {
        return "Skill{"
                + "id='" + skill_id + '\''
                + ", name='" + name + '\''
                + ", category='" + category + '\''
                + '}';
    }

    public String getID() {
        return skill_id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setSkill_id(String skill_id) {
        this.skill_id = skill_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
