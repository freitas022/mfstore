package br.com.example.mfstore.customer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_address")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 8)
    private String zipCode;

    private String complement;
}