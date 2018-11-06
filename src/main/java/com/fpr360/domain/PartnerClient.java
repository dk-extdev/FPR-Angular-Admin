package com.fpr360.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 *     A PartnerClient. Many-to-many implementation between {@link Partner} and {@link MerchantAccount} . Read it again:
 *     <strong>{@link MerchantAccount}</strong>. See more at
 *     https://chargebackgurus.sharepoint.com/sites/ProductDevelopment/Shared%20Documents/FPR360/Partners-Explained.pdf
 * </p>
 *
 * <p>
 *     Here's some explanation why the entity named "<code>PartnerClient</code>" and the relationship implementation use
 *     "Partner and MerchantAccount" instead of "Partner and Client":
 *     <ol>
 *         <li>
 *             Naming the entity with "PartnerMerchantAccount" would lead to miss-interpretation: In business point of
 *             view, it seems like a partner will own/have a merchant account, which is not the case.
 *         </li>
 *         <li>PartnerMerchantAccount is a long name without any better description, see point 1 above.</li>
 *         <li>
 *             {@link PartnerClient} give a better interpretation: A client that is "monitored" by partner, detail by
 *             {@link Client}'s {@link MerchantAccount}. (PartnerMonitoredClientsMerchantAccount would be perfect name,
 *             but, really?)
 *         </li>
 *         <li>
 *             More importantly, it is seems like technically (at least in MVP phrase), a partner should able to select
 *             and monitor their <code>client</code>'s merchant account. From the requirement document, page 4, there's
 *             statement, ".... for each partner, operations team should choose the allowed merchant account .... ".
 *         </li>
 *     </ol>
 * </p>
 *
 */
@Entity
@Table(name = "partner_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartnerClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Partner partner;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private MerchantAccount merchantAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public PartnerClient partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public MerchantAccount getMerchantAccount() {
        return merchantAccount;
    }

    public PartnerClient merchantAccount(MerchantAccount merchantAccount) {
        this.merchantAccount = merchantAccount;
        return this;
    }

    public void setMerchantAccount(MerchantAccount merchantAccount) {
        this.merchantAccount = merchantAccount;
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
        PartnerClient partnerClient = (PartnerClient) o;
        if (partnerClient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerClient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartnerClient{" +
            "id=" + getId() +
            "}";
    }
}
