package com.rasyid.projectprobation.base.mapper;

import com.rasyid.projectprobation.base.service.GenericMapper;
import com.rasyid.projectprobation.entity.SaleOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends GenericMapper<SaleOrder> {
    @Insert("INSERT INTO `sale_order` (order_name, order_user) VALUES (#{orderName}, #{orderUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(SaleOrder saleOrder);

    @Select("SELECT * FROM sale_order")
    List<SaleOrder> getAllData();
}
