package br.com.example.mfstore.customer.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_account")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void suspend() {
        this.status = AccountStatus.SUSPENDED;
    }
}