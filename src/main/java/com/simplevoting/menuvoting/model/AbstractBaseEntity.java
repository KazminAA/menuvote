package com.simplevoting.menuvoting.model;

import com.simplevoting.menuvoting.HasId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

@MappedSuperclass
@AccessType(AccessType.Type.PROPERTY)
public abstract class AbstractBaseEntity implements HasId {
    public static final int START_SEQ = 100000;

    @Id
    @GenericGenerator(
            name = "global_gen",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "global_seq"),
                    @Parameter(name = "optimizer", value = "none"),
                    @Parameter(name = "initial_value", value = "" + START_SEQ),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_gen")
    private Integer id;

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    protected AbstractBaseEntity() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%d)", getClass().getSimpleName(), this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBaseEntity)) return false;

        AbstractBaseEntity that = (AbstractBaseEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
