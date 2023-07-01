package com.rasyid.projectprobation.base.mapper;

import com.rasyid.projectprobation.base.service.GenericMapper;
import com.rasyid.projectprobation.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper extends GenericMapper<User> {

    @Insert("INSERT INTO user (firstname, lastname, email, password, role) " +
            "VALUES (#{firstname}, #{lastname}, #{email}, #{password}, #{role})")
    void insertUser(User user);

    @Select("SELECT * FROM user WHERE email = #{email}")
    Optional<User> findByEmail(String email);

}
