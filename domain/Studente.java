package SkillSwap.domain;

public class Studente {

    private String nome;
    private String student_id;
    private String classe;
    private String email;
    private int rating_avg;
    private int rating_count;

    public Studente(String nome, String student_id, String classe, String email, int rating_avg, int rating_count) {
        this.nome = nome;
        this.student_id = student_id;
        this.classe = classe;
        this.email = email;
        this.rating_avg = rating_avg;
        this.rating_count = rating_count;
    }

    public String getID() {
        return student_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Studente altro)) {
            return false;
        }
        return this.student_id.equals(altro.student_id);
    }

    @Override
    public int hashCode() {
        return 31 * 17 + student_id.hashCode();
    }

    @Override
    public String toString() {
        return "Studente{"
                + "nome='" + nome + '\''
                + ", id='" + student_id + '\''
                + ", classe='" + classe + '\''
                + ", email='" + email + '\''
                + ", rating_avg=" + rating_avg
                + ", rating_count=" + rating_count
                + '}';
    }

    public String getNome() {
        return nome;
    }

    public String getClasse() {
        return classe;
    }

    public String getEmail() {
        return email;
    }

    public int getRatingAvg() {
        return rating_avg;
    }

    public int getRatingCount() {
        return rating_count;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating_avg(int rating_avg) {
        this.rating_avg = rating_avg;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }
}
