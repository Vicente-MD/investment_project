package br.com.goldinvesting.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a transaction involving an investment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with this transaction.
     * This field is mandatory and should not be null.
     */
    @ManyToOne
    @NotNull
    private User user;

    /**
     * The status of the transaction.
     * This field is mandatory and should not be null.
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    /**
     * The investment involved in this transaction.
     * This field is mandatory and should not be null.
     */
    @OneToOne
    @NotNull
    @JsonIgnore
    private Investment investment;

    /**
     * Returns a string representation of the transaction.
     * 
     * @return a string representation of the transaction.
     */
    @Override
    public String toString() {
        return "Transaction(id=" + this.getId() + ", user=" + this.getUser() + ", status=" + this.getStatus() + ")";
    }
}
