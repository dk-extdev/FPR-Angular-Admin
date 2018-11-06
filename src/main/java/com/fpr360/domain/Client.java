package com.fpr360.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client extends Organization<Client> implements Serializable {

    private static final long serialVersionUID = 1L;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    protected Client builderMethodReturnValue() {
        return this;
    }
}
