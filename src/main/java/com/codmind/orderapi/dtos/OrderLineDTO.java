package com.codmind.orderapi.dtos;

import com.codmind.orderapi.entity.Order;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDTO {

    private Long id;
    private ProductDTO product;
    private Double price;
    private Double quantity;
    private Double total;
}
