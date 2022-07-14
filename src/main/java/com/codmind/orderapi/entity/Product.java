package com.codmind.orderapi.entity;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name="PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME", nullable = false, length = 100)
    private String name;

    @Column(name="PRICE", nullable = false)
    private Double price;
}
