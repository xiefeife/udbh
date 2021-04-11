package com.last.demo.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/21 18:29
 * @ClassName:MyPageInfo
 */
public class MyPageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private long total;
    /**
     * 更改原返回字段List为rows
     */
    private List<T> list;

    public static final int DEFAULT_NAVIGATE_PAGES = 8;

    private int pageNum;
    private int pageSize;
    private int size;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private long startRow;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private long endRow;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private int pages;

    private int prePage;
    private int nextPage;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private boolean isFirstPage;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private boolean isLastPage;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private boolean hasPreviousPage;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private boolean hasNextPage;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private int navigatePages;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private int[] navigatepageNums;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private int navigateFirstPage;
    /**
     * json序列化忽略该字段
     */
    @JsonIgnore
    private int navigateLastPage;

    public MyPageInfo() {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
    }

    public MyPageInfo(List<T> list) {
        this(list, 8);
    }

    public MyPageInfo(List<T> list, int navigatePages) {
        this.list = list;
        if (list instanceof Page) {
            this.total = ((Page) list).getTotal();
        } else {
            this.total = (long) list.size();
        }

        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.size = page.size();
            if (this.size == 0) {
                this.startRow = 0L;
                this.endRow = 0L;
            } else {
                this.startRow = page.getStartRow() + 1L;
                this.endRow = this.startRow - 1L + (long) this.size;
            }
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
            this.size = list.size();
            this.startRow = 0L;
            this.endRow = list.size() > 0 ? (long) (list.size() - 1) : 0L;
        }

        if (list instanceof Collection) {
            this.calcByNavigatePages(navigatePages);
        }

    }

    public static <T> MyPageInfo<T> of(List<T> list) {
        return new MyPageInfo(list);
    }

    public static <T> MyPageInfo<T> of(List<T> list, int navigatePages) {
        return new MyPageInfo(list, navigatePages);
    }

    public void calcByNavigatePages(int navigatePages) {
        this.setNavigatePages(navigatePages);
        this.calcNavigatepageNums();
        this.calcPage();
        this.judgePageBoudary();
    }

    private void calcNavigatepageNums() {
        int i;
        if (this.pages <= this.navigatePages) {
            this.navigatepageNums = new int[this.pages];

            for (i = 0; i < this.pages; ++i) {
                this.navigatepageNums[i] = i + 1;
            }
        } else {
            this.navigatepageNums = new int[this.navigatePages];
            i = this.pageNum - this.navigatePages / 2;
            int endNum = this.pageNum + this.navigatePages / 2;
            if (i < 1) {
                i = 1;

                for (i = 0; i < this.navigatePages; ++i) {
                    this.navigatepageNums[i] = i++;
                }
            } else if (endNum > this.pages) {
                endNum = this.pages;

                for (i = this.navigatePages - 1; i >= 0; --i) {
                    this.navigatepageNums[i] = endNum--;
                }
            } else {
                for (i = 0; i < this.navigatePages; ++i) {
                    this.navigatepageNums[i] = i++;
                }
            }
        }

    }

    private void calcPage() {
        if (this.navigatepageNums != null && this.navigatepageNums.length > 0) {
            this.navigateFirstPage = this.navigatepageNums[0];
            this.navigateLastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
            if (this.pageNum > 1) {
                this.prePage = this.pageNum - 1;
            }

            if (this.pageNum < this.pages) {
                this.nextPage = this.pageNum + 1;
            }
        }

    }

    private void judgePageBoudary() {
        this.isFirstPage = this.pageNum == 1;
        this.isLastPage = this.pageNum == this.pages || this.pages == 0;
        this.hasPreviousPage = this.pageNum > 1;
        this.hasNextPage = this.pageNum < this.pages;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getStartRow() {
        return this.startRow;
    }

    public void setStartRow(long startRow) {
        this.startRow = startRow;
    }

    public long getEndRow() {
        return this.endRow;
    }

    public void setEndRow(long endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return this.prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return this.isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return this.isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return this.navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return this.navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigateFirstPage() {
        return this.navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return this.navigateLastPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 自定义方法替换当前List，适用于更换信息的操作。
     *
     * @param replaceList 要替换的集合
     * @param <E>         转换对象
     * @return 替完后的结果
     */
    public <E> MyPageInfo<E> replace(List<E> replaceList) {
        MyPageInfo replacePageInfo = this;
        replacePageInfo.setList(replaceList);
        return replacePageInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PageInfo{");
        sb.append("pageNum=").append(this.pageNum);
        sb.append(", pageSize=").append(this.pageSize);
        sb.append(", size=").append(this.size);
        sb.append(", startRow=").append(this.startRow);
        sb.append(", endRow=").append(this.endRow);
        sb.append(", total=").append(this.total);
        sb.append(", pages=").append(this.pages);
        sb.append(", list=").append(this.list);
        sb.append(", prePage=").append(this.prePage);
        sb.append(", nextPage=").append(this.nextPage);
        sb.append(", isFirstPage=").append(this.isFirstPage);
        sb.append(", isLastPage=").append(this.isLastPage);
        sb.append(", hasPreviousPage=").append(this.hasPreviousPage);
        sb.append(", hasNextPage=").append(this.hasNextPage);
        sb.append(", navigatePages=").append(this.navigatePages);
        sb.append(", navigateFirstPage=").append(this.navigateFirstPage);
        sb.append(", navigateLastPage=").append(this.navigateLastPage);
        sb.append(", navigatepageNums=");
        if (this.navigatepageNums == null) {
            sb.append("null");
        } else {
            sb.append('[');

            for (int i = 0; i < this.navigatepageNums.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(this.navigatepageNums[i]);
            }

            sb.append(']');
        }

        sb.append('}');
        return sb.toString();
    }
}
