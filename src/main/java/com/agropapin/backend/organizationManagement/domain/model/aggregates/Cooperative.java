package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cooperative extends AuditableAbstractAggregateRoot<Cooperative> {

    @NotBlank
    @Column(name = "cooperative_name", nullable = false)
    private String cooperativeName;

    // 0..* members — mappedBy debe existir en Farmer: private Cooperative cooperative;
    @OneToMany(mappedBy = "cooperative", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Farmer> members = new ArrayList<>();

    // 1..* administrators — usar @NotEmpty para forzar al menos uno
    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cooperative_administrators",
            joinColumns = @JoinColumn(name = "cooperative_id"),
            inverseJoinColumns = @JoinColumn(name = "administrator_id")
    )
    private List<Administrator> administrators = new ArrayList<>();

    public Cooperative(String name, Administrator creatorUser) {
        this.cooperativeName = name;
        this.administrators.add(creatorUser);
    }

    public void updateInfo(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cooperative name cannot be empty");
        }
        this.cooperativeName = name;
    }

    public void addMember(Farmer farmer) {
        members.add(farmer);
        farmer.assignToCooperative(this);
    }

    public void removeMember(Farmer farmer) {
        members.remove(farmer);
        farmer.clearCooperative();
    }

    public void addAdministrator(Administrator administrator) {
        administrators.add(administrator);
    }

    public void removeAdministrator(Administrator administrator) {
        administrators.remove(administrator);
    }
}
