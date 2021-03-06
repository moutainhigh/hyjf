/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mongo.hgdatareport.entity;

import com.hyjf.mongo.hgdatareport.base.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.*;
import java.util.List;

/**
 * 理财计划
 * @author jun
 */
@Document(collection = "ht_bifa_hjh_planinfo")
public class BifaHjhPlanInfoEntity extends BaseHgDataReportEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String product_reg_type;
    private String product_name;
    private String product_mark;
    private String plan_raise_amount;
    private String rate;
    private String term_type;
    private String term;
    private String isshow;
    private String remark;
    private String amount_limmts;
    private String amount_limmtl;
    private String borrowamt;
    private String begindate;
    private String enddate;
    private String red_rate;
    private String source_product_url;

    private List<BifaBorrowInfoBean> borrowerlist;

    public String getProduct_reg_type() {
        return product_reg_type;
    }

    public void setProduct_reg_type(String product_reg_type) {
        this.product_reg_type = product_reg_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_mark() {
        return product_mark;
    }

    public void setProduct_mark(String product_mark) {
        this.product_mark = product_mark;
    }

    public String getPlan_raise_amount() {
        return plan_raise_amount;
    }

    public void setPlan_raise_amount(String plan_raise_amount) {
        this.plan_raise_amount = plan_raise_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTerm_type() {
        return term_type;
    }

    public void setTerm_type(String term_type) {
        this.term_type = term_type;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getRed_rate() {
        return red_rate;
    }

    public void setRed_rate(String red_rate) {
        this.red_rate = red_rate;
    }

    public String getSource_product_url() {
        return source_product_url;
    }

    public void setSource_product_url(String source_product_url) {
        this.source_product_url = source_product_url;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAmount_limmts() {
        return amount_limmts;
    }

    public void setAmount_limmts(String amount_limmts) {
        this.amount_limmts = amount_limmts;
    }

    public String getAmount_limmtl() {
        return amount_limmtl;
    }

    public void setAmount_limmtl(String amount_limmtl) {
        this.amount_limmtl = amount_limmtl;
    }

    public String getBorrowamt() {
        return borrowamt;
    }

    public void setBorrowamt(String borrowamt) {
        this.borrowamt = borrowamt;
    }

    public List<BifaBorrowInfoBean> getBorrowerlist() {
        return borrowerlist;
    }

    public void setBorrowerlist(List<BifaBorrowInfoBean> borrowerlist) {
        this.borrowerlist = borrowerlist;
    }

    /**
     * 深度克隆對象
     * @return
     */
    public BifaHjhPlanInfoEntity cloneObj() {
        BifaHjhPlanInfoEntity outer = null;
        try { // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            // 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            outer = (BifaHjhPlanInfoEntity) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return outer;
    }
}
