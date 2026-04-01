package br.com.example.mfstore.catalog.domain;

import br.com.example.mfstore.cart.domain.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_product")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String shortDescription;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String longDescription;

    @Column(precision = 19, scale = 3)
    private BigDecimal price;

    @Column(unique = true, length = 10)
    private String sku;

    @Column(precision = 19, scale = 3)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    private String imgUrl = "";

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProductCategory> categories = new HashSet<>();
}