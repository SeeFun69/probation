package com.rasyid.projectprobation.service;

import com.rasyid.projectprobation.dto.FlashSaleReq;
import com.rasyid.projectprobation.dto.OrderDTO;
import com.rasyid.projectprobation.entity.SaleOrder;

import java.util.List;

public interface OrderService {
    void createOrder(SaleOrder saleOrder);

    String flashSale(FlashSaleReq request, String username);

    String saleReguler(FlashSaleReq request, String username);

    List<OrderDTO> getAllOrder();
}
