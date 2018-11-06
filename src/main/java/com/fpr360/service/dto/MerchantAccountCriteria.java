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
 * Criteria class for the MerchantAccount entity. This class is used in MerchantAccountResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /merchant-accounts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MerchantAccountCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mid;

    private StringFilter midDescriptor;

    private BooleanFilter active;

    private LongFilter merchantId;

    private LongFilter clientCrmId;

    public MerchantAccountCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMid() {
        return mid;
    }

    public void setMid(StringFilter mid) {
        this.mid = mid;
    }

    public StringFilter getMidDescriptor() {
        return midDescriptor;
    }

    public void setMidDescriptor(StringFilter midDescriptor) {
        this.midDescriptor = midDescriptor;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(LongFilter merchantId) {
        this.merchantId = merchantId;
    }

    public LongFilter getClientCrmId() {
        return clientCrmId;
    }

    public void setClientCrmId(LongFilter clientCrmId) {
        this.clientCrmId = clientCrmId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MerchantAccountCriteria that = (MerchantAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mid, that.mid) &&
            Objects.equals(midDescriptor, that.midDescriptor) &&
            Objects.equals(active, that.active) &&
            Objects.equals(merchantId, that.merchantId) &&
            Objects.equals(clientCrmId, that.clientCrmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mid,
        midDescriptor,
        active,
        merchantId,
        clientCrmId
        );
    }

    @Override
    public String toString() {
        return "MerchantAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mid != null ? "mid=" + mid + ", " : "") +
                (midDescriptor != null ? "midDescriptor=" + midDescriptor + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (merchantId != null ? "merchantId=" + merchantId + ", " : "") +
                (clientCrmId != null ? "clientCrmId=" + clientCrmId + ", " : "") +
            "}";
    }

}
