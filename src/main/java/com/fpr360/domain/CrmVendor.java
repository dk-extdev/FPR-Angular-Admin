package com.fpr360.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.fpr360.domain.enumeration.CrmApiType;

/**
 * A CrmVendor.
 */
@Entity
@Table(name = "crm_vendor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmVendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "api_type", nullable = false)
    private CrmApiType apiType;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(name = "website", length = 100, nullable = false)
    private String website;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CrmVendor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmApiType getApiType() {
        return apiType;
    }

    public CrmVendor apiType(CrmApiType apiType) {
        this.apiType = apiType;
        return this;
    }

    public void setApiType(CrmApiType apiType) {
        this.apiType = apiType;
    }

    public String getWebsite() {
        return website;
    }

    public CrmVendor website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmVendor crmVendor = (CrmVendor) o;
        if (crmVendor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), crmVendor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CrmVendor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", apiType='" + getApiType() + "'" +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
