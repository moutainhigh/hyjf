package com.hyjf.api.operationaldata;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyjf.app.platdatastatistics.PlatDataStatisticsService;
import com.hyjf.mongo.operationreport.dao.OperationMongDao;
import com.hyjf.mongo.operationreport.dao.OperationMongoGroupDao;
import com.hyjf.mongo.operationreport.entity.OperationMongoGroupEntity;
import com.hyjf.mongo.operationreport.entity.OperationReportEntity;
import com.hyjf.mongo.operationreport.entity.SubEntity;
import com.hyjf.mybatis.model.auto.BorrowUserStatistic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version OperationalDataController, v0.1 2018/1/16 17:39
 */

@RestController
@RequestMapping("/find/operationalData")
public class OperationalDataController {
	
	private Logger _log = LoggerFactory.getLogger(OperationalDataController.class);
	
	@Autowired
	private OperationMongoGroupDao operationMongoGroupDao;
	@Autowired
	private OperationMongDao operationMongDao;
	@Autowired
	private PlatDataStatisticsService platDataStatisticsService;

//	@Autowired
//	private OperationReportCustomizeMapper operationReportCustomizeMapper;

	/**
	 * 获取平台实时数据
	 * 
	 * @return
	 */
	@RequestMapping("/getPlatformRealTimeData")
	@ResponseBody
	public JSONObject getPlatformRealTimeData() {
		Calendar cal = Calendar.getInstance();
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			Query query = new Query();
			query.limit(1);
			query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
			OperationReportEntity oe = operationMongDao.findOne(query);

			JSONObject info = new JSONObject();

//			info.put("CumulativeTransactionTotal", oe.getTotalCount());
//			info.put("CumulativeUserIncome", oe.getTotalInterest());
			//累计交易笔数(实时)
			info.put("CumulativeTransactionNum", platDataStatisticsService.selectTotalTradeSum());
			//累计交易总额(实时)
			info.put("CumulativeTransactionTotal", platDataStatisticsService.selectTotalInvest());
			//累计为用户赚取收益(实时)
			info.put("CumulativeUserIncome", platDataStatisticsService.selectTotalInterest());
//			info.put("CumulativeTransactionNum", operationReportCustomizeMapper.getTradeCount(getLastDay(cal)));

			//加上统计月份
			int staticMonth=oe.getStatisticsMonth();
			info.put("CutOffDate", transferIntToDate(staticMonth));
			
			// 获取12个月的数据
			DBObject dbObject = new BasicDBObject();
			BasicDBObject fieldsObject = new BasicDBObject();
			// 指定返回的字段
			fieldsObject.put("statisticsMonth", true);
			fieldsObject.put("accountMonth", true);
			fieldsObject.put("tradeCountMonth", true);
			query = new BasicQuery(dbObject, fieldsObject);
			query.limit(12);
			query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
			List<OperationReportEntity> list = operationMongDao.find(query);

			List<String> xlist = new ArrayList<String>();
			List<String> yMoneytlist = new ArrayList<String>();
			List<String> ycountlist = new ArrayList<String>();
			for (int i = list.size() - 1; i >= 0; i--) {
				xlist.add(oe.format(String.valueOf(list.get(i).getStatisticsMonth())));
				yMoneytlist.add(trim(list.get(i).getAccountMonth().intValue(),100000000));
				ycountlist.add(trim(list.get(i).getTradeCountMonth(),10000));
			}

			JSONObject detail = new JSONObject();
			detail.put("XAxis", xlist.toString());
			detail.put("YAxis", yMoneytlist);
			info.put("MonthlyTransactionTotlaList", detail);

			detail = new JSONObject();
			detail.put("XAxis", xlist.toString());
			detail.put("YAxis", ycountlist);
			info.put("MonthlyTransactionNumList", detail);

			result.put("info", info);
			
		} catch (Exception e) {
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}

	/**
	 * 获取借款&&出借数据
	 * 
	 * @return
	 */
	@RequestMapping("/getLoanInvestData")
	@ResponseBody
	public JSONObject getLoanInvestData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			Query query = new Query();
			query.limit(1);
			query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
			OperationReportEntity oe = operationMongDao.findOne(query);

			JSONObject detail = new JSONObject();
			
			if(oe != null){
				detail.put("CumulativeTransactionTotal", oe.getWillPayMoney());
				detail.put("LoanNum", oe.getLoanNum());
				
				detail.put("investorTotal", oe.getTenderCount());
				detail.put("perInvestTotal", oe.getPerInvest());
				float time = oe.getFullBillTimeCurrentMonth();
				
				detail.put("fullScaleHour", oe.getHour(time));
				detail.put("fullScaleMinute", oe.getMinutes(time));
				detail.put("fullScaleSecond", oe.getSeconds(time));
			}

			detail.put("relationshipTotal", 0);
			detail.put("relationshipNum", 0);
			detail.put("overdueTotal", 0);
			detail.put("overdueNum", 0);
			detail.put("overdue90Total", 0);
			detail.put("overdue90Num", 0);


			//借款人相关数据统计：
			BorrowUserStatistic borrowUserData = platDataStatisticsService.selectBorrowUserStatistic();
			BigDecimal borrowuserMoneyTopone = BigDecimal.ZERO;
			BigDecimal borrowuserMoneyTopten = BigDecimal.ZERO;
//			if(borrowUserData.getBorrowuserMoneyTotal() != null && borrowUserData.getBorrowuserMoneyTotal().compareTo(BigDecimal.ZERO) > 0){
				//borrowuserMoneyTopone = borrowUserData.getBorrowuserMoneyTopone().divide(borrowUserData.getBorrowuserMoneyTotal(), BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				//borrowuserMoneyTopten = borrowUserData.getBorrowuserMoneyTopten().divide(borrowUserData.getBorrowuserMoneyTotal(), BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
//				borrowuserMoneyTopone = borrowUserData.getBorrowuserMoneyTopone().divide(borrowUserData.getBorrowuserMoneyTotal(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP);
//				borrowuserMoneyTopten = borrowUserData.getBorrowuserMoneyTopten().divide(borrowUserData.getBorrowuserMoneyTotal(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP);

//			}

			detail.put("TotalBorrower", oe.getBorrowuserCountTotal());
			detail.put("NowBorrower", oe.getBorrowuserCountCurrent());
			detail.put("CurrentInvestor", oe.getTenderuserCountCurrent());
			detail.put("MaxBorrowerRate", oe.getBorrowuserMoneyTopone());
			detail.put("Top10BorrowerRate", oe.getBorrowuserMoneyTopten());

			result.put("info", detail);

		} catch (Exception e) {
			_log.error("发生异常", e);
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}

	/**
	 * 获取出借人地域分布数据
	 * 
	 * @return
	 */
	@RequestMapping("/getInvestorRegionData")
	@ResponseBody
	public JSONObject getInvestorRegionData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			Query query = new Query();
			query.limit(1);
			query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
			OperationMongoGroupEntity oe = operationMongoGroupDao.findOne(query);
			// 获取出借人区域信息
			Map<Integer, String> cityMap = oe.getInvestorRegionMap();
			List<SubEntity> list = oe.orgnizeData(cityMap);
			List<SubEntity> sublist=oe.formatList(list);
//			Gson gson = new Gson();
			
			ObjectMapper mapper = new ObjectMapper();
			
			result.put("InvestorRegionList", mapper.writeValueAsString(sublist));
//			result.put("InvestorRegionList", gson.toJson(sublist));
		} catch (Exception e) {
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}

	/**
	 * 获取出借人性别&&年龄数据
	 * 
	 * @return
	 */
	@RequestMapping("/getInvestorSexAgeData")
	@ResponseBody
	public JSONObject getSexAgeData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			JSONObject info = new JSONObject();

			Query query = new Query();
			query.limit(1);
			query.with(new Sort(Sort.Direction.DESC, "statisticsMonth"));
			OperationMongoGroupEntity oe = operationMongoGroupDao.findOne(query);
			// 出借人性别的分布
			Map<Integer, Integer> sexMap = oe.getInvestorSexMap();

			int maleCount = sexMap.get(OperationMongoGroupEntity.MALE);
			int femaleCount = sexMap.get(OperationMongoGroupEntity.FEMALE);
			float malePer = (float) maleCount * 100 / (maleCount + femaleCount);
			float femalePer = (float) femaleCount * 100 / (maleCount + femaleCount);
			info.put("InvestorRegionMenRate", oe.formatDate(malePer) + "%");
			info.put("InvestorRegionWoMenRate", oe.formatDate(femalePer) + "%");

			// 出借人年龄分布
			Map<Integer, Integer> ageMap = oe.getInvestorAgeMap();
			int r1 = ageMap.get(OperationMongoGroupEntity.ageRange1);
			int r2 = ageMap.get(OperationMongoGroupEntity.ageRange2);
			int r3 = ageMap.get(OperationMongoGroupEntity.ageRange3);
			int r4 = ageMap.get(OperationMongoGroupEntity.ageRange4);
			int total = r1 + r2 + r3 + r4;

			List<String> list = new ArrayList<String>();
			list.add(oe.getAge(OperationMongoGroupEntity.ageRange1Desc, OperationMongoGroupEntity.ageRange1Color,
					oe.formatDate((float) r1 * 100 / total)));
			list.add(oe.getAge(OperationMongoGroupEntity.ageRange2Desc, OperationMongoGroupEntity.ageRange2Color,
					oe.formatDate((float) r2 * 100 / total)));
			list.add(oe.getAge(OperationMongoGroupEntity.ageRange3Desc, OperationMongoGroupEntity.ageRange3Color,
					oe.formatDate((float) r3 * 100 / total)));
			list.add(oe.getAge(OperationMongoGroupEntity.ageRange4Desc, OperationMongoGroupEntity.ageRange4Color,
					oe.formatDate((float) r4 * 100 / total)));

			info.put("InvestorAgeList", list);

			result.put("info", info);
		} catch (Exception e) {
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}
	// public InvestorSexAgeDataResponse getInvestorSexAgeData() {
	// InvestorSexAgeDataResponse response = new InvestorSexAgeDataResponse();
	// InvestorSexAgeDataVO vo = new InvestorSexAgeDataVO();
	// vo.setInvestorRegionMenRate("10.00%");
	// vo.setInvestorRegionWoMenRate("90.00%");
	// List<InvestorSexAgeDataVO.InvestorAgeDistribution> list =
	// this.buildInvestorAgeList();
	// vo.setInvestorAgeList(list);
	// response.setInfo(vo);
	// return response;
	// }

	/**
	 * 构建
	 * 
	 * @return
	 */
	private List<InvestorSexAgeDataVO.InvestorAgeDistribution> buildInvestorAgeList() {
		List<InvestorSexAgeDataVO.InvestorAgeDistribution> list = new ArrayList<>();

		InvestorSexAgeDataVO.InvestorAgeDistribution distribution1 = new InvestorSexAgeDataVO.InvestorAgeDistribution();
		distribution1.setName("25岁以下");
		distribution1.setColor("red");
		distribution1.setValue(this.getAgeRate(0, 24));
		list.add(distribution1);

		InvestorSexAgeDataVO.InvestorAgeDistribution distribution2 = new InvestorSexAgeDataVO.InvestorAgeDistribution();
		distribution2.setName("25至35岁");
		distribution2.setColor("green");
		distribution2.setValue(this.getAgeRate(25, 35));
		list.add(distribution2);

		InvestorSexAgeDataVO.InvestorAgeDistribution distribution3 = new InvestorSexAgeDataVO.InvestorAgeDistribution();
		distribution3.setName("36至50岁");
		distribution3.setColor("black");
		distribution3.setValue(this.getAgeRate(36, 50));
		list.add(distribution3);

		InvestorSexAgeDataVO.InvestorAgeDistribution distribution4 = new InvestorSexAgeDataVO.InvestorAgeDistribution();
		distribution4.setName("50岁以上");
		distribution4.setColor("blue");
		distribution4.setValue(this.getAgeRate(51, 150));
		list.add(distribution4);
		return list;
	}

	/**
	 * 查询出借人年龄分布
	 * 
	 * @param startAge
	 * @param endAge
	 * @return
	 */
	private String getAgeRate(int startAge, int endAge) {
		String rate = "";
		// todo 暂时没有db
		rate = "25.00";
		return rate;
	}

	/**
	 * 转化数字格式的日期
	 * @param date
	 * @return
	 */
	public  String transferIntToDate(int date ) {
		Calendar cl=Calendar.getInstance();
		String str=String.valueOf(date);
		int year=Integer.valueOf(str.substring(0,4));
		int month=Integer.valueOf(str.substring(4,6))-1;
		
		cl.set(Calendar.YEAR,year);
		cl.set(Calendar.MONTH,month);
		int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);  
        cl.set(Calendar.DAY_OF_MONTH, lastDay); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(cl.getTime());
	}
	
	public String trim(float input,int fenzi){
		return new BigDecimal((float)input/fenzi).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
