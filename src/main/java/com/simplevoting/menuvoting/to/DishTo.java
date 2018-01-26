package com.simplevoting.menuvoting.to;

import com.simplevoting.menuvoting.model.Dish;
import org.hibernate.validator.constraints.Range;

public class DishTo {
    private Dish dish;
    @Range(min = 0)
    private double price;

    public DishTo(Dish dish, double price) {
        this.dish = dish;
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
}
