package com.codmind.orderapi.services;

import com.codmind.orderapi.entity.Order;
import com.codmind.orderapi.entity.OrderLine;
import com.codmind.orderapi.exceptions.GeneralServiceException;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.exceptions.ValidateServiceException;
import com.codmind.orderapi.repository.OrderLineRepository;
import com.codmind.orderapi.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    public List<Order> findAll(Pageable page){
        try {
             return orderRepository.findAll(page).toList();
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public Order findById(Long id) {
        try {
            return orderRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("La orden ${id} no existe"));
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
    public void delete(Long id){
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("La orden no existe"));
            orderRepository.delete(order);
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Order save(Order order){
        try {
            //Create
            order.getLines().forEach(line -> line.setOrder(order));

            if(order.getId() == null ){
               order.setRegDate(LocalDateTime.now());
               return orderRepository.save(order);
            }
            //Update
            Order saveOrder = orderRepository.findById(order.getId())
                    .orElseThrow(() -> new NoDataFoundException("La orden no existe "));
            order.setRegDate(saveOrder.getRegDate());

            List<OrderLine> deletedLines = saveOrder.getLines();
            deletedLines.removeAll(order.getLines());
            orderLineRepository.deleteAll(deletedLines);

            return orderRepository.save(order);
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
