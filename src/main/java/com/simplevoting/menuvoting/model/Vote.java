package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "date_user_idx")})
public class Vote {

    @EmbeddedId
    VoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, insertable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    public Vote() {
    }

    public Vote(LocalDate date, User user, Menu menu) {
        this.id = new VoteId(date, user.getId());
        this.menu = menu;
    }

    public LocalDate getDate() {
        return id.getDate();
    }

    public void setDate(LocalDate date) {
        id.setDate(date);
    }

    public Integer getUserId() {
        return id.getUser_id();
    }

    public User getUser() {
        return this.user;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return id.toString() + menu.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
