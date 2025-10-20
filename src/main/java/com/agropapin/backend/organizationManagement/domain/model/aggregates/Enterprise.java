package com.agropapin.backend.organizationManagement.domain.model.aggregates;

import com.fromzero.backend.iam.domain.model.aggregates.User;
import com.fromzero.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Enterprise  extends AuditableAbstractAggregateRoot<Enterprise> {

    @NotBlank
    private String enterpriseName;
    private String description = "No description provided.";

    private String country = "No country provided.";

    private String ruc = "No RUC provided.";

    private String phone = "No phone provided.";

    private String website = "No website provided.";

    private String profileImgUrl = "https://cdn-icons-png.flaticon.com/512/3237/3237472.png";

    private String sector = "No sector provided.";

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Enterprise(User user, String enterpriseName, String description, String country, String ruc, String phone, String website, String profileImgUrl, String sector){
        this.user = user;
        this.enterpriseName = enterpriseName;
        this.description = description;
        this.country = country;
        this.ruc = ruc;
        this.phone = phone;
        this.website = website;
        this.profileImgUrl = profileImgUrl;
        this.sector = sector;
    }

    public Enterprise(){

    }

    public Long getUserId() {
        return this.user.getId();
    }

}
