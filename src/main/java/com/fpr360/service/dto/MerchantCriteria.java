package com.fpr360.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the Merchant entity. This class is used in MerchantResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /merchants?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MerchantCriteria extends OrganizationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter clientId;

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MerchantCriteria that = (MerchantCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(name, that.name) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(address, that.address) &&
                        Objects.equals(city, that.city) &&
                        Objects.equals(state, that.state) &&
                        Objects.equals(postalCode, that.postalCode) &&
                        Objects.equals(country, that.country) &&
                        Objects.equals(phoneNumber, that.phoneNumber) &&
                        Objects.equals(phoneExt, that.phoneExt) &&
                        Objects.equals(email, that.email) &&
                        Objects.equals(website, that.website) &&
                        Objects.equals(currency, that.currency) &&
                        Objects.equals(active, that.active) &&
                        Objects.equals(industryId, that.industryId) &&
                        Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                description,
                address,
                city,
                state,
                postalCode,
                country,
                phoneNumber,
                phoneExt,
                email,
                website,
                currency,
                active,
                industryId,
                clientId
        );
    }

    @Override
    public String toString() {
        return "MerchantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (city != null ? "city=" + state + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (phoneExt != null ? "phoneExt=" + phoneExt + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (website != null ? "website=" + website + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (industryId != null ? "industryId=" + industryId + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                "}";
    }
}
