package com.simplevoting.menuvoting.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "menulists")
public class MenuList extends AbstractBaseEntity {
    @Column(name = "dish", nullable = false)
    @NotBlank
    @Size(min = 3, max = 50)
    private String dish;

    @Column(name = "price", columnDefinition = "NUMERIC(10, 4) DEFAULT 0.0")
    @NotNull
    private Double price = 0.0;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    public MenuList() {
    }

    public MenuList(Integer id, String dish, double price, Menu menu) {
        super(id);
        this.dish = dish;
        this.price = price;
        this.menu = menu;
    }

    public MenuList(Integer id, String dish, Double price) {
        super(id);
        this.dish = dish;
        this.price = price;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuList)) return false;
        if (!super.equals(o)) return false;

        MenuList menuList = (MenuList) o;

        if (!dish.equals(menuList.dish)) return false;
        return price.equals(menuList.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dish.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("'%s' - %s", dish, price.toString(2));
    }
}
