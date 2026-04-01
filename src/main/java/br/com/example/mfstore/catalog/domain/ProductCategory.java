package br.com.example.mfstore.catalog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_product_category")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private Integer displayOrder;

    private Boolean mainCategory;
}