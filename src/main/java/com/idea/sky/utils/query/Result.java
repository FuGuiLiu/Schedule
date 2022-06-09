package com.idea.sky.utils.query;

import com.idea.sky.utils.MagicNumberConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class Result<T> extends ErrorResult implements Serializable {

    private static final long serialVersionUID = -8722768053248374040L;

    private boolean success = true;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        if (!super.hasError()) {
            return "success=" + success + "\n" + "data=" + data;
        }
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public void setError(int code, String name, String message) {
        super.setError(code, name, message);
        this.setSuccess(false);
    }
}


/**
 * ErrorResult
 */
class ErrorResult implements Serializable {

    private static final long serialVersionUID = 4521021961844996809L;

    /**
     * GENERAL_CODE
     */
    public static final int GENERAL_CODE = 999;

    /**
     * GENERAL_NAME
     */
    public static final String GENERAL_NAME = "SERVICE_ERROR";

    /**
     * errorCode
     */
    private int errorCode;

    /**
     * errorName
     */
    private String errorName;

    /**
     * errorMessage
     */
    private String errorMessage;


    /**
     * ErrorResult
     */
    public ErrorResult() {
        this(GENERAL_CODE, GENERAL_NAME, null);
    }


    /**
     * ErrorResult
     *
     * @param code
     * @param name
     * @param message
     */
    public ErrorResult(int code, String name, String message) {
        this.setError(code, name, message);
    }


    /**
     * setError
     *
     * @param code
     * @param name
     * @param message
     */
    public void setError(int code, String name, String message) {
        this.errorCode = code;
        this.errorName = name;
        this.errorMessage = message;
    }


    /**
     * setError
     *
     * @param name
     * @param message
     */
    public void setError(String name, String message) {
        this.setError(GENERAL_CODE, name, message);
    }


    /**
     * hasError
     *
     * @return
     */
    public boolean hasError() {
        return StringUtils.isNotBlank(this.errorMessage) && StringUtils.isNotBlank(this.errorName);
    }


    /**
     * getErrorCode
     *
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * setErrorCode
     *
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * getErrorName
     *
     * @return
     */
    public String getErrorName() {
        return errorName;
    }

    /**
     * setErrorName
     *
     * @param errorName
     */
    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    /**
     * getErrorMessage
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * setErrorMessage
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

/**
 * 分页模型
 *
 * <p>
 * 用于查询列表时进行分页
 */
class Paginator {

    /**
     * 每页显示条数
     */
    private int limit = MagicNumberConstants.n20;

    /**
     * 起始行号
     */
    private long start = MagicNumberConstants.n0;

    /**
     * 结束行号
     */
    private long end = MagicNumberConstants.n20;

    /**
     * 总数
     */
    private long total = MagicNumberConstants.n0;

    /**
     * 时间戳
     */
    private Date timestamp;

    /**
     * 当前页
     */
    private long index = MagicNumberConstants.n1;

    /**
     * 总页数
     */
    private long pageNum = MagicNumberConstants.n0;

    /**
     * ...隔开的中间页码数量
     */
    private int breakpage = MagicNumberConstants.n5;

    /**
     * ...隔开的中间页码序列中间位置，从零开始
     */
    private long currentposition = MagicNumberConstants.n2;

    /**
     * ...隔开的两端页码数量
     */
    private int breakspace = MagicNumberConstants.n2;

    /**
     * 是否用...断开的判断数量
     */
    private int maxspace = MagicNumberConstants.n4;

    /**
     * prevnum
     */
    private long prevnum;

    /**
     * nextnum
     */
    private long nextnum;

    /**
     * Paginator
     */
    public Paginator() {

    }

    /**
     * Paginator
     * 初始化分页类，生成可供查询使用的分页类
     *
     * @param index
     * @param limit
     */
    public Paginator(int index, int limit) {
        if (index < MagicNumberConstants.n0) {
            this.setIndex(MagicNumberConstants.n1);
        }
        /**
         * setIndex
         *
         */
        this.setIndex(index);
        /**
         * setLimit
         *
         */
        this.setLimit(limit);
        /**
         * setStart
         *
         */
        this.setStart((this.getIndex() - MagicNumberConstants.n1) * this.getLimit());
        /**
         * setEnd
         *
         */
        this.setEnd(this.getIndex() * this.getLimit());
    }

    /**
     * generateView
     * 完成查询得道结果后，使用此方法生成供页面展示分页控件的分页类
     */
    public void generateView() {
        this.setPageNum(new Double(Math.ceil(new Double(total) / this.getLimit())).intValue());

        if (this.getIndex() > this.getPageNum()) {
            this.setIndex(this.getPageNum());
        }

        /**
         * setPrevnum
         *
         */
        this.setPrevnum(this.getIndex() - this.getCurrentposition());
        /**
         * setNextnum
         */
        this.setNextnum(this.getIndex() + this.getCurrentposition());
        /**
         * getPrevnum
         */
        if (this.getPrevnum() < MagicNumberConstants.n1) {
            this.setPrevnum(MagicNumberConstants.n1);
        }
        /**
         * getNextnum
         */
        if (this.getNextnum() > this.getPageNum()) {
            this.setNextnum(this.getPageNum());
        }
        /**
         * setEnd
         */
        this.setEnd(Math.min(this.getIndex() * this.getLimit(), this.getTotal()));
    }

    /**
     * getLimit
     *
     * @return
     */
    public int getLimit() {
        return limit;
    }

    /**
     * setLimit
     *
     * @param limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
        this.setStart((this.getIndex() - MagicNumberConstants.n1) * this.getLimit());
        this.setEnd(this.getIndex() * this.getLimit());
    }

    /**
     * getStart
     *
     * @return
     */
    public long getStart() {
        return start;
    }

    /**
     * setStart
     *
     * @param start
     */
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * getEnd
     *
     * @return
     */
    public long getEnd() {
        return end;
    }

    /**
     * setEnd
     *
     * @param end
     */
    public void setEnd(long end) {
        this.end = end;
    }

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
     * getIndex
     *
     * @return
     */
    public Long getIndex() {
        return index;
    }

    /**
     * setIndex
     *
     * @param index
     */
    public void setIndex(long index) {
        if (index < MagicNumberConstants.n1) {
            index = MagicNumberConstants.n1;
        }
        this.index = index;
        this.setStart((this.getIndex() - MagicNumberConstants.n1) * this.getLimit());
        this.setEnd(this.getIndex() * this.getLimit());
    }

    /**
     * getPageNum
     *
     * @return
     */
    public long getPageNum() {
        return pageNum;
    }

    /**
     * setPageNum
     *
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * getBreakpage
     *
     * @return
     */
    public int getBreakpage() {
        return breakpage;
    }

    /**
     * setBreakpage
     *
     * @param breakpage
     */
    public void setBreakpage(int breakpage) {
        this.breakpage = breakpage;
    }

    /**
     * getCurrentposition
     *
     * @return
     */
    public long getCurrentposition() {
        return currentposition;
    }

    /**
     * setCurrentposition
     *
     * @param currentposition
     */
    public void setCurrentposition(int currentposition) {
        this.currentposition = currentposition;
    }

    /**
     * getBreakspace
     *
     * @return
     */
    public int getBreakspace() {
        return breakspace;
    }

    /**
     * setBreakspace
     *
     * @param breakspace
     */
    public void setBreakspace(int breakspace) {
        this.breakspace = breakspace;
    }

    /**
     * getMaxspace
     *
     * @return
     */
    public int getMaxspace() {
        return maxspace;
    }

    /**
     * setMaxspace
     *
     * @param maxspace
     */
    public void setMaxspace(int maxspace) {
        this.maxspace = maxspace;
    }

    /**
     * getPrevnum
     *
     * @return
     */
    public long getPrevnum() {
        return prevnum;
    }

    /**
     * setPrevnum
     *
     * @param prevnum
     */
    public void setPrevnum(long prevnum) {
        this.prevnum = prevnum;
    }

    /**
     * getNextnum
     *
     * @return
     */
    public long getNextnum() {
        return nextnum;
    }

    /**
     * setNextnum
     *
     * @param nextnum
     */
    public void setNextnum(long nextnum) {
        this.nextnum = nextnum;
    }

    /**
     * isBreakLeft
     *
     * @return
     */
    public boolean isBreakLeft() {
        return this.getPrevnum() - this.getBreakspace() > this.getMaxspace() ? true : false;
    }

    /**
     * isBreakRight
     *
     * @return
     */
    public boolean isBreakRight() {
        return this.getPageNum() - this.getBreakspace() - this.getNextnum() + 1 > this.getMaxspace() ? true : false;
    }

    /**
     * getTimestamp
     *
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * setTimestamp
     *
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}


