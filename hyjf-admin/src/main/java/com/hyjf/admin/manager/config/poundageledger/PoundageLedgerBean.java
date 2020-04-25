package com.hyjf.admin.manager.config.poundageledger;

import java.util.List;

import com.hyjf.common.paginator.Paginator;
import com.hyjf.mybatis.model.customize.poundageledger.PoundageLedgerCustomize;

public class PoundageLedgerBean extends PoundageLedgerCustomize {

    private static final long serialVersionUID = 2089381476055766997L;
    private List<PoundageLedgerCustomize> recordList;
    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    private String fid; //功能菜单id

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public List<PoundageLedgerCustomize> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<PoundageLedgerCustomize> recordList) {
        this.recordList = recordList;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
