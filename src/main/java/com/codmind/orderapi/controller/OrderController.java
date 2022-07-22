package com.codmind.orderapi.controller;

import com.codmind.orderapi.converters.OrderConverter;
import com.codmind.orderapi.dtos.OrderDTO;
import com.codmind.orderapi.entity.Order;
import com.codmind.orderapi.services.OrderService;
import com.codmind.orderapi.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private static final OrderConverter orderConverter = new OrderConverter();

    @Autowired
    private OrderService orderService;

    @GetMapping(value="/orders")
    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
         @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
         @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ){

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Order> orders = orderService.findAll(page);
        return new WrapperResponse(true, "success", orderConverter.fromEntity(orders))
            .createResponse();
    }
    @GetMapping(value="/orders/{id}")
    public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable(name="id") Long id){

        Order order = orderService.findById(id);
        return new WrapperResponse(true, "success", orderConverter.fromEntity(order))
                .createResponse();
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> create(@RequestBody OrderDTO order){
        Order newOrder = orderService.save(orderConverter.fromDTO(order));
        return new WrapperResponse(true, "success", orderConverter.fromEntity(newOrder))
                .createResponse();
    }

    @PutMapping(value = "/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> update(@RequestBody OrderDTO order){
        Order newOrder =  orderService.save(orderConverter.fromDTO(order));
        return new WrapperResponse(true, "success", orderConverter.fromEntity(newOrder))
                .createResponse();
    }
    @DeleteMapping(value = "/orders/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") Long id){
        orderService.delete(id);
        return new WrapperResponse(true, "success", null)
                .createResponse();
    }
}
