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
 * Criteria class for the ClientCrm entity. This class is used in ClientCrmResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /client-crms?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientCrmCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter endpoint;

    private StringFilter webUsername;

    private StringFilter webPassword;

    private StringFilter apiKey;

    private StringFilter apiSecret;

    private LongFilter clientId;

    private LongFilter vendorId;

    public ClientCrmCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(StringFilter endpoint) {
        this.endpoint = endpoint;
    }

    public StringFilter getWebUsername() {
        return webUsername;
    }

    public void setWebUsername(StringFilter webUsername) {
        this.webUsername = webUsername;
    }

    public StringFilter getWebPassword() {
        return webPassword;
    }

    public void setWebPassword(StringFilter webPassword) {
        this.webPassword = webPassword;
    }

    public StringFilter getApiKey() {
        return apiKey;
    }

    public void setApiKey(StringFilter apiKey) {
        this.apiKey = apiKey;
    }

    public StringFilter getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(StringFilter apiSecret) {
        this.apiSecret = apiSecret;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientCrmCriteria that = (ClientCrmCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(endpoint, that.endpoint) &&
            Objects.equals(webUsername, that.webUsername) &&
            Objects.equals(webPassword, that.webPassword) &&
            Objects.equals(apiKey, that.apiKey) &&
            Objects.equals(apiSecret, that.apiSecret) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        endpoint,
        webUsername,
        webPassword,
        apiKey,
        apiSecret,
        clientId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "ClientCrmCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (endpoint != null ? "endpoint=" + endpoint + ", " : "") +
                (webUsername != null ? "webUsername=" + webUsername + ", " : "") +
                (webPassword != null ? "webPassword=" + webPassword + ", " : "") +
                (apiKey != null ? "apiKey=" + apiKey + ", " : "") +
                (apiSecret != null ? "apiSecret=" + apiSecret + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}
