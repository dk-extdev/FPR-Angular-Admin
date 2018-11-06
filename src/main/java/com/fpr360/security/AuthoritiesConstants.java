package com.fpr360.security;

/**
 * Constants for Spring Security authorities. Currently, the values are almost the same with {@link com.fpr360.domain.UserType}.
 * But these value maybe added more to make sure certain user doesn't have access to specifics area, while the
 * {@link com.fpr360.domain.UserType} values would remain the same.
 *
 * Read more at https://chargebackgurus.sharepoint.com/sites/ProductDevelopment/Shared%20Documents/FPR360/User-Business-Entities.pdf
 */
public final class AuthoritiesConstants {

    /** Super Admin */
    public static final String ADMIN = "ROLE_ADMIN";

    /** Type of guru, from chargebackgurus */
    public static final String OPS_MANAGER = "ROLE_OPS_MANAGER";
    public static final String PREVENTION_ALERT_MANAGER = "ROLE_PREVENTION_ALERT_MANAGER";
    public static final String PREVENTION_ALERT_ANALYST = "ROLE_PREVENTION_ALERT_ANALYST";
    public static final String CLIENT_SERVICE_MANAGER = "ROLE_CLIENT_SERVICE_MANAGER";
    public static final String SENIOR_CHARGEBACK_ANALYST = "ROLE_SENIOR_CHARGEBACK_ANALYST";
    public static final String INTERMEDIETE_CHARGEBACK_ANALYST = "ROLE_INTERMEDIETE_CHARGEBACK_ANALYST";
    public static final String JUNIOR_CHARGEBACK_ANALYST = "ROLE_JUNIOR_CHARGEBACK_ANALYST";
    public static final String CASE_RESOLUTION_ANALYST = "ROLE_CASE_RESOLUTION_ANALYST";
    public static final String DATA_ENTRY_OPERATOR = "ROLE_DATA_ENTRY_OPERATOR";

    /** Client, that own a merchant(s). */
    public static final String CLIENT = "ROLE_CLIENT";

    /** Partner, commonly payment gateway (stripe, braintree, etc) company representation */
    public static final String PARTNER = "ROLE_PARTNER";

    /** To check the "logged in user" doesn't have this role. */
    public static final String ANONYMOUS = "ROLE_ANONYMOUS"; // FIXME: Do we really need this?

    private AuthoritiesConstants() {
    }
}
