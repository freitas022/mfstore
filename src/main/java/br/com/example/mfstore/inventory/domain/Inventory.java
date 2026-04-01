package br.com.example.mfstore.inventory.domain;

import br.com.example.mfstore.catalog.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_inventory")
@NoArgsConstructor @AllArgsConstructor
@Getter
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Version
    private Long version;

    @Column(nullable = false)
    private Integer quantityAvailable;

    @Column(nullable = false)
    private Integer reorderLevel;

    /**
     * Item added to cart
     */
    public void reserve(int qty) {
        if (qty > quantityAvailable)
            throw new IllegalArgumentException(
                    "Insufficient stock for product id %d: requested %d, available %d"
                            .formatted(product.getId(), qty, quantityAvailable));
        this.quantityAvailable -= qty;
    }

    /**
     * Abandoned cart / item removed
     */
    public void release(int qty) {
        this.quantityAvailable += qty;
    }

    /**
     * Order confirmed
     */
    public void decrease(int qty) {
        reserve(qty);
    }

    /**
     *  Inventory entry
     */
    public void increase(int qty) { // entrada de mercadoria
        this.quantityAvailable += qty;
    }

    public boolean needsReorder() {
        return quantityAvailable <= reorderLevel;
    }
}