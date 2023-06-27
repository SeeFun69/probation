package com.rasyid.projectprobation.base.mapper;

import com.rasyid.projectprobation.base.service.GenericMapper;
import com.rasyid.projectprobation.dto.StockDTO;
import com.rasyid.projectprobation.entity.Stock;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StockMapper extends GenericMapper<Stock> {

    @Insert("INSERT INTO stock(name, stock) " +
            "VALUES (#{name}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertStock(Stock stock);

    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    @Select("SELECT * FROM stock WHERE id = #{id}")
    StockDTO findById(Long id);
}
