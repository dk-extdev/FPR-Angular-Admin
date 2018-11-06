package com.fpr360.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.fpr360.domain.enumeration.GuruClientLevel;

/**
 * A GuruClient. Many-to-many implementation between guru (which is user) and their client (the organization).
 */
@Entity
@Table(name = "guru_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GuruClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "guru_client_level", nullable = false)
    private GuruClientLevel level;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User guru;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GuruClientLevel getLevel() {
        return level;
    }

    public GuruClient level(GuruClientLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(GuruClientLevel level) {
        this.level = level;
    }

    public User getGuru() {
        return guru;
    }

    public GuruClient guru(User user) {
        this.guru = user;
        return this;
    }

    public void setGuru(User user) {
        this.guru = user;
    }

    public Client getClient() {
        return client;
    }

    public GuruClient client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
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
        GuruClient guruClient = (GuruClient) o;
        if (guruClient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), guruClient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GuruClient{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
