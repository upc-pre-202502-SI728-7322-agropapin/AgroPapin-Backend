package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Developer extends AuditableAbstractAggregateRoot<Developer> {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;


    private String description = "No description provided.";

    private String country = "No country provided.";

    private String phone = "999 999 999";

    private int completedProjects = 0;

    private String specialties = "No specialties provided.";

    private String profileImgUrl = "https://cdn-icons-png.flaticon.com/512/3237/3237472.png";

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Developer(User user, String firstName, String lastName, String description, String country, String phone, int completedProjects, String specialties, String profileImgUrl){
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.country = country;
        this.phone = phone;
        this.completedProjects = completedProjects;
        this.specialties = specialties;
        this.profileImgUrl = profileImgUrl;
    }

    public Developer(){}

    public Long getUserId() {
        return this.user.getId();
    }
}
