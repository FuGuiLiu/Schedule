package com.idea.sky.utils.queryUtils;

import com.idea.sky.utils.MagicNumberConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static java.lang.Math.abs;

/**
 * MergeQueryBase 基类
 *
 * @author <a href="mailto:sanbian@pamirs.top">Sanbian</a>
 * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
 * @version 1.0
 * @since 16/11/28 下午10:17
 * <p>
 * 方法  属性
 */
public class MergeQueryBase {

    /**
     * pageSize
     * 属性
     */
    protected int pageSize = MagicNumberConstants.n20;
    /**
     * currentPage
     * 属性
     */
    protected int currentPage = MagicNumberConstants.n1;
    /**
     * total
     * 属性
     */
    protected long total;

    /**
     * firstTotal
     * 属性
     */
    protected long firstTotal = MagicNumberConstants.n0;
    /**
     * secondTotal
     * 属性
     */
    protected long secondTotal = MagicNumberConstants.n0;

    /**
     * midPage
     * 属性
     */
    protected int midPage;
    /**
     * midPageSize
     * 属性
     */
    protected int midPageSize;

    /**
     * start
     * 属性
     */
    protected int start = MagicNumberConstants.n0;
    /**
     * end
     * 属性
     */
    protected int end = MagicNumberConstants.n20;

    /**
     * set get 方法
     * <p>
     * getFirstTotal
     *
     * @return
     */
    public long getFirstTotal() {
        return firstTotal;
    }

    /**
     * set get 方法
     * <p>
     * setFirstTotal
     *
     * @param firstTotal
     */
    public void setFirstTotal(long firstTotal) {
        this.firstTotal = firstTotal;
    }

    /**
     * set get 方法
     * <p>
     * getSecondTotal
     *
     * @return
     */
    public long getSecondTotal() {
        return secondTotal;
    }

    /**
     * set get 方法
     * <p>
     * setSecondTotal
     *
     * @param secondTotal
     */
    public void setSecondTotal(long secondTotal) {
        this.secondTotal = secondTotal;
        this.setMidPage();
        this.setMidPageSize();
        this.setStartAndEnd();
    }

    /**
     * set get 方法
     * <p>
     * getMidPage
     *
     * @return
     */
    public int getMidPage() {
        return midPage;
    }

    /**
     * set get 方法
     * <p>
     * setMidPage
     */
    public void setMidPage() {
        this.midPage = (int) (abs(firstTotal / this.getPageSize()) + MagicNumberConstants.n1);
    }

    /**
     * set get 方法
     * <p>
     * getMidPageSize
     *
     * @return
     */
    public int getMidPageSize() {
        return midPageSize;
    }

    /**
     * set get 方法
     * <p>
     * setMidPageSize
     */
    public void setMidPageSize() {
        this.midPageSize = (int) (firstTotal % this.getPageSize());
    }

    /**
     * set get 方法
     * <p>
     * getTotal
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * set get 方法
     * <p>
     * setTotal
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = this.getFirstTotal() + this.getSecondTotal();
    }

    /**
     * set get 方法
     * <p>
     * getPageSize
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * set get 方法
     * <p>
     * setPageSize
     *
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > MagicNumberConstants.n0) {
            this.pageSize = pageSize;
        }
        this.setStartAndEnd();
    }

    /**
     * set get 方法
     * <p>
     * getCurrentPage
     *
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * set get 方法
     * <p>
     * setCurrentPage
     *
     * @param currentPage
     */
    public void setCurrentPage(Integer currentPage) {
        if (currentPage != null && currentPage > MagicNumberConstants.n0) {
            this.currentPage = currentPage;
        }
        this.setStartAndEnd();
    }

    protected void setStartAndEnd() {
        this.start = (this.getCurrentPage() - MagicNumberConstants.n1) * this.getPageSize();
        if (this.start < MagicNumberConstants.n0){
            this.start = MagicNumberConstants.n0;
        }
        if (this.getSecondTotal() > MagicNumberConstants.n0) {
            if (this.getCurrentPage() < this.getMidPage()) {
                this.pageSize = MagicNumberConstants.n0;
            } else if (this.getCurrentPage() == this.getMidPage()) {
                this.start = MagicNumberConstants.n0;
                this.pageSize = this.getPageSize() - this.getMidPageSize();
            } else {
                // TODO: 16/11/28  int 和 long 的转换，会不会因为溢出出错
                this.start = (int) (this.getStart() - this.getFirstTotal());
            }
        }
        this.end = this.getStart() + this.getPageSize() - MagicNumberConstants.n1;
    }

    /**
     * set get 方法
     * <p>
     * getStart
     *
     * @return
     */
    public int getStart() {
        return start;
    }

    /**
     * set get 方法
     * <p>
     * setStart
     *
     * @param start
     */
    public void setStart(Integer start) {
        if (start != null && start >= MagicNumberConstants.n0) {
            this.start = start;
        }
    }

    /**
     * set get 方法
     * <p>
     * getEnd
     *
     * @return
     */
    public int getEnd() {
        return end;
    }

    /**
     * set get 方法
     * <p>
     * setEnd
     *
     * @param end
     */
    public void setEnd(Integer end) {
        if (end != null && end >= MagicNumberConstants.n0) {
            this.end = end;
        }
    }

    /**
     * set get 方法
     * <p>
     * hasNextPage
     *
     * @return
     */
    public boolean hasNextPage() {
        return getCurrentPage() < getTotalPage() - MagicNumberConstants.n1;
    }

    /**
     * set get 方法
     * <p>
     * hasPreviousPage
     *
     * @return
     */
    public boolean hasPreviousPage() {
        return getCurrentPage() > MagicNumberConstants.n1;
    }

    /**
     * set get 方法
     * <p>
     * getTotalPage
     *
     * @return
     */
    public long getTotalPage() {
        if (total % (long) pageSize == MagicNumberConstants.n0)
            return total / (long) pageSize;
        else
            return total / (long) pageSize + MagicNumberConstants.n1;
    }

    /**
     * set get 方法
     * <p>
     * toString
     *
     * @return
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
