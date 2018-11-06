package com.fpr360.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactInfo.
 */
@Entity
@Table(name = "contact_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "primary_phone", length = 35)
    @Size(max = 35)
    private String primaryPhone;

    @Column(name = "secondary_phone", length = 35)
    @Size(max = 35)
    private String secondaryPhone;

    @Column(name = "work_phone", length = 35)
    @Size(max = 35)
    private String workPhone;

    @Column(name = "work_phone_ext", length = 5)
    @Size(max = 5)
    private String workPhoneExt;

    @Column(name = "mobile_phone", length = 35)
    @Size(max = 35)
    private String mobilePhone;

    @Column(name = "skype_id", length = 50)
    @Size(max = 50)
    private String skypeId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public ContactInfo primaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
        return this;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public ContactInfo secondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
        return this;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public ContactInfo workPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkPhoneExt() {
        return workPhoneExt;
    }

    public ContactInfo workPhoneExt(String workPhoneExt) {
        this.workPhoneExt = workPhoneExt;
        return this;
    }

    public void setWorkPhoneExt(String workPhoneExt) {
        this.workPhoneExt = workPhoneExt;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public ContactInfo mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public ContactInfo skypeId(String skypeId) {
        this.skypeId = skypeId;
        return this;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public User getUser() {
        return user;
    }

    public ContactInfo user(User user) {
        user.setContactInfo(this);
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        ContactInfo contactInfo = (ContactInfo) o;
        if (contactInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
            "id=" + getId() +
            ", primaryPhone='" + getPrimaryPhone() + "'" +
            ", secondaryPhone='" + getSecondaryPhone() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", workPhoneExt='" + getWorkPhoneExt() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", skypeId='" + getSkypeId() + "'" +
            "}";
    }
}
