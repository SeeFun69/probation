package com.rasyid.projectprobation.base.service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface GenericMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
