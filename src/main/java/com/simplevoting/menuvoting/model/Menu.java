package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = "date", name = "menu_date_restaurant_idx"),
        @UniqueConstraint(columnNames = "restaurant_id", name = "menu_date_restaurant_idx")})
public class Menu extends AbstractBaseEntity {
    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private Date date = new Date();

    @Column(name = "curvotenum", columnDefinition = "default 0")
    @NotNull
    private Integer curvotenum = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    private Set<MenuList> menuList;

    public Menu() {
    }

    public Menu(Integer id, Date date, Restaurant restaurant, Set<MenuList> menuList) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.menuList = menuList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCurvotenum() {
        return curvotenum;
    }

    public void setCurvotenum(Integer curvotenum) {
        this.curvotenum = curvotenum;
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
        return String.format("Menu for '%s' on %s: %s", restaurant.getName(), date.toString(), Arrays.toString(menuList.toArray()));
    }
}