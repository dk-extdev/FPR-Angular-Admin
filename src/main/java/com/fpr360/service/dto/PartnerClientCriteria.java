package com.fpr360.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the PartnerClient entity. This class is used in PartnerClientResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /partner-clients?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartnerClientCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter partnerId;

    private LongFilter merchantAccountId;

    public PartnerClientCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(LongFilter partnerId) {
        this.partnerId = partnerId;
    }

    public LongFilter getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(LongFilter merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartnerClientCriteria that = (PartnerClientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(partnerId, that.partnerId) &&
            Objects.equals(merchantAccountId, that.merchantAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        partnerId,
        merchantAccountId
        );
    }

    @Override
    public String toString() {
        return "PartnerClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (partnerId != null ? "partnerId=" + partnerId + ", " : "") +
                (merchantAccountId != null ? "merchantAccountId=" + merchantAccountId + ", " : "") +
            "}";
    }

}
