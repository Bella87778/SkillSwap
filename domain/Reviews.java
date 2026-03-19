package SkillSwap.domain;

import java.time.LocalDateTime;

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

    public String getID() {
        return review_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reviews altro)) {
            return false;
        }
        return this.review_id.equals(altro.review_id);
    }

    @Override
    public int hashCode() {
        return review_id.hashCode();
    }

    @Override
    public String toString() {
        return "Review{"
                + "id='" + review_id + '\''
                + ", exchange_id='" + exchange_id + '\''
                + ", reviewer='" + reviewer_student_id + '\''
                + ", reviewee='" + reviewee_student_id + '\''
                + ", stars=" + stars
                + ", comment='" + comment + '\''
                + ", created_at=" + created_at
                + '}';
    }

    public String getExchangeId() {
        return exchange_id;
    }

    public String getReviewerStudentId() {
        return reviewer_student_id;
    }

    public String getRevieweeStudentId() {
        return reviewee_student_id;
    }

    public int getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public void setReviewer_student_id(String reviewer_student_id) {
        this.reviewer_student_id = reviewer_student_id;
    }

    public void setReviewee_student_id(String reviewee_student_id) {
        this.reviewee_student_id = reviewee_student_id;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
