package br.com.example.mfstore.payment.domain;

import br.com.example.mfstore.order.domain.Order;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_payment")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    private Instant paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    public void authorize() {
        if (status != PaymentStatus.PENDING)
            throw new IllegalStateException("Only PENDING payments can be authorized.");
        this.status = PaymentStatus.AUTHORIZED;
    }

    public void capture() {
        if (status != PaymentStatus.AUTHORIZED)
            throw new IllegalStateException("Only AUTHORIZED payments can be captured.");
        this.status = PaymentStatus.CAPTURED;
        this.paidAt = Instant.now();
    }

    public void cancel() {
        if (status == PaymentStatus.CAPTURED || status == PaymentStatus.REFUNDED)
            throw new IllegalStateException("Cannot cancel a payment with status: " + status);
        this.status = PaymentStatus.CANCELLED;
    }

    public void refund() {
        if (status != PaymentStatus.CAPTURED)
            throw new IllegalStateException("Only CAPTURED payments can be refunded.");
        this.status = PaymentStatus.REFUNDED;
    }
}