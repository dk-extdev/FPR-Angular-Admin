package com.fpr360.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.fpr360.domain.enumeration.PartnerType;

/**
 * A Partner.
 */
@Entity
@Table(name = "partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partner extends Organization<Partner> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "partner_type", nullable = false)
    private PartnerType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public PartnerType getType() {
        return type;
    }

    public Partner type(PartnerType type) {
        this.type = type;
        return this;
    }

    public void setType(PartnerType type) {
        this.type = type;
    }

    @Override
    protected Partner builderMethodReturnValue() {
        return this;
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", phoneExt='" + getPhoneExt() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", active='" + isActive() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
