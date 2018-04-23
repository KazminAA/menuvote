package com.simplevoting.menuvoting.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "dishes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}, name = "dish_name_idx")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dish extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @Column(name = "description", columnDefinition = "varchar default ''", nullable = false)
    private String description;

    public Dish() {
    }

    public Dish(Integer id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public Dish(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getDescription());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        if (!super.equals(o)) return false;
        Dish dish = (Dish) o;
        return Objects.equals(name, dish.name) &&
                Objects.equals(description, dish.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, description);
    }

    @Override
    public String toString() {
        return String.format("%s (%.20s)", this.name, this.description);
    }
}
