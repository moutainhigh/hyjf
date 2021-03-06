package com.hyjf.mqreceiver.hgdatareport.cert.status;

import com.hyjf.mqreceiver.hgdatareport.cert.BaseHgCertReportServiceImpl;
import com.hyjf.mqreceiver.hgdatareport.common.CertCallConstant;
import com.hyjf.mybatis.mapper.auto.BorrowRepayPlanMapper;
import com.hyjf.mybatis.model.auto.Borrow;
import com.hyjf.mybatis.model.auto.BorrowRecover;
import com.hyjf.mybatis.model.auto.BorrowRecoverExample;
import com.hyjf.mybatis.model.auto.BorrowRepay;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nxl
 */

@Service
public class CertBorrowStatusServiceImpl extends BaseHgCertReportServiceImpl implements CertBorrowStatusService {

    @Autowired
    BorrowRepayPlanMapper borrowRepayPlanMapper;

    /**
     * 获取标的的还款信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Map<String,Object> selectBorrowByBorrowNid(String borrowNid,String statusAfter,boolean isUserInfo,boolean isOld) {
        //标的信息
        Borrow borrow = this.getBorrowByBorrowNid(borrowNid);
        String productStatus = "";
        String productDate = "";
        String productStatusDesc = "";
        // 如果是用户信息保送完完毕后,保送投资中
        if(isUserInfo){
            borrow.setStatus(2);
        }
        //标的还款信息
        BorrowRepay borrowRepay = this.selectBorrowRepay(borrowNid);
        //标的放款信息
        BorrowRecover borrowRecover = selectBorrowRecover(borrowNid);
        if (null != borrow) {
            if(borrow.getStatus()==2||borrow.getStatus()==5||borrow.getStatus()==4||(borrow.getStatus() == 3 && borrow.getBorrowFullStatus() == 1)||StringUtils.isNotBlank(statusAfter)) {
                Map<String,Object> param = new HashMap<String,Object>();
                //判断标的状态 = 9 的,再报送一次 添加判断=9
                if (StringUtils.isNotBlank(statusAfter)) {
                    //放款后（报送5还款中）
                    productStatus = "5";
                    //放款时间
                    productDate = dateFormatTransformation(borrow.getRecoverLastTime().toString());
                    productStatusDesc = "放款后（报送5还款中）";
                } else if (borrow.getStatus() == 2) {
                    //投资中
                    //标的状态投资中报送筹标中（报送6筹标中）
                    productStatus = "6";
                    //发标时间
                    productDate = dateFormatTransformation(borrow.getVerifyTime());

                    productStatusDesc = "标的状态投资中报送筹标中（报送6筹标中）";
                } else if (borrow.getStatus() == 5) {
                    //已还款
                    //最后一笔还款完成（报送3还款结束）
                    productStatus = "3";
                    //最后一笔还款时间(还款实际时间)
                    productDate = getLastRepayTime(borrowRepay);
                    productStatusDesc = "最后一笔还款完成（报送3还款结束）";
                } else if (borrow.getStatus() == 4) {
                    //还款中
                    //放款（报送9放款）
                    productStatus = "9";
                    //放款时间
                    productDate = dateFormatTransformation(borrow.getRecoverLastTime().toString());
                    productStatusDesc = "放款（报送9放款）";
                } else if (borrow.getStatus() == 3 && borrow.getBorrowFullStatus() == 1) {
                    //满标
                    //满标时候（报送1满标）
                    productStatus = "1";
                    productDate = dateFormatTransformation(borrow.getBorrowFullTime().toString());
                    productStatusDesc = "满标时候（报送1满标）";
                }
                param = putParamObject(borrowNid, productStatus, productDate, productStatusDesc);
                if (isOld) {
                    //是否是历史数据
                    // groupByDate  旧数据上报排序 按月用
                    param.put("groupByDate", getOrderByDate(productDate));
                }
                return param;
            }
        }
        return null;
    }

    /**
     * 组装参数
     * @param borrowNid
     * @param productStatus
     * @param productDate
     * @param productStatusDesc
     * @return
     */
    private  Map<String,Object> putParamObject(String borrowNid,String productStatus,String productDate, String productStatusDesc ){
        Map<String,Object> param = new HashMap<String,Object>();
        //接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        //平台编号
        param.put("sourceCode", CertCallConstant.CERT_SOURCE_CODE);
        //原产品信息编号
        param.put("sourceProductCode", borrowNid);
        //原产品信息编号
        param.put("sourceFinancingCode","-1");
        //状态编码
        param.put("productStatus",productStatus);
        //状态描述
        param.put("productStatusDesc", productStatusDesc);
        //状态更新时间
        param.put("productDate", productDate);
        return param;

    }
    /**
     * 日期转换,数据存的int10的时间戳
     *
     * @param repayTime
     * @return
     */
    private String dateFormatTransformation(String repayTime) {
        if (StringUtils.isNotBlank(repayTime)) {
            long intT = Long.parseLong(repayTime) * 1000;
            Date dateRapay = new Date(intT);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(dateRapay);
            return dateStr;
        }
        return null;
    }

    /**
     * 格式实际还款计划
     * @param borrowRepay
     * @return
     */
    private String getLastRepayTime(BorrowRepay borrowRepay){
        if(null!=borrowRepay){
            String repayTime = dateFormatTransformation(borrowRepay.getRepayActionTime());
            return repayTime;
        }
        return null;
    }

    /**
     * 获取放款信息
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowRecover selectBorrowRecover(String borrowNid){
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRepayList = this.borrowRecoverMapper.selectByExample(example);
        if (null != borrowRepayList && borrowRepayList.size() > 0) {
            return borrowRepayList.get(0);
        }
        return null;
    }

    /**
     * 格式化放款时间
     * @param borrowRecover
     * @return
     */
    private String getRecoverTime(BorrowRecover borrowRecover){
        if(null!=borrowRecover){
            String repayTime = dateFormatTransformation(borrowRecover.getAddtime());
            return repayTime;
        }
        return null;
    }

    /**
     * 获取排序时间的年月
     * @param date
     * @return
     */
    private String getOrderByDate(String date){
        if(StringUtils.isNotBlank(date)){
            String strDate = date.substring(0,7);
            return strDate;
        }
        return null;
    }
}
