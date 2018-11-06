package com.fpr360.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientCrm.
 */
@Entity
@Table(name = "client_crm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientCrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 150)
    @Column(name = "endpoint", length = 150, nullable = false)
    private String endpoint;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "web_username", length = 50, nullable = false)
    private String webUsername;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "web_password", length = 50, nullable = false)
    private String webPassword;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "api_key", length = 100, nullable = false)
    private String apiKey;

    @Column(name = "api_secret")
    private String apiSecret; // Could be null because maybe the CRM vendor use just API_KEY for authentication.

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private CrmVendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public ClientCrm endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getWebUsername() {
        return webUsername;
    }

    public ClientCrm webUsername(String webUsername) {
        this.webUsername = webUsername;
        return this;
    }

    public void setWebUsername(String webUsername) {
        this.webUsername = webUsername;
    }

    public String getWebPassword() {
        return webPassword;
    }

    public ClientCrm webPassword(String webPassword) {
        this.webPassword = webPassword;
        return this;
    }

    public void setWebPassword(String webPassword) {
        this.webPassword = webPassword;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ClientCrm apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public ClientCrm apiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        return this;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public Client getClient() {
        return client;
    }

    public ClientCrm client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CrmVendor getVendor() {
        return vendor;
    }

    public ClientCrm vendor(CrmVendor crmVendor) {
        this.vendor = crmVendor;
        return this;
    }

    public void setVendor(CrmVendor crmVendor) {
        this.vendor = crmVendor;
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
        ClientCrm clientCrm = (ClientCrm) o;
        if (clientCrm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientCrm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientCrm{" +
            "id=" + getId() +
            ", endpoint='" + getEndpoint() + "'" +
            ", webUsername='" + getWebUsername() + "'" +
            ", webPassword='" + getWebPassword() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", apiSecret='" + getApiSecret() + "'" +
            "}";
    }
}
