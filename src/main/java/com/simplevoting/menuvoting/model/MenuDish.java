package com.simplevoting.menuvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "MenuDish")
@Table(name = "menus_dishes")
public class MenuDish implements Serializable {
    @EmbeddedId
    @JsonIgnore
    private MenuDishId id = new MenuDishId();
    @Column(name = "price", columnDefinition = "NUMERIC(10, 4) DEFAULT 0.0")
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menu_id")
    @JsonBackReference(value = "menu-dishes")
    private Menu menu;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("dish_id")
    private Dish dish;

    public MenuDish() {
    }

    public MenuDish(Menu menu, Dish dish, double price) {
        this.price = price;
        this.menu = menu;
        this.dish = dish;
        id.setMenu_id(menu.getId());
        id.setDish_id(dish.getId());
    }

    public MenuDishId getId() {
        return id;
    }

    public void setId(MenuDishId id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        id.setMenu_id(menu != null ? menu.getId() : null);
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        id.setDish_id(dish != null ? dish.getId() : null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuDish)) return false;
        MenuDish menuDish = (MenuDish) o;
        return Double.compare(menuDish.price, price) == 0 &&
                Objects.equals(id, menuDish.id) &&
                Objects.equals(menu, menuDish.menu) &&
                Objects.equals(dish, menuDish.dish);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, price, menu, dish);
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n", getDish().toString(), price);
    }
}
