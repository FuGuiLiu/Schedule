package com.idea.sky.service.impl;

import com.idea.sky.entity.SystemJob;
public interface SystemJobService{


    int deleteByPrimaryKey(Long id);

    int insert(SystemJob record);

    int insertSelective(SystemJob record);

    SystemJob selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemJob record);

    int updateByPrimaryKey(SystemJob record);

}
