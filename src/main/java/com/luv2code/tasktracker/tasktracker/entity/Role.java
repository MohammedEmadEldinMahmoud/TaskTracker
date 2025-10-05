package com.luv2code.tasktracker.tasktracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luv2code.tasktracker.tasktracker.enums.RoleName;
import jakarta.persistence.*;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="role")
public class Role {

        // define fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;
        @Enumerated(EnumType.STRING)
        @Column(name = "name",columnDefinition = "ENUM('ADMIN', 'USER')")
        private RoleName name;

        // define constructors
        public Role() {
        }

    public Role(RoleName name) {
        this.name = name;
    }

    // define getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}


