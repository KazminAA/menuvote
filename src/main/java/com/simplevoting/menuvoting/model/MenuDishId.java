package com.simplevoting.menuvoting.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuDishId implements Serializable {
    @Column(name = "menu_id")
    private Integer menu_id;
    @Column(name = "dish_id")
    private Integer dish_id;

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuDishId)) return false;
        MenuDishId that = (MenuDishId) o;
        return Objects.equals(menu_id, that.menu_id) &&
                Objects.equals(dish_id, that.dish_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(menu_id, dish_id);
    }
}
