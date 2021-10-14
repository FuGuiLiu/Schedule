package com.idea.sky.dao;

import com.idea.sky.entity.SystemJob;
import io.swagger.annotations.ApiModelProperty;

public interface SystemJobDAO {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(SystemJob record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(SystemJob record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    SystemJob selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(SystemJob record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(SystemJob record);
}