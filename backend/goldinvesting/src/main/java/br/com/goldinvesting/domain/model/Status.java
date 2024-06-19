package br.com.goldinvesting.domain.model;

/**
 * Enumeration representing the status of an investment.
 */
public enum Status {
    /**
     * The investment is currently active and being traded.
     */
    ACTIVE,

    /**
     * The investment is no longer being traded.
     */
    INACTIVE,

    /**
     * The investment is pending and awaiting some condition to become active.
     */
    PENDING,

    /**
     * The investment has been sold.
     */
    SOLD,

    /**
     * The investment has been purchased but is no longer in the portfolio.
     */
    PURCHASED,

    /**
     * The investment is currently under review.
     */
    UNDER_REVIEW,

    /**
     * The investment has been concluded.
     */
    CONCLUDED,
}
