package com.fpr360.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A Merchant.
 */
@Entity
@Table(name = "merchant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Merchant extends Organization<Merchant> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false, targetEntity = Client.class)
    @JoinColumn
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Client client;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Merchant client(Client client) {
        this.client = client;
        return builderMethodReturnValue();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    protected Merchant builderMethodReturnValue() {
        return this;
    }
}
