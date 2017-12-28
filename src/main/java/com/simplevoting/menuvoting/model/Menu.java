package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "menus", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "restaurant_id"}, name = "menu_date_restaurant_idx")
})
public class Menu extends AbstractBaseEntity {
    @Column(name = "date", columnDefinition = "date default now()", nullable = false)
    @NotNull
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @NotNull
    private Restaurant restaurant;

    @OneToMany(
            mappedBy = "menu",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<MenuDish> dishes = Collections.emptySet();

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private Set<Vote> votes;

    public Menu() {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant, Set<MenuDish> dishes) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = new HashSet<>(dishes);
    }

    public Menu(Menu menu) {
        this.setId(menu.getId());
        this.date = menu.getDate();
        this.restaurant = menu.getRestaurant();
        this.dishes = new HashSet<>();
        menu.getMenuList().forEach(menuDish -> {
            MenuDish newMenuDish = new MenuDish(this, menuDish.getDish(), menuDish.getPrice());
            this.dishes.add(newMenuDish);
        });
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<MenuDish> getMenuList() {
        return dishes;
    }

    public void setMenuList(Set<MenuDish> dishes) {
        this.dishes = dishes;
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
        return Objects.equals(date, menu.date) &&
                Objects.equals(restaurant, menu.restaurant);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), date, restaurant);
    }

    @Override
    public String toString() {
        return String.format("Menu for '%s' on %s (%d):\n %s", restaurant.getName(), date.toString(), this.getId(), Arrays.asList(dishes.toArray()));
    }
}
