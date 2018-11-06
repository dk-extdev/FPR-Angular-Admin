package com.fpr360.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.fpr360.domain.enumeration.CrmApiType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the CrmVendor entity. This class is used in CrmVendorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /crm-vendors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CrmVendorCriteria implements Serializable {
    /**
     * Class for filtering CrmApiType
     */
    public static class CrmApiTypeFilter extends Filter<CrmApiType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private CrmApiTypeFilter apiType;

    private StringFilter website;

    public CrmVendorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public CrmApiTypeFilter getApiType() {
        return apiType;
    }

    public void setApiType(CrmApiTypeFilter apiType) {
        this.apiType = apiType;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CrmVendorCriteria that = (CrmVendorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(apiType, that.apiType) &&
            Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        apiType,
        website
        );
    }

    @Override
    public String toString() {
        return "CrmVendorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (apiType != null ? "apiType=" + apiType + ", " : "") +
                (website != null ? "website=" + website + ", " : "") +
            "}";
    }

}
