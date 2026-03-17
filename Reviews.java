package Skillswap.domain;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Reviews {

    private String review_id;
    private String exchange_id;
    private String reviewer_student_id;
    private String reviewee_student_id;
    private int stars;
    private String comment;
    private LocalDateTime created_at;

    public Reviews(String review_id, String exchange_id, String reviewer_student_id, String reviewee_student_id,
            int stars, String comment, LocalDateTime created_at) {
        this.review_id = review_id;
        this.exchange_id = exchange_id;
        this.reviewer_student_id = reviewer_student_id;
        this.reviewee_student_id = reviewee_student_id;
        this.stars = stars;
        this.comment = comment;
        this.created_at = created_at;
    }

    LinkedList<String> reviews = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Studente altro) {
            return this.review_id.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int var1 = 17;
        var1 = var1 * 31 + review_id.hashCode();
        return var1;

    }

    @Override

    public String toString() {
        return review_id;
    }
}
