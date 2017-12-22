package com.simplevoting.menuvoting.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class VoteId implements Serializable {
    @Column(name = "date")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Column(name = "user_id")
    @NotNull
    private Integer user_id;

    public VoteId() {
    }

    public VoteId(LocalDate date, Integer user_id) {
        this.date = date;
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("(%s (%d))", date.toString(), user_id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteId)) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(date, voteId.date) &&
                Objects.equals(user_id, voteId.user_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, user_id);
    }
}
