package com.simplevoting.menuvoting.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuDishId implements Serializable {
    @ManyToOne
    private Menu menu;
    @ManyToOne
    private Dish dish;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuDishId)) return false;
        MenuDishId that = (MenuDishId) o;
        return Objects.equals(menu, that.menu) &&
                Objects.equals(dish, that.dish);
    }

    @Override
    public int hashCode() {

        return Objects.hash(menu, dish);
    }
}
