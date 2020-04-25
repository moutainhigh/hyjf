/**
 * Description:汇计划service接口实现
 * Copyright: Copyright (HYJF Corporation) 2017
 * Company: HYJF Corporation
 * @author: LIBIN
 * @version: 1.0
 */
package com.hyjf.wechat.controller.hjh;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.common.util.*;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.user.auth.AuthService;
import com.hyjf.bank.service.user.auto.AutoService;
import com.hyjf.bank.service.sensorsdata.SensorsDataBean;
import com.hyjf.bank.service.user.auto.AutoService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.log.LogUtil;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.mongo.operationreport.dao.TotalInvestAndInterestMongoDao;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.mybatis.model.customize.UserInfoCustomize;
import com.hyjf.mybatis.model.customize.coupon.CouponConfigCustomizeV2;
import com.hyjf.mybatis.model.customize.web.htj.DebtPlanAccedeCustomize;
import com.hyjf.mybatis.model.customize.web.htj.DebtPlanBorrowCustomize;
import com.hyjf.mybatis.model.customize.web.htj.DebtPlanDetailCustomize;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import com.hyjf.wechat.base.BaseServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxHjhPlanServiceImpl extends BaseServiceImpl implements WxHjhPlanService {

     @Autowired
     @Qualifier("myAmqpTemplate")
     private RabbitTemplate rabbitTemplate;

	@Autowired
	TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;

	@Autowired
	private AuthService authService;

	 private Logger logger = LoggerFactory.getLogger(WxHjhPlanServiceImpl.class);
	 
	 
	 /** Jedis  */
	 public static JedisPool pool = RedisUtils.getPool();
	    
	/**
	 * 根据计划nid查询相应的计划详情
	 * @param planNid
	 * @return
	 */
	@Override
	public DebtPlanDetailCustomize selectDebtPlanDetail(String planNid) {
		DebtPlanDetailCustomize planDetail = this.hjhPlanCustomizeMapper.selectDebtPlanDetail(planNid);
		return planDetail;
	}
	
	/**
	 * 统计相应的计划加入记录总数
	 * @param params
	 * @return
	 */
	@Override
	public int countPlanAccedeRecordTotal(Map<String, Object> params) {
		int count = this.hjhPlanCustomizeMapper.countPlanAccedeRecordTotal(params);
		return count;
	}

	/**
	 * 统计相应的计划总数
	 * @param params
	 * @return
	 */
	@Override
	public Long selectPlanAccedeSum(Map<String, Object> params) {
		return hjhPlanCustomizeMapper.selectPlanAccedeSum(params);
	}

	/**
	 * 查询计划的加入记录
	 * @param params
	 * @return
	 * @author Administrator
	 */
	@Override
	public List<DebtPlanAccedeCustomize> selectPlanAccedeList(Map<String, Object> params) {
		List<DebtPlanAccedeCustomize> planAccedeList = this.hjhPlanCustomizeMapper.selectPlanAccedeList(params);
		return planAccedeList;
	}

	@Override
	public Users searchLoginUser(Integer userId) {
		Users user = usersMapper.selectByPrimaryKey(userId);
		return user;
	}

	@Override
	public int countUserAccede(String planNid, Integer userId) {
		HjhAccedeExample example = new HjhAccedeExample();
		HjhAccedeExample.Criteria crt = example.createCriteria();
		crt.andPlanNidEqualTo(planNid);
		crt.andUserIdEqualTo(userId);
		int count = this.hjhAccedeMapper.countByExample(example);
		return count;
	}

	@Override
	public HjhUserAuth getHjhUserAuthByUserId(Integer userId) {
        HjhUserAuthExample example=new HjhUserAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<HjhUserAuth> list=hjhUserAuthMapper.selectByExample(example);
        if(list!=null&& list.size()>0){
            return list.get(0);
        }else{
            return null;    
        }
	}
	
	/**
	 * 查询相应的计划标的记录总数
	 * 
	 * @param params
	 * @return
	 * @author Administrator
	 */
	@Override
	public int countPlanBorrowRecordTotal(Map<String, Object> params) {
		int count = this.hjhPlanCustomizeMapper.countPlanBorrowRecordTotal(params);
		return count;

	}
	
	/**
	 * 查询相应的计划标的列表
	 * 
	 * @param params
	 * @return
	 * @author Administrator
	 */
	@Override
	public List<DebtPlanBorrowCustomize> selectPlanBorrowList(Map<String, Object> params) {
		List<DebtPlanBorrowCustomize> planAccedeList = this.hjhPlanCustomizeMapper.selectPlanBorrowList(params);
		// add 给借款人加密 wangxiaohui 20180425 start
		for (DebtPlanBorrowCustomize planAccede : planAccedeList) {
			String borrowNid = planAccede.getBorrowNid();
			if ("1".equals(planAccede.getCompanyOrPersonal())) {//如果类型是公司 huiyingdai_borrow_users
				BorrowUsersExample caExample = new BorrowUsersExample();
				BorrowUsersExample.Criteria caCra = caExample.createCriteria();
				caCra.andBorrowNidEqualTo(borrowNid);
				List<BorrowUsers> selectByExample = this.borrowUsersMapper.selectByExample(caExample);
				String tureName= selectByExample.get(0).getUsername();
				String str = "******";
				if (tureName != null &&tureName != "") {
					if (tureName.length() <= 2) {
						tureName = str + tureName;
					}else if (tureName.length() > 2) {
						String substring = tureName.substring(tureName.length()-2);
						tureName = str + substring;
					}
				}
				planAccede.setTrueName(tureName);
			}else if("2".equals(planAccede.getCompanyOrPersonal())){//类型是个人 huiyingdai_borrow_maninfo
				//根据borrowNid查询查询个人的真实姓名
				BorrowManinfoExample boExample = new BorrowManinfoExample();
				BorrowManinfoExample.Criteria caCra = boExample.createCriteria();
				caCra.andBorrowNidEqualTo(borrowNid);
				List<BorrowManinfo> selectByExample = this.borrowManinfoMapper.selectByExample(boExample);
				String trueName = selectByExample.get(0).getName();
				String str = "**";
				if (trueName != null &&trueName != "") {
					if (trueName.length() == 1) {
						trueName =  trueName + str;
					}else if (trueName.length() > 1) {
						String substring = trueName.substring(0,1);
						trueName =  substring + str;
					}
				}
				planAccede.setTrueName(trueName);
			}
		}
		// add 给借款人加密 wangxiaohui 20180425 end
		
		
		
		return planAccedeList;

	}

	/**
	 * 取得用户优惠券信息
	 */
	@Override
	public CouponConfigCustomizeV2 getCouponUser(String couponGrantId, Integer userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("couponGrantId", couponGrantId);
		paramMap.put("userId", userId);
		CouponConfigCustomizeV2 ccTemp = this.couponUserCustomizeMapper.selectCouponConfigByGrantId(paramMap);
		return ccTemp;
	}

	@Override
	public JSONObject checkHJHParam(String planNid, String account, String userId, String platform,
			CouponConfigCustomizeV2 cuc) {
		// 当前用户
		Users user = this.getUsers(Integer.parseInt(userId));
		UsersInfoExample usersInfoExample = new UsersInfoExample();
		usersInfoExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId));
		List<UsersInfo> usersInfos = this.usersInfoMapper.selectByExample(usersInfoExample);
		// (1.1)判断 用户是不是担保机构 + 用户是不是被禁用
		if (null != usersInfos && usersInfos.size() == 1) {
			String roleIsOpen = PropUtils.getSystem(CustomConstants.HYJF_ROLE_ISOPEN);
			if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
				if (usersInfos.get(0).getRoleId() != 1) {// 担保机构用户
					return hjhResultMess("仅限出借人进行出借");
				}
			}

		} else {
			return hjhResultMess("账户信息异常");
		}
		// 判断用户是否禁用
		if (user.getStatus() == 1) {// 0启用，1禁用
			return hjhResultMess("抱歉，您的账户已被禁用，如有疑问请联系客服");
		}
		// 用户存在且用户未被禁用
		/**
		 * autoiStatus 自动投标授权状态 0: 未授权 1:已授权', autocStatus 自动债转授权状态 0: 未授权
		 * 1:已授权', invesTime 自动投标授权时间 iorderId 自动投标订单号
		 */
		// 自动出借授权
		if (!authService.checkInvesAuthStatus(Integer.parseInt(userId))) {
			return hjhResultMess("该产品需开通自动投标功能");
		}
		// 自动债转授权
		if (!authService.checkCreditAuthStatus(Integer.parseInt(userId))) {
			return hjhResultMess("该产品需开通自动债转功能");
		}
		// 服务费授权校验
		if (!authService.checkPaymentAuthStatus(Integer.parseInt(userId))) {
		    return hjhResultMess("该产品需开通服务费授权功能");
        }

		HjhPlan plan = this.getPlanByNid(planNid);

		if (Validator.isNull(plan)) {
			return hjhResultMess("未获取到相应的计划详情");
		}
		if (plan != null && plan.getPlanInvestStatus() == 2) {
			return hjhResultMess("此计划项目已经关闭");
		}
		// 查看江西银行账户
		BankOpenAccount bankOpenAccount = this.getBankOpenAccount(Integer.parseInt(userId));
		// 用户未在平台开户 且 未开户
		if (bankOpenAccount == null || user.getBankOpenAccount() == 0) {
			return hjhResultMess("用户开户信息不存在");
		}
		// 判断借款人开户信息是否存在
		if (bankOpenAccount.getUserId() == null && bankOpenAccount.getAccount() == null) {
			return hjhResultMess("用户银行客户号不存在");
		}
		// 判断借款编号是否存在
		if (StringUtils.isEmpty(planNid)) {
			return hjhResultMess("计划项目不存在");
		}
		// 判断借款信息是否存在
		if (plan == null || plan.getId() == null) {
			return hjhResultMess("计划项目不存在");
		}
		// 判断用户出借金额是否为空
		if (!(StringUtils.isNotEmpty(account)
				|| (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3)
				|| (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 1 && cuc.getAddFlg() == 1))) {
			return hjhResultMess("请输入加入金额");
		} else {
			// 还款金额是否数值
			try {
				// 出借金额必须是整数
				if (StringUtils.isEmpty(account)) {
					account = "0";
				}
				try {
					account = new BigDecimal(account).toString();
				} catch (Exception e) {
					return hjhResultMess("出借金额格式错误");
				}
				BigDecimal accountBigDecimal = new BigDecimal(account);
				if (accountBigDecimal.compareTo(BigDecimal.ZERO) == -1) {
					return hjhResultMess("加入金额不能为负数");
				} else {
					if ((accountBigDecimal.compareTo(BigDecimal.ZERO) == 0 && cuc == null)
							|| (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0 && cuc != null
									&& cuc.getCouponType() == 2)) {
						return hjhResultMess("出借金额不能为0元");
					}
					if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 1 && cuc != null && cuc.getCouponType() == 1
							&& cuc.getAddFlg() == 1) {
						return hjhResultMess("该优惠券只能单独使用");
					}

					if (plan.getLockPeriod() == null) {
						return hjhResultMess("计划锁定期不存在");
					}
					// String lockPeriod = plan.getLockPeriod().toString();
					// 从redis取该计划的开放额度
					String balance = RedisUtils.get(RedisConstants.HJH_PLAN + planNid);
					if (StringUtils.isEmpty(balance)) {
						return hjhResultMess("您来晚了，下次再来抢吧");
					} else {
						// DB 该计划可投金额
						BigDecimal minInvest = plan.getMinInvestment();// 该计划的最小出借金额
						// 当剩余可投金额小于最低起投金额，不做最低起投金额的限制
						if (minInvest != null && minInvest.compareTo(BigDecimal.ZERO) != 0
								&& new BigDecimal(balance).compareTo(minInvest) == -1) {
							if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
								return hjhResultMess("剩余可加入金额为" + balance + "元");
							}
							if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
								return hjhResultMess("剩余可加入只剩" + balance + "元，须全部购买");
							}
						} else {
							// 项目的剩余金额大于最低起投金额
							if (accountBigDecimal.compareTo(plan.getMinInvestment()) == -1) {
								if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
									if (cuc != null && cuc.getCouponType() != 3 && cuc.getCouponType() != 1) {
										return hjhResultMess(plan.getMinInvestment() + "元起投");
									}
								} else {
									return hjhResultMess(plan.getMinInvestment() + "元起投");
								}
							} else {
								BigDecimal max = plan.getMaxInvestment();
								if (max != null && max.compareTo(BigDecimal.ZERO) != 0
										&& accountBigDecimal.compareTo(max) == 1) {
									return hjhResultMess("项目最大加入额为" + max + "元");
								}
							}
						}
						if (accountBigDecimal.compareTo(plan.getAvailableInvestAccount()) > 0) {
							return hjhResultMess("加入金额不能大于开放额度");
						} else {
							// 查询用户账户表-出借账户
							Account tenderAccount = this.getAccount(Integer.parseInt(userId));
							// 获取汇盈平台DB---用户银行账户余额
							logger.info("tenderAccount.getBankBalance():" + tenderAccount.getBankBalance());
							logger.info("accountBigDecimal:" + accountBigDecimal);
							if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
								return hjhResultMess("可用金额不足");
							}
							// 获取江西银行---用户银行账户余额
							BankOpenAccount accountChinapnr = getBankOpenAccount(Integer.parseInt(userId));
							BigDecimal userBankBalance = getBankBalance(Integer.parseInt(userId),
									accountChinapnr.getAccount());
							logger.info("userBankBalance:" + userBankBalance);
							if (userBankBalance.compareTo(accountBigDecimal) < 0) {
								return hjhResultMess("可用金额不足");
							}
							if (StringUtils.isEmpty(balance)) {
								return hjhResultMess("您来晚了，下次再来抢吧");
							} else {
								// balance 是 redis 中的该计划可投金额
								if (StringUtils.isNotEmpty(balance)) {
									// redis剩余金额不足
									if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
										return hjhResultMess("项目太抢手了！剩余可加入金额只有" + balance + "元");
									} else {
										// 开放额度 < 1000(阀值)
										// 先判断开放额度是否小于1000，小于1000走阀值的逻辑
										if (new BigDecimal(balance)
												.compareTo(new BigDecimal(CustomConstants.TENDER_THRESHOLD)) == -1) {
											// 出借金额 != 开放额度
											if ((accountBigDecimal.compareTo(new BigDecimal(balance)) == -1)
													&& (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1)) {
												// 继续使用递增的逻辑
												if (plan.getInvestmentIncrement() != null
														&& BigDecimal.ZERO
																.compareTo((accountBigDecimal.subtract(minInvest))
																		.remainder(plan.getInvestmentIncrement())) != 0
														&& accountBigDecimal.compareTo(new BigDecimal(balance)) == -1) {

													return hjhResultMess(
															"加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍");
												}
											}
										}
										// 大于等于走正常出借校验的逻辑
										// 应该是： (用户出借额度 -
										// 起投额度)%增量 = 0
										if (plan.getInvestmentIncrement() != null
												&& (accountBigDecimal.longValue() - minInvest.longValue())
														% plan.getInvestmentIncrement().longValue() != 0
												&& accountBigDecimal.compareTo(new BigDecimal(balance)) == -1) {
											return hjhResultMess("加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍");
										}
										return successMess();
									}
								} else {
									return hjhResultMess("您来晚了，下次再来抢吧");
								}
							}
						}
					}
				}
			} catch (Exception e) {
				return hjhResultMess("加入金额必须为整数");
			}
		}
	}

	private JSONObject hjhResultMess(String desc) {
		JSONObject result = new JSONObject();
		result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
		result.put(CustomConstants.APP_STATUS_DESC, desc);
		return result;
	}
	
	private JSONObject successMess() {
		JSONObject result = new JSONObject();
		result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
		return result;
	}
	
	/**
	 * 加入计划
	 */
	@Override
	public JSONObject updateInvestInfo(ModelAndView modelAndView, String planNid, String account, String userId,
			String platform, String ip, String couponGrantId) {
		JSONObject result;
		String accountStr = account;
		modelAndView.addObject("plan", "1");
		modelAndView.addObject("planNid", planNid);
		CouponConfigCustomizeV2 cuc = null;
		if (StringUtils.isNotEmpty(couponGrantId) && new Integer(couponGrantId) > 0) {
			cuc = getCouponUser(couponGrantId, new Integer(userId));
		}
		logger.info("couponGrantId is :{}, 加入计划使用的优惠券信息: {}", couponGrantId, JSONObject.toJSONString(cuc));

		if (accountStr == null || "".equals(accountStr)) {
			accountStr = "0";
		}

		HjhPlan plan = getPlanByNid(planNid);
		String lockPeriod = plan.getLockPeriod().toString();

		logger.info("joinPlan ShiroUtil.getLoginUserId--------------------" + userId);// 如果没有本金出借且有优惠券出借
		BigDecimal decimalAccount = StringUtils.isNotEmpty(accountStr) ? new BigDecimal(accountStr) : BigDecimal.ZERO;

		// 无本金出借是使用优惠券出借 start
		modelAndView.addObject("couponInterest", "0");
		// 排他校验时间
		int couponOldTime = Integer.MIN_VALUE;
		// -1:有可用的优惠券，但是出借时不选择优惠券 空或null：用户没有可用优惠券
		if (cuc != null && StringUtils.isNotEmpty(couponGrantId) && !StringUtils.equals("-1", couponGrantId)) {
			// 排他check用
			couponOldTime = cuc.getUserUpdateTime();
		}
		// 无本金出借是使用优惠券出借 end
		result = checkHJHParam(planNid, accountStr, userId, platform, cuc);
		if (result == null) {
			logger.info("校验异常planNid:[" + planNid + "],userId:[" + userId + "]");
			return hjhResultMess("抱歉，出借失败，请重试！");
		} else if (result.get(CustomConstants.APP_STATUS) != null
				&& result.get(CustomConstants.APP_STATUS).equals("1")) {
			logger.info("抱歉，出借失败，请重试！planNid:[" + planNid + "],userId:[" + userId + "]");
			return hjhResultMess("抱歉，出借失败，请重试！");
		}
		/** redis 锁 */
		boolean reslut = RedisUtils.tranactionSet("HjhInvestUser" + userId, 10);
		if (!reslut) {
			return hjhResultMess("您正在出借，请稍后再试...");
		}
		// 体验金出借
		if (decimalAccount.compareTo(BigDecimal.ZERO) != 1 && cuc != null
				&& (cuc.getCouponType() == 3 || cuc.getCouponType() == 1)) {
			logger.info("cuc.getCouponType():" + cuc.getCouponType());
			result = this.couponTender(modelAndView, result, planNid, account, ip, cuc, userId, couponOldTime, platform,
					couponGrantId);
			return result;
		}

		String tenderUsrcustid = result.getString("tenderUsrcustid");
		// 生成冻结订单
		String frzzeOrderId = GetOrderIdUtils.getOrderId0(Integer.valueOf(userId));
		String frzzeOrderDate = GetOrderIdUtils.getOrderDate();
		// 冻结 加入 相应金额 明细
		Jedis jedis = pool.getResource();
		// 操作redis
		while ("OK".equals(jedis.watch(RedisConstants.HJH_PLAN + planNid))) {
			// 智投授权时开放额度bug修正 liubin 20181218 start
			String balance = RedisUtils.get(RedisConstants.HJH_PLAN + planNid);
			// 智投授权时开放额度bug修正 liubin 20181218 end
			if (StringUtils.isNotBlank(balance)) {
				LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(),
						"移动端用户:" + userId + "***********************************加入计划冻结前可投金额：" + balance);
				if (new BigDecimal(balance).compareTo(BigDecimal.ZERO) == 0) {
					LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "您来晚了，下次再来抢吧");
					return hjhResultMess("抱歉，出借失败，请重试！");
				} else {
					if (new BigDecimal(balance).compareTo(decimalAccount) < 0) {
						LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "可加入剩余金额为" + balance + "元");
						return hjhResultMess("抱歉，出借失败，请重试！");
					} else {
						Transaction tx = jedis.multi();
						// 事务：计划当前可用额度 = 计划未投前可用余额 - 用户出借额度
						BigDecimal lastAccount = new BigDecimal(balance).subtract(decimalAccount);
						tx.set(RedisConstants.HJH_PLAN + planNid, lastAccount + "");
						List<Object> result1 = tx.exec();
						if (result1 == null || result1.isEmpty()) {
							jedis.unwatch();
							LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "redis可加入剩余金额为" + balance + "元");
							//return hjhResultMess("抱歉，出借失败，请重试！");
						} else {
							logger.info("移动端用户:" + userId + "***********************************计划未投前可用余额redis："
									+ decimalAccount);
							// 写队列
							break;
						}
					}
				}
			} else {
				LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "您来晚了，下次再来抢吧");
				return hjhResultMess("抱歉，出借失败，请重试！");
			}
		}

		boolean afterDealFlag = false;
		String couponInterest = "0";
		// 写入加入计划表
		try {
			// 生成加入订单
			result = successMess();
			String planOrderId = GetOrderIdUtils.getOrderId0(Integer.valueOf(userId));
			// update by pcc 汇添金有本金出借是使用优惠券出借修改updateAfterPlanRedis接口
			// (原汇添金)添加couponGrantId、modelAndView参数 start
			afterDealFlag = updateAfterPlanRedis(modelAndView, planNid, frzzeOrderId, Integer.parseInt(userId),
					accountStr, tenderUsrcustid, ip, "", frzzeOrderDate, planOrderId, couponGrantId, couponInterest,
					platform);
			// update by pcc 汇添金有本金出借是使用优惠券出借修改updateAfterPlanRedis接口
			// 添加couponGrantId、modelAndView参数 end
			logger.info("afterDealFlag:" + afterDealFlag);
			if (afterDealFlag) {

				LogUtil.endLog(WxHjhPlanServiceImpl.class.toString(), "[交易完成后,回调结束]");
				DecimalFormat df = CustomConstants.DF_FOR_VIEW;
				String interest = null;
				logger.info("interest:" + interest);
				if (StringUtils.isBlank(interest)) {
					// 根据项目编号获取相应的项目
					// DebtPlan debtPlan = planService.getPlanByNid(planNid);
					HjhPlan debtPlan = getPlanByNid(planNid);
					BigDecimal planApr = debtPlan.getExpectApr();
					// 周期
					Integer planPeriod = debtPlan.getLockPeriod();
					BigDecimal earnings = new BigDecimal("0");
					df.setRoundingMode(RoundingMode.FLOOR);
					// 计算预期收益
					if ("endday".equals(debtPlan.getBorrowStyle())) {
						// 还款方式为”按天计息，到期还本还息“：预期收益=出借金额*年化收益÷365*锁定期；
						earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(accountStr),
								planApr.divide(new BigDecimal("100")), planPeriod);
					} else {
						// earnings=hjhGetMonthInterest(debtPlan.getBorrowStyle(),planApr,accountStr,planPeriod);
						// 还款方式为”按月计息，到期还本还息“：预期收益=出借金额*年化收益÷12*月数；
						earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(accountStr),
								planApr.divide(new BigDecimal("100")), planPeriod)
								.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
					}
					CommonSoaUtils.listedTwoInvestment(Integer.valueOf(userId), new BigDecimal(accountStr));
					CommonSoaUtils.listInvestment(Integer.valueOf(userId), new BigDecimal(accountStr), debtPlan.getBorrowStyle(),planPeriod);
					// 预期收益
					result.put("earnings", earnings);
					modelAndView.addObject("earnings", earnings);
					// modelAndView.addObject("couponInterest", couponInterest);
					// interest = df.format(earnings.add(new
					// BigDecimal(couponInterest)));
					logger.info("interest2:" + interest);
				}
				if (StringUtils.isNotBlank(interest)) {
					result.put("interest", interest);
					modelAndView.addObject("interest", interest);
					logger.info("interest3:" + interest);
				}
				if (cuc == null) {
					modelAndView.addObject("couponType", "0");
				} else {
					modelAndView.addObject("couponType", cuc.getCouponType());
				}
				modelAndView.addObject("plan", 0);
				result.put("account", df.format(new BigDecimal(accountStr)));
				modelAndView.addObject("account", df.format(new BigDecimal(accountStr)));
				logger.info("移动端用户:" + userId + "***********************************加入计划成功：" + accountStr);
				logger.info("移动端加入计划用户:" + userId +",planOrderId:"+ planOrderId+"发送mq");
				//MQ  加入汇计划，出借触发奖励
				sendMQActivity(Integer.valueOf(userId),planOrderId,new BigDecimal(accountStr),3);
				sendRrturnCashActivity(Integer.valueOf(userId),planOrderId,new BigDecimal(accountStr),3);
				result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
				// 神策数据统计 add by liuyang 20180726 start
				result.put("accedeOrderId",planOrderId);
				// 神策数据统计 add by liuyang 20180726 end
				LogUtil.endLog(WxHjhPlanServiceImpl.class.toString(), "investInfo");
				return result;
			} else {
				logger.info("移动端用户:" + userId + "***********************************预约成功后处理失败：" + accountStr);
				// 恢复redis
				recoverRedis(planNid, Integer.parseInt(userId), accountStr, lockPeriod);
				/*
				 * planService.recoverRedis(planNid, userId, accountStr,
				 * planPeriod);
				 */
				LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "系统异常");
				return hjhResultMess("抱歉，出借失败，请重试！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 恢复redis
			recoverRedis(planNid, Integer.parseInt(userId), accountStr, lockPeriod);
			LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "系统异常");
			return hjhResultMess("抱歉，出借失败，请重试！");

		}
	}

	public void recoverRedis(String planNid, Integer userId, String account, String lockPeriod) {
		JedisPool pool = RedisUtils.getPool();
		Jedis jedis = pool.getResource();
		BigDecimal accountBigDecimal = new BigDecimal(account);

		while ("OK".equals(jedis.watch("HJHBAL_" + planNid))) {
			String balanceLast = RedisUtils.get("HJHBAL_" + planNid);
			if (StringUtils.isBlank(balanceLast)) {
				return;
			}
			BigDecimal recoverAccount = accountBigDecimal.add(new BigDecimal(balanceLast));
			Transaction tx = jedis.multi();
			tx.set("HJHBAL_" + planNid, recoverAccount + "");
			List<Object> result = tx.exec();
			if (result == null || result.isEmpty()) {
				jedis.unwatch();
			} else {
				logger.info("加入计划用户:" + userId + "***********************************from redis恢复redis：" + account);
				break;
			}
		}
	}

	/**
	 * 发放活动奖励
	 * @param userId
	 * @param order
	 * @param investMoney
	 * @param projectType 项目类型
	 */
	private void sendMQActivity(Integer userId,String order,BigDecimal investMoney,int projectType){
		// 加入到消息队列
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("orderId", order);
		params.put("investMoney", investMoney.toString());
		//来源,1=新手标，2=散标，3=汇计划
		params.put("productType", projectType);
		rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.MDIAU_ACTIVITY, JSONObject.toJSONString(params));
	}
	/**
	 * 纳觅返现活动
	 * @param userId
	 * @param order
	 */
	private void sendRrturnCashActivity(Integer userId,String order,BigDecimal investMoney,int projectType){
		// 加入到消息队列
		logger.info("hjh send mq activity userId="+userId+" orderId="+order);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("orderId", order);
		params.put("investMoney", investMoney.toString());
		//来源,1=新手标，2=散标，3=汇计划
		params.put("productType", projectType);
		rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.RETURN_CASH_ACTIVITY, JSONObject.toJSONString(params));
	}
	private JSONObject couponTender(ModelAndView modelAndView, JSONObject result, String planNid, String account,
			String ip, CouponConfigCustomizeV2 cuc, String userId, int couponOldTime, String platform,
			String couponGrantId) {
		DecimalFormat df = CustomConstants.DF_FOR_VIEW;

		// 优惠券出借校验
		Map<String, String> validateMap = validateCoupon(Integer.parseInt(userId), account, couponGrantId, planNid,
				platform);
		if (validateMap.containsKey(CustomConstants.APP_STATUS_DESC)) {
			// 出借失败
			return hjhResultMess(validateMap.get(CustomConstants.APP_STATUS_DESC));
		}
		JSONObject jsonObject = CommonSoaUtils.planCouponInvest(userId, planNid, account, platform, couponGrantId, "",
				ip, couponOldTime + "");
		logger.info("调用优惠券出借返回结果：" + jsonObject.toString());
		if (jsonObject.getIntValue("status") == 0) {
			// 出借成功
			result = successMess();
			result.put("account", "0");
			// 优惠券收益
			result.put("couponInterest", df.format(new BigDecimal(jsonObject.getString("couponInterest"))));
			// 预期收益
			result.put("interest", df.format(new BigDecimal(jsonObject.getString("couponInterest"))));
			// 预期收益
			result.put("earnings", df.format(new BigDecimal(jsonObject.getString("couponInterest"))));
			// 优惠券ID
			result.put("couponGrantId", couponGrantId);
			// 出借类型
			result.put("investType", jsonObject.getString("couponTypeInt"));
			// 优惠券类别
			result.put("couponType", jsonObject.getString("couponType"));
			// 优惠券额度
			result.put("couponQuota", jsonObject.getString("couponQuota"));
			// 出借额度
			result.put("accountDecimal", jsonObject.getString("accountDecimal"));
			/** 修改出借成功页面显示修改开始 */
			modelAndView.addObject("plan", 1);
			modelAndView.addObject("planNid", planNid);

			// 优惠券收益
			modelAndView.addObject("couponInterest", df.format(new BigDecimal(jsonObject.getString("couponInterest"))));
			// 优惠券收益
			modelAndView.addObject("interest", df.format(new BigDecimal(jsonObject.getString("couponInterest"))));
			// 优惠券收益
			modelAndView.addObject("account", "0");
			modelAndView.addObject("earnings", "0");
			// 优惠券类别
			modelAndView.addObject("couponType", jsonObject.getString("couponTypeInt"));
			// 优惠券额度
			modelAndView.addObject("couponQuota", jsonObject.getString("couponQuota"));
			return result;
		} else {
			LogUtil.infoLog(this.toString(), "couponTender", "优惠券出借结束。。。。。。");
			result = hjhResultMess(jsonObject.getString("statusDesc"));
			modelAndView.addObject("investDesc", jsonObject.getString("statusDesc"));
			modelAndView.addObject("plan", 1);
			return result;
		}
	}

	// 优惠券出借校验
	private Map<String, String> validateCoupon(Integer userId, String account, String couponGrantId, String planNid,
			String platform) {

		JSONObject jsonObject = CommonSoaUtils.planCheckCoupon(userId + "", planNid, account, platform, couponGrantId);
		int status = jsonObject.getIntValue("status");
		String statusDesc = jsonObject.getString("statusDesc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (status == 1) {
			paramMap.put(CustomConstants.APP_STATUS_DESC, statusDesc);
		}

		return paramMap;
	}

	public boolean updateAfterPlanRedis(ModelAndView modelAndView, String planNid, String frzzeOrderId, Integer userId,
			String accountStr, String tenderUsrcustid, String ipAddr, String freezeTrxId, String frzzeOrderDate,
			String planOrderId, String couponGrantId, String couponInterest, String platform) throws Exception {
		int nowTime = GetDate.getNowTime10();
		Users user = getUsers(userId);
		UsersInfo userInfo = getUsersInfoByUserId(userId);
		HjhPlan debtPlan = getPlanByNid(planNid);
		// 预期年利率
		BigDecimal planApr = debtPlan.getExpectApr();
		// 周期
		Integer planPeriod = debtPlan.getLockPeriod();

		String borrowStyle = debtPlan.getBorrowStyle();

		BigDecimal earnings = BigDecimal.ZERO;
		if ("endday".equals(borrowStyle)) {
			// 还款方式为”按天计息，到期还本还息“：预期收益=出借金额*年化收益÷365*锁定期；
			earnings = DuePrincipalAndInterestUtils
					.getDayInterest(new BigDecimal(accountStr), planApr.divide(new BigDecimal("100")), planPeriod)
					.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
		} else {
			// 还款方式为”按月计息，到期还本还息“：预期收益=出借金额*年化收益÷12*月数；
			earnings = DuePrincipalAndInterestUtils
					.getMonthInterest(new BigDecimal(accountStr), planApr.divide(new BigDecimal("100")), planPeriod)
					.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

		}

		// 当大于等于100时 取百位 小于100 时 取十位
		BigDecimal accountDecimal = new BigDecimal(accountStr);// 用户出借金额
//		BigDecimal maxInvestNumber = debtPlan.getMaxInvestment();// 最高加入金额
//		BigDecimal minInvestNumber = debtPlan.getMinInvestment();// 最低加入金额

		/* (1)汇计划加入明细表插表开始 */

		// 处理汇计划加入明细表(以下涵盖所有字段)
		HjhAccede planAccede = new HjhAccede();
		planAccede.setAccedeOrderId(planOrderId);
		planAccede.setPlanNid(planNid);
		planAccede.setUserId(userId);
		planAccede.setUserName(user.getUsername());
		planAccede.setUserAttribute(userInfo.getAttribute());// 用户属性 0=>无主单
																// 1=>有主单
																// 2=>线下员工
																// 3=>线上员工
		planAccede.setAccedeAccount(accountDecimal);// 加入金额
		planAccede.setAlreadyInvest(BigDecimal.ZERO);// 已出借金额(出借时维护)
		planAccede.setClient(Integer.parseInt(platform));
		planAccede.setOrderStatus(0);// 0自动投标中 2自动投标成功 3锁定中 5退出中 7已退出 99
										// 自动出借异常(出借时维护)
		planAccede.setAddTime(nowTime);
		planAccede.setCountInterestTime(0);// 计息时间(最后放款时维护)
		planAccede.setSendStatus(0);// 协议发送状态0未发送 1已发送
		planAccede.setLockPeriod(debtPlan.getLockPeriod());
		planAccede.setCommissionStatus(0);// 提成计算状态:0:未计算,1:已计算
		planAccede.setAvailableInvestAccount(accountDecimal);// 可投金额初始与加入金额一致
		planAccede.setWaitTotal(BigDecimal.ZERO);// (出借时维护)
		planAccede.setWaitCaptical(BigDecimal.ZERO);// (出借时维护)
		planAccede.setWaitInterest(BigDecimal.ZERO);// (出借时维护)
		planAccede.setReceivedTotal(BigDecimal.ZERO);// (退出时维护)
		planAccede.setReceivedInterest(BigDecimal.ZERO);// (退出时维护)
		planAccede.setReceivedCapital(BigDecimal.ZERO);// (退出时维护)
		planAccede.setQuitTime(0);// (退出时维护)
		planAccede.setLastPaymentTime(0);// 最后回款时间(复审时维护)
		planAccede.setAcctualPaymentTime(0);// 实际回款时间(退出时维护)
		planAccede.setShouldPayTotal(accountDecimal.add(earnings));// 应还总额 =
																	// 应还本金
																	// +应还利息
		planAccede.setShouldPayCapital(accountDecimal);
		planAccede.setShouldPayInterest(earnings);
		planAccede.setCreateUser(userId);
		planAccede.setCreateTime(nowTime);
		planAccede.setDelFlg(0);// 初始未未删除
		
		//汇计划三期要求加入计划存当时的预期年化收益率 LIBIN WECHAT
		if(debtPlan.getExpectApr() != null){
			planAccede.setExpectApr(debtPlan.getExpectApr());
		}
		logger.info("username:" + user.getUsername() + " userid:" + userId);

		if (Validator.isNotNull(userInfo)) {
			SpreadsUsers spreadsUsers = this.getSpreadsUsersByUserId(userId);
			logger.info("推荐人信息：" + spreadsUsers);
			if (spreadsUsers != null) {
				int refUserId = spreadsUsers.getSpreadsUserid();
				logger.info("推荐人用户id：" + spreadsUsers.getSpreadsUserid());
				// 查找用户推荐人详情信息
				UserInfoCustomize userInfoCustomize = userInfoCustomizeMapper.queryUserInfoByUserId(refUserId);
				if (Validator.isNotNull(userInfoCustomize)) {
					planAccede.setInviteUserId(userInfoCustomize.getUserId());
					planAccede.setInviteUserName(userInfoCustomize.getUserName());
					planAccede.setInviteUserAttribute(userInfoCustomize.getAttribute());
					planAccede.setInviteUserRegionname(userInfoCustomize.getRegionName());
					planAccede.setInviteUserBranchname(userInfoCustomize.getBranchName());
					planAccede.setInviteUserDepartmentname(userInfoCustomize.getDepartmentName());
					logger.info("InviteUserName: " + userInfoCustomize.getUserName());
					logger.info("InviteUserRegionname: " + userInfoCustomize.getRegionName());
					logger.info("InviteUserBranchname: " + userInfoCustomize.getBranchName());
					logger.info("InviteUserDepartmentname: " + userInfoCustomize.getDepartmentName());
				}
			}else if(userInfo.getAttribute() == 2 || userInfo.getAttribute() == 3){
				// 查找用户推荐人详情信息
				UserInfoCustomize userInfoCustomize = userInfoCustomizeMapper.queryUserInfoByUserId(userId);
				if (Validator.isNotNull(userInfoCustomize)) {
					planAccede.setInviteUserId(userInfoCustomize.getUserId());
					planAccede.setInviteUserName(userInfoCustomize.getUserName());
					planAccede.setInviteUserAttribute(userInfoCustomize.getAttribute());
					planAccede.setInviteUserRegionname(userInfoCustomize.getRegionName());
					planAccede.setInviteUserBranchname(userInfoCustomize.getBranchName());
					planAccede.setInviteUserDepartmentname(userInfoCustomize.getDepartmentName());
				}
			}
		}
		logger.info("计划加入用户:" + userId + "***********************************插入planAccede："
				+ JSON.toJSONString(planAccede));
		// boolean trenderFlag =
		// debtPlanAccedeMapper.insertSelective(planAccede) > 0 ? true :
		// false;// 原 汇添金插表
		boolean trenderFlag = hjhAccedeMapper.insertSelective(planAccede) > 0 ? true : false;

		/* 汇计划加入明细表插表结束 */
		/* add by pcc 汇添金使用优惠券出借 start */

		if (StringUtils.isNotEmpty(couponGrantId)) {
			// 优惠券出借校验
			try {
				Map<String, String> validateMap = this.validateCoupon(userId, accountStr, couponGrantId, planNid,
						platform);
				LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "updateCouponTender",
						"优惠券出借校验结果：" + validateMap.get("statusDesc"));
				logger.info("updateCouponTender" + "优惠券出借校验结果：" + validateMap.get("statusDesc"));
				if (MapUtils.isEmpty(validateMap)) {
					// 校验通过 进行出借
					this.updateCouponTender(modelAndView, couponGrantId, planNid, userId, accountStr, ipAddr,
							new Integer(frzzeOrderDate), planOrderId, couponInterest, platform);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.infoLog(WxHjhPlanServiceImpl.class.getName(), "tenderRetUrl", "优惠券出借失败");
			}
		}
		/* add by 汇添金使用优惠券出借 end */

		if (trenderFlag) {// 加入明细表插表成功的前提下，继续
			// 查询开户表获取电子账号
			BankOpenAccount accountChinapnrTender = getBankOpenAccount(userId);

			AccountExample example = new AccountExample();
			AccountExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			List<Account> list = accountMapper.selectByExample(example);
			if (list != null && list.size() == 1) {
				// 该用户的江西银行可用余额(查询当前用户)
//				BigDecimal bankBalance = list.get(0).getBankBalance();
				// 该用户的汇计划账户可用余额(查询当前用户)
//				BigDecimal planBalance = list.get(0).getBankBalance();
				// 冻结金额(查询当前用户)
				BigDecimal frost = list.get(0).getBankFrost();
				// 总金额(查询当前用户)
				BigDecimal total = list.get(0).getBankTotal();
				/* 该用户的计划可用余额 = 原该用户的计划可用余额 + 该用户加入金额 */
				/* 该用户的江西银行可用余额 = 原该用户的江西银行可用余额 - 该用户加入金额 */
				Account account = new Account();
				// 用户id
				account.setUserId(userId);
				account.setPlanBalance(accountDecimal);// 注意：先set值，加法运算放在SQL中防并发
				account.setBankBalance(accountDecimal);// 注意：先set值，减法运算放在SQL中防并发

				// new added APP 更新用户的累积出借金额
				account.setBankInvestSum(accountDecimal);// 注意：先set值，加法运算放在SQL中防并发

				// 更新用户计划账户
				boolean accountFlag = this.hjhPlanCustomizeMapper.updateOfHjhPlanJoin(account) > 0 ? true : false;

				// boolean accountFlag =
				// this.adminAccountCustomizeMapper.updateOfPlanJoin(account) >
				// 0 ? true : false;//原 汇添金更表
				/* 更新huiyingdai_account用户账户余额表结束 */
				/* (3)插入account_list表 开始 */
				if (accountFlag) {// huiyingdai_account更表成功的前提下，继续
					// 先查询一遍Account表
					Account accedeAccount = selectUserAccount(userId);
					AccountList accountList = new AccountList();
					accountList.setIsBank(1);
					accountList.setIsShow(0);
					accountList.setNid(planOrderId);// 插入生成的计划订单号
					accountList.setAccedeOrderId(planOrderId);// 也插入生成的计划订单号
					accountList.setUserId(userId);
					accountList.setAmount(accountDecimal);// 插入用户加入金额
					accountList.setType(2);// 收支类型1收入2支出3冻结
					accountList.setTrade("hjh_invest");// 汇计划出借
					accountList.setTradeCode("balance");
					accountList.setIp(ipAddr);
					accountList.setOperator(userId + "");
					accountList.setRemark(planNid);
					accountList.setBankBalance(accedeAccount.getBankBalance());// 江西银行账户余额
					// accountList.setBalance(accedeAccount.getBalance());//原
					// 汇付账户
					accountList.setPlanBalance(accedeAccount.getPlanBalance());// 汇计划账户可用余额
					accountList.setInterest(new BigDecimal(0));
					accountList.setAwait(accedeAccount.getAwait());
					accountList.setPlanFrost(accedeAccount.getPlanFrost());
					accountList.setBankFrost(frost);
					accountList.setIsUpdate(0);
					accountList.setRepay(new BigDecimal(0));
					accountList.setBankTotal(total);
					accountList.setWeb(0);
					accountList.setBaseUpdate(0);
					accountList.setCreateTime(nowTime);
					accountList.setAccountId(accountChinapnrTender.getAccount());
					// 汇计划三期插入 accountList 表新增三个字段 By libin start
					accountList.setBankAwait(accedeAccount.getBankAwait());
					accountList.setBankAwaitCapital(accedeAccount.getBankAwaitCapital());
					accountList.setBankAwaitInterest(accedeAccount.getBankAwaitInterest());
					// 汇计划三期插入 accountList 表新增三个字段 By libin end
					logger.info("加入计划用户:" + userId + "***********************************预插入accountList："
							+ JSON.toJSONString(accountList));
					boolean accountListFlag = accountListMapper.insertSelective(accountList) > 0 ? true : false;
					/* (3)插入account_list表 结束 */

					/* (4)更新汇计划列表 开始 */
					if (accountListFlag) {// account_list表 插表成功的前提下
						// 更新plan表
						Map<String, Object> plan = new HashMap<String, Object>();
						plan.put("planId", debtPlan.getPlanNid());
						plan.put("accountDecimal", accountDecimal);// 用户加入金额
						plan.put("earnings", earnings);
						boolean updateBorrowAccountFlag = hjhPlanCustomizeMapper.updateByDebtPlanId(plan) > 0 ? true
								: false;
						/* (4)更新汇计划列表 结束 */

						/* (5)更新 平台累积出借 开始 */
						if (updateBorrowAccountFlag) {
							List<CalculateInvestInterest> calculates = this.calculateInvestInterestMapper
									.selectByExample(new CalculateInvestInterestExample());
							if (calculates != null && calculates.size() > 0) {
								CalculateInvestInterest calculateNew = new CalculateInvestInterest();
								calculateNew.setTenderSum(accountDecimal);
								calculateNew.setId(calculates.get(0).getId());
								calculateNew.setCreateTime(GetDate.getDate(nowTime));
								this.webCalculateInvestInterestCustomizeMapper
										.updateCalculateInvestByPrimaryKey(calculateNew);
							}
						}

						//计入计划成功更新mongo运营数据
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("type", 3);// 加入计划
						jsonObject.put("money", accountDecimal);
						rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_OPERATION_DATA, JSONObject.toJSONString(jsonObject));

						/* (5)更新 平台累积出借 结束 */

						/* (6)更新 渠道统计用户累计出借 和 huiyingdai_utm_reg的首投信息 开始 */
						if (updateBorrowAccountFlag) {// 更新汇计划列表成功的前提下
							// 更新渠道统计用户累计出借
							// 出借人信息
							Users users = getUsers(userId);
							if (users != null) {
								// 更新渠道统计用户累计出借
								AppChannelStatisticsDetailExample channelExample = new AppChannelStatisticsDetailExample();
								AppChannelStatisticsDetailExample.Criteria crt = channelExample.createCriteria();
								crt.andUserIdEqualTo(userId);
								List<AppChannelStatisticsDetail> appChannelStatisticsDetails = this.appChannelStatisticsDetailMapper
										.selectByExample(channelExample);
								if (appChannelStatisticsDetails != null && appChannelStatisticsDetails.size() == 1) {
									AppChannelStatisticsDetail channelDetail = appChannelStatisticsDetails.get(0);
									Map<String, Object> params = new HashMap<String, Object>();
									params.put("id", channelDetail.getId());
									// 认购本金
									params.put("accountDecimal", accountDecimal);
									// 出借时间
									params.put("investTime", nowTime);
									// 项目类型
									params.put("projectType", "汇计划");
									// 首次投标项目期限
									String investProjectPeriod = debtPlan.getLockPeriod() + "天";
									params.put("investProjectPeriod", investProjectPeriod);
									// 更新渠道统计用户累计出借
									if (users.getInvestflag() == 1) {
										this.appChannelStatisticsDetailCustomizeMapper
												.updateAppChannelStatisticsDetail(params);
									} else if (users.getInvestflag() == 0) {
										// 更新首投出借
										this.appChannelStatisticsDetailCustomizeMapper
												.updateFirstAppChannelStatisticsDetail(params);
									}
									logger.info("用户:" + userId
											+ "***********************************预更新渠道统计表AppChannelStatisticsDetail，订单号："
											+ planAccede.getAccedeOrderId());
								} else {
									// 更新huiyingdai_utm_reg的首投信息
									UtmRegExample utmRegExample = new UtmRegExample();
									UtmRegExample.Criteria utmRegCra = utmRegExample.createCriteria();
									utmRegCra.andUserIdEqualTo(userId);
									List<UtmReg> utmRegList = this.utmRegMapper.selectByExample(utmRegExample);
									if (utmRegList != null && utmRegList.size() > 0) {
										UtmReg utmReg = utmRegList.get(0);
										Map<String, Object> params = new HashMap<String, Object>();
										params.put("id", utmReg.getId());
										params.put("accountDecimal", accountDecimal);
										// 出借时间
										params.put("investTime", nowTime);
										// 项目类型
										params.put("projectType", "汇计划");
										// 首次投标项目期限
										String investProjectPeriod = debtPlan.getLockPeriod() + "天";
										// 首次投标项目期限
										params.put("investProjectPeriod", investProjectPeriod);
										// 更新渠道统计用户累计出借
										if (users.getInvestflag() == 0) {
											// 更新huiyingdai_utm_reg的首投信息
											this.appChannelStatisticsDetailCustomizeMapper.updateFirstUtmReg(params);
											logger.info("用户:" + userId
													+ "***********************************预更新渠道统计表huiyingdai_utm_reg的首投信息，订单号："
													+ planAccede.getAccedeOrderId());
										}
									}
								}
							}
							/* (6)更新 渠道统计用户累计出借 和 huiyingdai_utm_reg的首投信息 结束 */
							/* (7)更新 users新手标志位 开始 */
							// 出借人信息:更新新手标志位
							users = getUsers(userId);
							if (users != null) {
								if (users.getInvestflag() == 0) {
									users.setInvestflag(1);
									this.usersMapper.updateByPrimaryKeySelective(users);
								}
							}
							

	                        //crm出借推送
	                        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME,
	                                RabbitMQConstants.ROUTINGKEY_POSTINTERFACE_CRM, JSON.toJSONString(planAccede));
	                        
	                        
							/* (7)更新 users新手标志位 结束 */
							return true;

						} else {
							throw new RuntimeException(
									"huiyingdai_account表，hyjf_hjh_plan表，account_list表，hyjf_hjh_accede表 操作失败");
						}
					} else {
						throw new RuntimeException("account_list表操作失败");
					}
				} else {
					throw new RuntimeException("huiyingdai_account表操作失败");
				}
			} else {
				throw new RuntimeException("用户账户信息(huiyingdai_account)操作失败！");
			}
		} else {
			throw new RuntimeException("加入明细表操作失败！");
		}
	}

	public JSONObject updateCouponTender(ModelAndView modelAndView, String couponGrantId, String planNid,
			Integer userId, String accountStr, String ipAddr, int couponOldTime, String planOrderId,
			String couponInterest, String plan) {
		logger.info("优惠券出借开始.....");
		LogUtil.infoLog(WxHjhPlanServiceImpl.class.toString(), "updateCouponTender", "优惠券出借开始。。。。。。");
		JSONObject jsonObject = CommonSoaUtils.planCouponInvest(userId + "", planNid, accountStr, plan, couponGrantId,
				planOrderId, ipAddr, couponOldTime + "");
		logger.info("优惠券出借结果：" + jsonObject);
		JSONObject result = successMess();
		if (jsonObject.getIntValue("status") == 0) {
			// 优惠券收益
			result.put("couponInterest", jsonObject.getString("couponInterest"));
			result.put("couponType", jsonObject.getString("couponType"));
			result.put("couponTypeInt", jsonObject.getString("couponTypeInt"));
			result.put("couponQuota", jsonObject.getString("couponQuota"));
			// 优惠券ID
			result.put("couponGrantId", couponGrantId);
			// 优惠券额度
			result.put("couponQuota", jsonObject.getString("couponQuota"));
			// 出借额度
			result.put("accountDecimal", jsonObject.getString("accountDecimal"));
			result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
			// ----------old
			if ("0".equals(accountStr)) {
				modelAndView.addObject("plan", 1);
				modelAndView.addObject("planNid", planNid);
			}

			modelAndView.addObject("couponInterest", jsonObject.getString("couponInterest"));
			// 优惠券类别
			modelAndView.addObject("couponType", jsonObject.getString("couponTypeInt"));
			// 优惠券额度
			modelAndView.addObject("couponQuota", jsonObject.getString("couponQuota"));
			modelAndView.addObject("investDesc", "出借成功！");
		} else {
			result = hjhResultMess("出借失败！");
		}

		return result;
	}


	/**
	 * 发送神策数据统计MQ
	 *
	 * @param sensorsDataBean
	 */
	@Override
	public void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) {
		// 加入到消息队列
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mqMsgId", GetCode.getRandomCode(10));
		params.put("eventCode", sensorsDataBean.getEventCode());
		params.put("userId", sensorsDataBean.getUserId());
		params.put("presetProps", sensorsDataBean.getPresetProps());
		params.put("orderId", sensorsDataBean.getOrderId());
		rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.SENSORS_DATA_ROUTINGKEY_HJH_INVEST, JSONObject.toJSONString(params));
	}
}
