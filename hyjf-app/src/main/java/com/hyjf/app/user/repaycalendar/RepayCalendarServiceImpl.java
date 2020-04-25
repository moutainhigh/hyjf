package com.hyjf.app.user.repaycalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.app.BaseServiceImpl;
import com.hyjf.app.user.plan.MyPlanDefine;
import com.hyjf.app.user.project.InvestProjectDefine;
import com.hyjf.mybatis.model.customize.app.RepayCalendarCustomize;

/**
 * 回款日历
 * @author xiasq
 * @version RepayCalendarServiceImpl, v0.1 2018/1/30 18:29
 */

@Service
public class RepayCalendarServiceImpl extends BaseServiceImpl implements RepayCalendarService {
	private Logger logger = LoggerFactory.getLogger(RepayCalendarServiceImpl.class);

	@Override
	public int countRepaymentCalendar(Map<String, Object> params) {
		return assetManageCustomizeMapper.countRepaymentCalendar(params);
	}

	@Override
	public List<ReapyCalendarResult> searchRepaymentCalendar(Map<String, Object> params) {

		List<RepayCalendarCustomize> list = assetManageCustomizeMapper.selectRepaymentCalendar(params);

		List<ReapyCalendarResult> resultList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
		String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String monthSign = "0";
		String month = "";
		if (!CollectionUtils.isEmpty(list)) {
			ReapyCalendarResult result = null;
			for (RepayCalendarCustomize repayCalendarCustomize : list) {
				result = new ReapyCalendarResult();

				Integer orderStatus = repayCalendarCustomize.getOrderStatus();
				if (orderStatus != null && orderStatus == 5) {
					//计息时间为0或者不到锁定期时 不显示退出中标签
					result.setIsExiting(1);
				}else {
					result.setIsExiting(0);

				}

				Calendar repayTimeCalendar = parseCalendarFromString(repayCalendarCustomize.getRepayTime());
				String tradeYear = String.valueOf(repayTimeCalendar.get(Calendar.YEAR));
				// +1 是因为日历对象取出的月份从0开始
				String tradeMonth = String.valueOf(repayTimeCalendar.get(Calendar.MONTH) + 1);
				// 本月
				if (currentYear.equals(tradeYear) && currentMonth.equals(tradeMonth)) {
					if (!monthSign.equals(tradeYear + tradeMonth)) {
						ReapyCalendarResult titleResult = new ReapyCalendarResult();
						titleResult.setIsMonth("0");
						titleResult.setMonth("本月");
						month = "本月";
						monthSign = tradeYear + tradeMonth;
						resultList.add(titleResult);
					}
				}

				// 非本月放 xx月
				if (currentYear.equals(tradeYear) && !currentMonth.equals(tradeMonth)) {
					if (!monthSign.equals(tradeYear + tradeMonth)) {
						ReapyCalendarResult titleResult = new ReapyCalendarResult();
						titleResult.setIsMonth("0");
						titleResult.setMonth(tradeMonth + "月");
						monthSign = tradeYear + tradeMonth;
						month = tradeMonth + "月";
						resultList.add(titleResult);
					}
				}

				// 非本年 month 放 xxxx年xx月
				if (!currentYear.equals(tradeYear)) {
					if (!monthSign.equals(tradeYear + tradeMonth)) {
						ReapyCalendarResult titleResult = new ReapyCalendarResult();
						titleResult.setIsMonth("0");
						titleResult.setMonth(tradeYear + "年" + tradeMonth + "月");
						monthSign = tradeYear + tradeMonth;
						month = tradeYear + "年" + tradeMonth + "月";
						resultList.add(titleResult);
					}
				}
				result.setMonth(month);
				result.setIsMonth("1");
				this.copyCustomize2Result(repayCalendarCustomize, result);
				resultList.add(result);
			}
		} else {
			logger.error("未查询到数据....");
		}
		return resultList;
	}

	@Override
	public int searchNearlyRepaymentTime(Map<String, Object> params) {
		return assetManageCustomizeMapper.selectNearlyRepaymentTime(params);
	}

	/**
	 * 复制bean属性
	 * 
	 * @param customize
	 * @param result
	 */
	private void copyCustomize2Result(RepayCalendarCustomize customize, ReapyCalendarResult result) {
		if (customize != null) {
			result.setLabel(customize.getCouponName());
			result.setBorrowNid(customize.getBorrowNid());
			result.setBorrowName(customize.getBorrowName());
			result.setBorrowTheFirst(customize.getAccount() + "元");
			result.setBorrowTheFirstDesc("回款金额");
			result.setBorrowTheSecond(customize.getPeriod());
			result.setBorrowTheSecondDesc("项目期限");
			result.setBorrowTheThird(customize.getRepayTime());
			//result.setBorrowTheThirdDesc("回款时间");
			result.setLabel(customize.getCouponName());
			// 优惠券类型
			result.setCouponType(customize.getCouponType());

			// 产品加息的返回4
			if(customize.getType().equals("4")){
			    result.setCouponType("4");
			    customize.setCouponType("4");
			}
			// 拼接详情url borrowUrl
			// type = 6,5 是计划详情 其他是散标详情
			if (Arrays.asList("6", "5").contains(customize.getType())) {
				// mod by nxl 智投服务 项目期限->回报期限
				result.setBorrowTheSecondDesc("回报期限");
				result.setBorrowUrl(MyPlanDefine.MYPLAN_DETAIL_REQUEST + "/" + customize.getOrderId() + "?type="
						+ customize.getType() + "&couponType=" + customize.getCouponType().concat("&investStatusDesc=还款中"));
				// add 汇计划二期前端优化  计划的回款日历计划显示退出时间 20180509 start
//				result.setBorrowTheThirdDesc("退出时间");
				// add 汇计划二期前端优化  计划的回款日历计划显示退出时间 20180509 end
				// mod by nxl 智投服务 修改推出时间->开始退出
				result.setBorrowTheThirdDesc("开始退出");
			} else {
				String borrowUrl = InvestProjectDefine.MY_BORROW_PAGE_URL + "/" + customize.getBorrowNid()
						+ "?couponType=" + customize.getCouponType();
				// 如果是产品加息  特殊展示
				if(customize.getType().equals("4")){
				    borrowUrl += "&isIncrease=1&isCalendar=1";;
	            }
				String assignNid =  customize.getAssignNid();
				if (StringUtils.isNotBlank(assignNid)) {
					// 这里的orderId是assignNid
					borrowUrl = borrowUrl.concat("&orderId=").concat(assignNid).concat("&assignNid=").concat(assignNid).concat("&investStatusDesc=承接债转");
				} else {
				    borrowUrl = borrowUrl.concat("&orderId=").concat(customize.getOrderId());
					if("0".equals(customize.getCouponType())){ //本金出借
						borrowUrl = borrowUrl.concat("&investStatusDesc=还款中");
					} else { //优惠券出借
						borrowUrl = borrowUrl.concat("&investStatusDesc=未回款");
					}
				}
				// add 汇计划二期前端优化  散标的回款日历计划显示退出时间 20180509 start
				result.setBorrowTheThirdDesc("回款时间");
				// add 汇计划二期前端优化  散标的回款日历计划显示退出时间 20180509 start
				result.setBorrowUrl(borrowUrl);
			}
		}

	}

	/**
	 * 字符串对象转换日历对象
	 * 
	 * @param repayTime
	 * @return
	 */
	private Calendar parseCalendarFromString(String repayTime) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(repayTime);
		} catch (ParseException e) {
			logger.error("日期转换错误...", e);
		}
		calendar.setTime(date);
		return calendar;
	}
}
