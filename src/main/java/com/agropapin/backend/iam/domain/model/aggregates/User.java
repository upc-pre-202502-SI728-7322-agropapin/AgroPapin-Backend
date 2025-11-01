package com.agropapin.backend.iam.domain.model.aggregates;

import com.agropapin.backend.iam.domain.model.entities.Role;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class User {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String username) {
        this.username = username;
        this.roles = new HashSet<>();
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
        this.roles = new HashSet<>();
    }

    public User(String username,List<Role> roles) {
        this(username);
        addRoles(roles);
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }

    public List<String> getSerializedRoles() {
        return this.roles.stream().map(role -> role.getName().name()).toList();
    }
}