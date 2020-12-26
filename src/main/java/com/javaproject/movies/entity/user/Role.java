package com.javaproject.movies.entity.user;

import com.javaproject.movies.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity //сущность, имеющая таблицу
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }
}
