package com.fpr360.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.Filter;

/**
 * Criteria class for the ServiceProvider entity. This class is used in ServiceProviderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /service-providers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceProviderCriteria extends OrganizationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

}
