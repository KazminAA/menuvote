package com.simplevoting.menuvoting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "restaurants", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")
})
public class Restaurant extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Column(name = "address")
    @Pattern(regexp = "[a-zа-я]{1,3}\\.[a-zA-Zа-яА-Я-]+\\,\\s\\d{1,3}[a-zа-я]?")
    private String address;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Restaurant(Integer id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public Restaurant(Restaurant restaurant) {
        this.setId(restaurant.getId());
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Restaurant '%s' (%d), address: '%s'", this.name, getId(), this.address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        if (!super.equals(o)) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, address);
    }
}
