package br.com.example.mfstore.shipment.domain;

import br.com.example.mfstore.order.domain.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_shipment")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @CreationTimestamp
    private Instant createdAt;

    private String trackingCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ShipmentStatus status = ShipmentStatus.PENDING;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal freightCost;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    public void dispatch(String trackingCode) {
        if (status != ShipmentStatus.PENDING)
            throw new IllegalStateException("Only PENDING shipments can be dispatched.");
        this.trackingCode = trackingCode;
        this.status = ShipmentStatus.DISPATCHED;
    }

    public void deliver() {
        if (status != ShipmentStatus.ON_THE_ROAD)
            throw new IllegalStateException("Only ON_THE_ROAD shipments can be delivered.");
        this.status = ShipmentStatus.DELIVERED;
    }

    public String track() {
        if (trackingCode == null)
            throw new IllegalStateException("Shipment has no tracking code yet.");
        return trackingCode;
    }
}