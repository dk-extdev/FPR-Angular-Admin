package com.fpr360.domain;

/**
 * {@link UserType} is a type of user in business point of view. Sometimes (but not always), {@link UserType} define
 * a role and or authorities of users in "business language", or point of view.
 *
 * This user type if have nothing to do with system's/application's User, Role, and {@link Authority}.
 * System's/Application's roles/authority defined in {@link com.fpr360.security.AuthoritiesConstants}.
 *
 * The decision to have separated {@link UserType} and {@link com.fpr360.security.AuthoritiesConstants} is that, maybe,
 * in the future, we need to add different system role to a business's {@link UserType}: For example, while from
 * business point of view, {@link UserType#GURU} is a GURU, but system need to updated to have a "Junior Guru", and
 * "Senior Guru" and they have separate role.
 *
 */
public enum UserType {
    /**
     * This user is an Agent / Representation of the Company.
     */
    CLIENT,

    /**
     * Guru is a consultant/employee of service provider (chargebackgurus) team members.
     */
    GURU,

    /**
     * It is possible that employee/agent/representation of company like stripe, braintree, etc have an account in this
     * system. This type of user would be called "Partner".
     */
    PARTNER
}
