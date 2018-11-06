package com.fpr360.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.fpr360.domain.enumeration.GuruClientLevel;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the GuruClient entity. This class is used in GuruClientResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /guru-clients?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GuruClientCriteria implements Serializable {
    /**
     * Class for filtering GuruClientLevel
     */
    public static class GuruClientLevelFilter extends Filter<GuruClientLevel> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private GuruClientLevelFilter level;

    private LongFilter guruId;

    private LongFilter clientId;

    public GuruClientCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public GuruClientLevelFilter getLevel() {
        return level;
    }

    public void setLevel(GuruClientLevelFilter level) {
        this.level = level;
    }

    public LongFilter getGuruId() {
        return guruId;
    }

    public void setGuruId(LongFilter guruId) {
        this.guruId = guruId;
    }

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
        final GuruClientCriteria that = (GuruClientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(level, that.level) &&
            Objects.equals(guruId, that.guruId) &&
            Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        level,
        guruId,
        clientId
        );
    }

    @Override
    public String toString() {
        return "GuruClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (guruId != null ? "guruId=" + guruId + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
            "}";
    }

}
