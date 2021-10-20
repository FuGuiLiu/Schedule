package com.idea.sky.utils.queryUtils;

import java.io.Serializable;

/**
 * @param <T>
 * @author <a href="mailto:sanbian@pamirs.top">Sanbian</a>
 * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
 * @version 1.0
 * @since 16/11/28 下午10:16
 */
public class MergeQuery<T> extends MergeQueryBase implements Serializable {
    private static final long serialVersionUID = -6443186486048799474L;

    /**
     * data
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private T data;

    /**
     * orderBy
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private String orderBy;

    /**
     * getData
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public T getData() {
        return data;
    }

    /**
     * setData
     *
     * @param data
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getOrderBy
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * setOrderBy
     *
     * @param orderBy
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
