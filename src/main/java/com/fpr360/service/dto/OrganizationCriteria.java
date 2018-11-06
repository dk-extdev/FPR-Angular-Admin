package com.fpr360.service.dto;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public abstract class OrganizationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    protected LongFilter id;
    protected StringFilter name;
    protected StringFilter description;
    protected StringFilter address;
    protected StringFilter city;
    protected StringFilter state;
    protected StringFilter postalCode;
    protected StringFilter country;
    protected StringFilter phoneNumber;
    protected StringFilter phoneExt;
    protected StringFilter email;
    protected StringFilter website;
    protected StringFilter currency;
    protected BooleanFilter active;
    protected LongFilter industryId;

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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(StringFilter phoneExt) {
        this.phoneExt = phoneExt;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getIndustryId() {
        return industryId;
    }

    public void setIndustryId(LongFilter industryId) {
        this.industryId = industryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrganizationCriteria that = (OrganizationCriteria) o;
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
                        Objects.equals(industryId, that.industryId);
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
                industryId
        );
    }

    @Override
    public String toString() {
        return "OrganizationCriteria{" +
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
                "}";
    }

}
