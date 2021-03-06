package com.hyjf.admin.exception.transferexception;

import java.io.Serializable;
import java.util.List;

import com.hyjf.common.paginator.Paginator;
import com.hyjf.mybatis.model.customize.admin.AdminTransferExceptionLogCustomize;

/**
 * 转账异常Form Bean
 */
public class TransferExceptionLogBean extends AdminTransferExceptionLogCustomize implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	/**
     * 检索条件 有效时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 有效时间结束
     */
    private String timeEndSrch;
    
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;
	/**
	 * 列表
	 */
	private List<AdminTransferExceptionLogCustomize> recordList;
	
	
	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public List<AdminTransferExceptionLogCustomize> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<AdminTransferExceptionLogCustomize> recordList) {
		this.recordList = recordList;
	}

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

}
