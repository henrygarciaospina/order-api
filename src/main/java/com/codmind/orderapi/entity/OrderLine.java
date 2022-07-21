package com.codmind.orderapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="ORDER_LINES")
public class OrderLine {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FK_ORDER", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "FK_PRODUCT", nullable = false)
    private Product product;

    @Column(name="PRICE", nullable = false)
    private Double price;

    @Column(name="QUANTITY", nullable = false)
    private Double quantity;

    @Column(name="TOTAL", nullable = false)
    private Double total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderLine orderLine = (OrderLine) o;
        return id != null && Objects.equals(id, orderLine.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}