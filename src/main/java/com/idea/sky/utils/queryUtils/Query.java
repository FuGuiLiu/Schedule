package com.idea.sky.utils.queryUtils;

import com.idea.sky.utils.MagicNumberConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Query
 */
public class Query<T> extends QueryBase implements Serializable {

    /**
     * 方法  属性
     */
    private static final long serialVersionUID = 1930382256159908170L;
    /**
     * 属性
     * 方法  属性
     */
    private T data;

    /**
     * orderBy
     * 方法  属性
     */
    private String orderBy;

    /**
     * getData
     *
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * setData
     *
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getOrderBy
     *
     * @return
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * setOrderBy
     *
     * @param orderBy
     */
    public void setOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.isEmpty()) {
            this.orderBy = orderBy;
        } else {
            this.orderBy = "id";
        }
    }
}

/**
 * QueryBase
 * 传入pageSize,currentPage,total;计算start,end
 */
class QueryBase {

    private static int DEFAULT_PAGE_SIZE = com.idea.sky.utils.MagicNumberConstants.n20;

    /**
     * pageSize
     */

    protected int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * currentPage
     */
    protected int currentPage = com.idea.sky.utils.MagicNumberConstants.n1;
    /**
     * total
     */
    protected long total;

    /**
     * start
     */
    protected int start = com.idea.sky.utils.MagicNumberConstants.n0;

    /**
     * end
     */
    protected int end = com.idea.sky.utils.MagicNumberConstants.n20;

    /**
     * getTotal
     *
     * @return
     */

    public long getTotal() {
        return total;
    }

    /**
     * setTotal
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * getPageSize
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * setPageSize
     *
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > com.idea.sky.utils.MagicNumberConstants.n0) {
            this.pageSize = pageSize;
        }
        this.setStartAndEnd();
    }

    /**
     * getCurrentPage
     *
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * setCurrentPage
     *
     * @param currentPage
     */
    public void setCurrentPage(Integer currentPage) {
        if (currentPage != null && currentPage > com.idea.sky.utils.MagicNumberConstants.n0) {
            this.currentPage = currentPage;
        }
        this.setStartAndEnd();
    }

    /**
     * setStartAndEnd
     */
    protected void setStartAndEnd() {
        this.start = (this.getCurrentPage() - com.idea.sky.utils.MagicNumberConstants.n1) * this.getPageSize();
        if (this.start < com.idea.sky.utils.MagicNumberConstants.n0) {
            this.start = com.idea.sky.utils.MagicNumberConstants.n0;
        }
        this.end = this.getStart() + this.getPageSize() - com.idea.sky.utils.MagicNumberConstants.n1;
    }

    /**
     * getStart
     *
     * @return
     */
    public int getStart() {
        return start;
    }

    /**
     * setStart
     *
     * @param start
     */
    public void setStart(Integer start) {
        if (start != null && start >= com.idea.sky.utils.MagicNumberConstants.n0) {
            this.start = start;
        }
    }

    /**
     * getEnd
     *
     * @return
     */
    public int getEnd() {
        return end;
    }

    /**
     * setEnd
     *
     * @param end
     */
    public void setEnd(Integer end) {
        if (end != null && end >= com.idea.sky.utils.MagicNumberConstants.n0) {
            this.end = end;
        }
    }

    /**
     * hasNextPage
     *
     * @return
     */
    public boolean hasNextPage() {
        return getCurrentPage() < getTotalPage() - com.idea.sky.utils.MagicNumberConstants.n1;
    }

    /**
     * hasPreviousPage
     *
     * @return
     */
    public boolean hasPreviousPage() {
        return getCurrentPage() > com.idea.sky.utils.MagicNumberConstants.n1;
    }

    /**
     * getTotalPage
     *
     * @return
     */
    public long getTotalPage() {
        if (total % (long) pageSize == com.idea.sky.utils.MagicNumberConstants.n0)
            return total / (long) pageSize;
        else
            return total / (long) pageSize + MagicNumberConstants.n1;
    }

    /**
     * toString
     *
     * @return
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}


