package br.com.goldinvesting.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @OneToOne
    @NotNull
    @JsonIgnore
    private Investment investment;

    public String toString() {
        return "Transaction(id=" + this.getId() + ", user=" + this.getUser() + ", status=" + this.getStatus() + ")";
    }
}
