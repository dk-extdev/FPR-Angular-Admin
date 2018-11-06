package com.fpr360.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represent an organization. This class is an abstract class to minimize an interaction with this class and force
 * implementation to its subclasses. The main purpose of this class  is just to share 'common attributes', not behaviour.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Organization<E extends Organization<E>> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Size(min = 5, max = 150)
    @Column(name = "address", length = 150, nullable = false)
    private String address;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @NotNull
    @Size(min = 3, max = 8)
    @Column(name = "postal_code", length = 8, nullable = false)
    private String postalCode;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "country", length = 100, nullable = false)
    private String country;

    @NotNull
    @Size(min = 4, max = 35)
    @Column(name = "phone_number", length = 35, nullable = false)
    private String phoneNumber;

    @Column(name = "phone_ext")
    private String phoneExt;

    @NotNull
    @Size(min = 5, max = 150)
    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "website")
    private String website;

    @NotNull
    @Size(min = 2, max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean active;

    @ManyToOne(optional = false, targetEntity = Industry.class)
    @JoinColumn
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Industry industry;

    /**
     * https://stackoverflow.com/q/1069528/554958 . Used to dynamically set builder method return values. These builder
     * methods are: {@link #name(String)}, {@link #description(String)}, etc that return <code>this</code> instance.
     * @return implementation instance defined in E.
     */
    protected abstract E builderMethodReturnValue();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public E name(String name) {
        this.name = name;
        return builderMethodReturnValue();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public E description(String description) {
        this.description = description;
        return builderMethodReturnValue();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public E address(String address) {
        this.address = address;
        return builderMethodReturnValue();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public E city(String city) {
        this.city = city;
        return builderMethodReturnValue();
    }

    public String getState() {
        return state;
    }

    public E state(String state) {
        this.state = state;
        return builderMethodReturnValue();
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public E postalCode(String postalCode) {
        this.postalCode = postalCode;
        return builderMethodReturnValue();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public E country(String country) {
        this.country = country;
        return builderMethodReturnValue();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public E phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return builderMethodReturnValue();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public E phoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
        return builderMethodReturnValue();
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getEmail() {
        return email;
    }

    public E email(String email) {
        this.email = email;
        return builderMethodReturnValue();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public E website(String website) {
        this.website = website;
        return builderMethodReturnValue();
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCurrency() {
        return currency;
    }

    public E currency(String currency) {
        this.currency = currency;
        return builderMethodReturnValue();
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public E active(boolean active) {
        this.active = active;
        return builderMethodReturnValue();
    }

    public Industry getIndustry() {
        return industry;
    }

    public E industry(Industry industry) {
        this.industry = industry;
        return builderMethodReturnValue();
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        E toCompare = (E) o;
        if (toCompare.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), toCompare.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IServiceProvider{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", address='" + getAddress() + "'" +
                ", state='" + getState() + "'" +
                ", postalCode='" + getPostalCode() + "'" +
                ", country='" + getCountry() + "'" +
                ", phoneNumber='" + getPhoneNumber() + "'" +
                ", phoneExt='" + getPhoneExt() + "'" +
                ", email='" + getEmail() + "'" +
                ", website='" + getWebsite() + "'" +
                ", currency='" + getCurrency() + "'" +
                "}";
    }
}
