package com.rasyid.projectprobation.base.mapper;

import com.rasyid.projectprobation.base.service.GenericMapper;
import com.rasyid.projectprobation.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper extends GenericMapper<Order> {
    @Insert("INSERT INTO `t_order` (order_name, order_user) VALUES (#{orderName}, #{orderUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(Order order);

}
