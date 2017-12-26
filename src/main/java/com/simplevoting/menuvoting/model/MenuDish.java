package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "MenuDish")
@Table(name = "menus_dishes")
public class MenuDish {
    @EmbeddedId
    private MenuDishId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menu_id")
    private Menu menu;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("dish_id")
    private Dish dish;
    @Column(name = "price", columnDefinition = "NUMERIC(10, 4) DEFAULT 0.0")
    private double price;

    public MenuDish() {
    }

    public MenuDish(Menu menu, Dish dish, double price) {
        this.menu = menu;
        this.dish = dish;
        this.id = new MenuDishId(menu.getId(), dish.getId());
        this.price = price;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuDish)) return false;
        MenuDish menuDish = (MenuDish) o;
        return Double.compare(menuDish.price, price) == 0 &&
                Objects.equals(id, menuDish.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, price);
    }

    @Override
    public String toString() {
        return String.format("%s: %d\n", dish.toString(), price);
    }
}
