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
 * Criteria class for the ContactInfo entity. This class is used in ContactInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /contact-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContactInfoCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter primaryPhone;

    private StringFilter secondaryPhone;

    private StringFilter workPhone;

    private StringFilter workPhoneExt;

    private StringFilter mobilePhone;

    private StringFilter skypeId;

    private LongFilter userId;

    public ContactInfoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(StringFilter primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public StringFilter getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(StringFilter secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public StringFilter getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(StringFilter workPhone) {
        this.workPhone = workPhone;
    }

    public StringFilter getWorkPhoneExt() {
        return workPhoneExt;
    }

    public void setWorkPhoneExt(StringFilter workPhoneExt) {
        this.workPhoneExt = workPhoneExt;
    }

    public StringFilter getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(StringFilter mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public StringFilter getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(StringFilter skypeId) {
        this.skypeId = skypeId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContactInfoCriteria that = (ContactInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(primaryPhone, that.primaryPhone) &&
            Objects.equals(secondaryPhone, that.secondaryPhone) &&
            Objects.equals(workPhone, that.workPhone) &&
            Objects.equals(workPhoneExt, that.workPhoneExt) &&
            Objects.equals(mobilePhone, that.mobilePhone) &&
            Objects.equals(skypeId, that.skypeId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        primaryPhone,
        secondaryPhone,
        workPhone,
        workPhoneExt,
        mobilePhone,
        skypeId,
        userId
        );
    }

    @Override
    public String toString() {
        return "ContactInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (primaryPhone != null ? "primaryPhone=" + primaryPhone + ", " : "") +
                (secondaryPhone != null ? "secondaryPhone=" + secondaryPhone + ", " : "") +
                (workPhone != null ? "workPhone=" + workPhone + ", " : "") +
                (workPhoneExt != null ? "workPhoneExt=" + workPhoneExt + ", " : "") +
                (mobilePhone != null ? "mobilePhone=" + mobilePhone + ", " : "") +
                (skypeId != null ? "skypeId=" + skypeId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
