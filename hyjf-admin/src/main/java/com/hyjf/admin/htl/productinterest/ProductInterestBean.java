package com.hyjf.admin.htl.productinterest;

import java.io.Serializable;
import java.util.List;

import com.hyjf.common.paginator.Paginator;
import com.hyjf.mybatis.model.customize.ProductInterestCustomize;

/**
  * @ClassName: ProductInterestCustomizeBean
  * @Description: TODO
  * @author Michael
  * @date 2015年11月27日 下午2:38:14
 */
public class ProductInterestBean extends ProductInterestCustomize implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	private List<ProductInterestCustomize> recordList;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

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

	public List<ProductInterestCustomize> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<ProductInterestCustomize> recordList) {
		this.recordList = recordList;
	}

}
