package com.fpr360.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MerchantAccount.
 */
@Entity
@Table(name = "merchant_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MerchantAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 8, max = 50)
    @Column(name = "mid", length = 50, nullable = false)
    private String mid;

    @Column(name = "mid_descriptor")
    private String midDescriptor;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Merchant merchant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ClientCrm clientCrm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public MerchantAccount mid(String mid) {
        this.mid = mid;
        return this;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMidDescriptor() {
        return midDescriptor;
    }

    public MerchantAccount midDescriptor(String midDescriptor) {
        this.midDescriptor = midDescriptor;
        return this;
    }

    public void setMidDescriptor(String midDescriptor) {
        this.midDescriptor = midDescriptor;
    }

    public Boolean isActive() {
        return active;
    }

    public MerchantAccount active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public MerchantAccount merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public ClientCrm getClientCrm() {
        return clientCrm;
    }

    public MerchantAccount clientCrm(ClientCrm clientCrm) {
        this.clientCrm = clientCrm;
        return this;
    }

    public void setClientCrm(ClientCrm clientCrm) {
        this.clientCrm = clientCrm;
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
        MerchantAccount merchantAccount = (MerchantAccount) o;
        if (merchantAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), merchantAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MerchantAccount{" +
            "id=" + getId() +
            ", mid='" + getMid() + "'" +
            ", midDescriptor='" + getMidDescriptor() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
