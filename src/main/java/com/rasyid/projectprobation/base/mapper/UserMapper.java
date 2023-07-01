package com.rasyid.projectprobation.base.mapper;

import com.rasyid.projectprobation.base.service.GenericMapper;
import com.rasyid.projectprobation.entity.Order;
import com.rasyid.projectprobation.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper extends GenericMapper<User> {
    @Select("SELECT * FROM _user WHERE email = #{email}")
    Optional<User> findByEmail(String email);

}
