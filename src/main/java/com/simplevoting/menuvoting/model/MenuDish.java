package com.simplevoting.menuvoting.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "MenuDish")
@Table(name = "menus_dishes")
@AssociationOverrides({
        @AssociationOverride(name = "id.menu",
                joinColumns = @JoinColumn(name = "menu_id")),
        @AssociationOverride(name = "id.dish",
                joinColumns = @JoinColumn(name = "dish_id"))
})
public class MenuDish implements Serializable {
    @EmbeddedId
    private MenuDishId id = new MenuDishId();
    @Column(name = "price", columnDefinition = "NUMERIC(10, 4) DEFAULT 0.0")
    private double price;

    public MenuDish() {
    }

    public MenuDish(Menu menu, Dish dish, double price) {
        this.price = price;
        id.setMenu(menu);
        id.setDish(dish);
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

    @Transient
    public Menu getMenu() {
        return id.getMenu();
    }

    public void setMenu(Menu menu) {
        id.setMenu(menu);
    }

    @Transient
    public Dish getDish() {
        return id.getDish();
    }

    public void setDish(Dish dish) {
        id.setDish(dish);
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
        return String.format("%s: %s\n", getDish().toString(), price);
    }
}
