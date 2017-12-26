package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id"}, name = "menu_date_restaurant_idx")})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "menu_votes", attributeNodes = {@NamedAttributeNode(value = "votes")})
})
public class Menu extends AbstractBaseEntity {
    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @NotNull
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private Set<MenuList> menuList = Collections.EMPTY_SET;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private Set<Vote> votes;

    public Menu() {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant, Integer curvotenum, Set<MenuList> menuList) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.menuList = menuList;
    }

    public Menu(Menu menu) {
        this.setId(menu.getId());
        this.date = menu.getDate();
        this.restaurant = menu.getRestaurant();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<MenuList> getMenuList() {
        return menuList;
    }

    public void setMenuList(Set<MenuList> menuList) {
        this.menuList = menuList;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        if (!super.equals(o)) return false;

        Menu menu = (Menu) o;

        if (!date.equals(menu.date)) return false;
        return restaurant.equals(menu.restaurant);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + restaurant.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Menu for '%s' on %s (%d): %s", restaurant.getName(), date.toString(), this.getId(), Arrays.asList(menuList.toArray()));
    }
}
