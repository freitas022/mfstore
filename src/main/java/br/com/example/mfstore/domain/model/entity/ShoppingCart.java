package br.com.example.mfstore.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_shopping_cart")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int qty) {
        items.stream()
                .filter(i -> i.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        existing -> existing.setQuantity(existing.getQuantity() + qty),
                        () -> items.add(CartItem.builder()
                                .product(product)
                                .quantity(qty)
                                .unitPrice(product.getPrice())
                                .cart(this)
                                .build())
                );
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public void updateQty(Product product, int qty) {
        items.stream()
                .filter(i -> i.getProduct().equals(product))
                .findFirst()
                .ifPresent(i -> i.setQuantity(qty));
    }

    public void clear() {
        items.clear();
    }
}