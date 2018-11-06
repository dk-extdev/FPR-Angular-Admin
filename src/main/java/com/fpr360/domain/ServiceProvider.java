package com.fpr360.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * A ServiceProvider.
 */
@Entity
@Table(name = "service_provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceProvider extends Organization<ServiceProvider> implements Serializable {

    @Override
    @Transient
    protected ServiceProvider builderMethodReturnValue() {
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
}
