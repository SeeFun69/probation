package com.rasyid.projectprobation.service.impl;

import com.rasyid.projectprobation.base.mapper.OrderMapper;
import com.rasyid.projectprobation.entity.Order;
import com.rasyid.projectprobation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void createOrder(Order order) {
        orderMapper.insert(order);
    }
}
