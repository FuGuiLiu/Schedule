package com.idea.sky.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ResultList
 *
 * @param <T>
 * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
 */
public class ResultList<T> extends Result<T> {

    /**
     * serialVersionUID
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private static final long serialVersionUID = -8793824978794414238L;
    /**
     * datalist
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private Collection<T> datalist;
    /**
     * total
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private long total = MagicNumberConstants.n0;

    /**
     * DEFAULT_PAGE_SIZE
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private static int DEFAULT_PAGE_SIZE = MagicNumberConstants.n20;
    /**
     * pageSize
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private int pageSize;
    /**
     * start
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    private long start;

    /**
     * ResultList
     *
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public ResultList() {
        this(MagicNumberConstants.n0, MagicNumberConstants.n0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    /**
     * ResultList
     *
     * @param start
     * @param totalSize
     * @param pageSize
     * @param data
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public ResultList(long start, long totalSize, int pageSize, Collection<T> data) {
        this.pageSize = pageSize;
        this.start = start;
        this.total = totalSize;
        this.datalist = data;
    }

    /**
     * Collection
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public Collection<T> getDatalist() {
        return this.datalist;
    }

    /**
     * setDatalist
     *
     * @param data
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public void setDatalist(Collection<T> data) {
        this.datalist = data;
    }

    /**
     * getTotal
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public long getTotal() {
        return total;
    }

    /**
     * setTotal
     *
     * @param total
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * getTotalPageCount
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public long getTotalPageCount() {
        if (total % (long) pageSize == MagicNumberConstants.n0)
            return total / (long) pageSize;
        else
            return total / (long) pageSize + MagicNumberConstants.n1;
    }

    /**
     * getPageSize
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * getCurrentPageNo
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public long getCurrentPageNo() {
        return start / (long) pageSize + MagicNumberConstants.n1;
    }

    /**
     * hasNextPage
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public boolean hasNextPage() {
        return getCurrentPageNo() < getTotalPageCount() - MagicNumberConstants.n1;
    }

    /**
     * hasPreviousPage
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public boolean hasPreviousPage() {
        return getCurrentPageNo() > MagicNumberConstants.n1;
    }

    /**
     * getStartOfPage
     *
     * @param pageNo
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * getStartOfPage
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    /**
     * getPaginator
     *
     * @param currentPage
     * @param pageSize
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public Paginator getPaginator(Integer currentPage, Integer pageSize) {
        if (null == currentPage) {
            currentPage = MagicNumberConstants.n1;
        }
        if (null == pageSize) {
            pageSize = MagicNumberConstants.n20;
        }
        Paginator paginator = new Paginator(currentPage, pageSize);
        paginator.setTotal(total);
        paginator.generateView();
        return paginator;
    }

    /**
     * toString
     *
     * @return
     * @author yakirChen ~^o^~ <a href="http://pamirs.top">yakirchen.com</a>
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
