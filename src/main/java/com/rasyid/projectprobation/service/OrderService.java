package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.entity.Order;

public interface OrderService {
    void createOrder(Order order);

    String flashSale(FlashSaleReq request, String username);
}
