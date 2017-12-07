package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = "date, user_id", name = "date_user_idx")})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "menu_votes_and_users", attributeNodes = {@NamedAttributeNode("users")}),
        @NamedEntityGraph(name = "user_votes_and_menus", attributeNodes = {@NamedAttributeNode("menus")})
})
public class Vote {
    @Column(name = "date")
    @NotNull
    private LocalDate date = LocalDate.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    @NotNull
    private Menu menu;

    public Vote() {
    }

    public Vote(LocalDate date, User user, Menu menu) {
        this.date = date;
        this.user = user;
        this.menu = menu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
