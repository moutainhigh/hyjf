package com.hyjf.admin.exception.borrowregistexception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.BaseServiceImpl;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.util.RabbitMQConstants;
import com.hyjf.common.util.SessionUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.mybatis.model.auto.BankOpenAccount;
import com.hyjf.mybatis.model.auto.BorrowExample;
import com.hyjf.mybatis.model.auto.BorrowWithBLOBs;
import com.hyjf.mybatis.model.auto.HjhAssetBorrowType;
import com.hyjf.mybatis.model.auto.HjhAssetBorrowTypeExample;
import com.hyjf.mybatis.model.auto.HjhPlanAsset;
import com.hyjf.mybatis.model.auto.HjhPlanAssetExample;
import com.hyjf.mybatis.model.auto.STZHWhiteList;
import com.hyjf.mybatis.model.auto.STZHWhiteListExample;
import com.hyjf.mybatis.model.auto.Users;
import com.hyjf.mybatis.model.customize.AdminSystem;
import com.hyjf.mybatis.model.customize.BorrowCommonCustomize;
import com.hyjf.mybatis.model.customize.admin.BorrowRegistCustomize;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

@Service
public class BorrowRegistExceptionServiceImpl extends BaseServiceImpl implements BorrowRegistExceptionService {

	Logger _log = LoggerFactory.getLogger(BorrowRegistExceptionServiceImpl.class);
	

	
    @Autowired
    @Qualifier("myAmqpTemplate")
    private RabbitTemplate rabbitTemplate;

	/**
	 * COUNT
	 * 
	 * @param borrowCustomize
	 * @return
	 */
	public Long countBorrow(BorrowCommonCustomize borrowCommonCustomize) {
		return this.adminBorrowRegistExceptionMapper.countBorrow(borrowCommonCustomize);
	}

	/**
	 * 借款列表
	 * 
	 * @return
	 */
	public List<BorrowRegistCustomize> selectBorrowList(BorrowCommonCustomize borrowCommonCustomize) {
		return this.adminBorrowRegistExceptionMapper.selectBorrowList(borrowCommonCustomize);
	}

	@Override
	public JSONObject debtRegistSearch(String borrowNid, JSONObject result, int loginUserId) {

		// 获取相应的标的详情
		BorrowExample borrowExample = new BorrowExample();
		BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
		borrowCra.andBorrowNidEqualTo(borrowNid);
		List<BorrowWithBLOBs> borrowList = this.borrowMapper.selectByExampleWithBLOBs(borrowExample);// 获取相应的标的信息
		if (borrowList != null && borrowList.size() == 1) {
			BorrowWithBLOBs borrow = borrowList.get(0);
			String borrowStyle = borrow.getBorrowStyle();// 項目还款方式
			// 是否月标(true:月标, false:天标)
			boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
					|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
			int userId = borrow.getUserId();// 借款人userId
			Users user = this.getUsersByUserId(userId);// 借款人用户
			if (Validator.isNotNull(user)) {
				BankOpenAccount bankOpenAccount = this.getBankOpenAccount(userId);
				if (Validator.isNotNull(bankOpenAccount)) {
					// 借款人银行账户
					String accountId = bankOpenAccount.getAccount();
					// 1查询相应的标的备案状态
					BankCallBean searchResult = this.borrowRegistSearch(borrowNid, accountId, loginUserId);
					if (Validator.isNotNull(searchResult)) {
						String searchRetCode = StringUtils.isNotBlank(searchResult.getRetCode()) ? searchResult.getRetCode() : "";
						// 如果返回成功
						if (BankCallConstant.RESPCODE_SUCCESS.equals(searchRetCode)) {
							String subPacks = searchResult.getSubPacks();
							if (StringUtils.isNotBlank(subPacks)) {
								JSONArray debtDetails = JSONObject.parseArray(subPacks);
								if (debtDetails != null) {
									if (debtDetails.size() == 0) {
										// 更新相应的标的状态为备案中
										boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1);
										if (debtRegistingFlag) {
											try {
		                                        //modify by 受托支付
												BankCallBean registResult = this.borrowRegist(borrow, isMonth, bankOpenAccount.getAccount(), loginUserId);
												if (Validator.isNotNull(registResult)) {
													String registRetCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
													if (BankCallConstant.RESPCODE_SUCCESS.equals(registRetCode)) {
														//new added by 受托支付备案
														if(borrow.getEntrustedFlg()==1){
															boolean debtEntrustedRegistedFlag = this.updateEntrustedBorrowRegist(borrow, 7, 2);
															if (debtEntrustedRegistedFlag) {
																result.put("success", "0");
																result.put("msg", "备案成功！");
																// 更新标的资产信息如果关联计划的话
																updateBorrowAsset(borrow,4);//受托支付传4
															} else {
																result.put("success", "1");
																result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
															}	
														} else {
															boolean debtRegistedFlag = this.updateBorrowRegist(borrow, 1, 2);
															if (debtRegistedFlag) {
																result.put("success", "0");
																result.put("msg", "备案成功！");
																// 更新标的资产信息如果关联计划的话
																updateBorrowAsset(borrow,5);//非受托支付5
															} else {
																result.put("success", "1");
																result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
															}
														}
													} else {
														this.updateBorrowRegist(borrow, 0, 4);
														String message = registResult.getRetMsg();
														result.put("success", "1");
														result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
													}
												} else {
													result.put("success", "1");
													result.put("msg", "银行备案接口调用失败！");
												}
											} catch (Exception e) {
												e.printStackTrace();
												this.updateBorrowRegist(borrow, 0, 4);
												result.put("success", "1");
												result.put("msg", "银行备案接口调用失败！");
											}
										} else {
											result.put("success", "1");
											result.put("msg", "更新相应的标的信息失败,请稍后再试！");
										}
									} 
									else if (debtDetails.size() == 1) {
										JSONObject debtDetail = debtDetails.getJSONObject(0);
										String state = debtDetail.getString(BankCallConstant.PARAM_STATE);
										if (state.equals("9")) {
											// 2备案状态已撤销直接更改标的状态为:流标,撤销备案
											boolean debtRegistedNewFlag = this.updateBorrowRegist(borrow, 6, 3);
											if (debtRegistedNewFlag) {
												result.put("success", "0");
												result.put("msg", "备案已经撤销！");
											} else {
												result.put("success", "1");
												result.put("msg", "备案已经撤销后，更新相应的状态失败,请联系客服！");
											}
										} else if (state.equals("8")) {
											// 2备案处理中更改备案状态为备案中
											boolean debtRegistedNewFlag = this.updateBorrowRegist(borrow, 0, 1);
											if (debtRegistedNewFlag) {
												result.put("success", "0");
												result.put("msg", "银行备案处理中！");
											} else {
												result.put("success", "1");
												result.put("msg", "银行备案处理中，更新相应的状态失败,请联系客服！");
											}
										} else if (state.equals("1")) {
											// 2备案成功直接更改备案状态:银行确认state为1的情况,银行标的备案成功
											//new added by 受托支付备案
											if(borrow.getEntrustedFlg()==1){
												boolean debtEntrustedRegistedFlag = this.updateEntrustedBorrowRegist(borrow, 7, 2);
												if (debtEntrustedRegistedFlag) {
													result.put("success", "0");
													result.put("msg", "备案成功！");
													updateBorrowAsset(borrow,4);//非受托支付传
												} else {
													result.put("success", "1");
													result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
												}
											} else {
												boolean debtRegistedNewFlag = this.updateBorrowRegist(borrow, 1, 2);
												if (debtRegistedNewFlag) {
													result.put("success", "0");
													result.put("msg", "备案成功！");
													// 更新标的资产信息如果关联计划的话
													updateBorrowAsset(borrow,5);//非受托支付传5
												} else {
													result.put("success", "1");
													result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
												}
											}
										}
									} else {
										result.put("success", "1");
										result.put("msg", "银行端标号重复,请联系客服！");
									}
								} else {
									// 更新相应的标的状态为备案中
									boolean debtRegistingFlag = this.updateBorrowRegist(borrow, 0, 1);
									if (debtRegistingFlag) {
										try {
											BankCallBean registResult = this.borrowRegist(borrow, isMonth, bankOpenAccount.getAccount(), loginUserId);
											if (Validator.isNotNull(registResult)) {
												String registRetCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
												if (BankCallConstant.RESPCODE_SUCCESS.equals(registRetCode)) {
													boolean debtRegistedFlag = this.updateBorrowRegist(borrow, 1, 2);
													if (debtRegistedFlag) {
														result.put("success", "0");
														result.put("msg", "备案成功！");
													} else {
														result.put("success", "1");
														result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
													}
												} else {
													this.updateBorrowRegist(borrow, 0, 4);
													String message = registResult.getRetMsg();
													result.put("success", "1");
													result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
												}
											} else {
												result.put("success", "1");
												result.put("msg", "银行备案接口调用失败！");
											}
										} catch (Exception e) {
											e.printStackTrace();
											this.updateBorrowRegist(borrow, 0, 4);
											result.put("success", "1");
											result.put("msg", "银行备案接口调用失败！");
										}
									} else {
										result.put("success", "1");
										result.put("msg", "更新相应的标的信息失败,请稍后再试！");
									}
								}
							} else {
								result.put("success", "1");
								result.put("msg", "查询标的备案状态失败,请联系客服！");
							}
						} else {
							result.put("success", "1");
							result.put("msg", "查询标的备案状态失败,请联系客服！");
						}
					} else {
						result.put("success", "1");
						result.put("msg", "查询标的备案状态失败,请联系客服！");
					}
				} else {
					result.put("success", "1");
					result.put("msg", "未查询到借款人开户信息！");
				}
			} else {
				result.put("success", "1");
				result.put("msg", "借款人信息错误！");
			}
		} else {
			result.put("success", "1");
			result.put("msg", "项目编号为空！");
		}
		return result;
	}

	/**
	 * 标的备案
	 * 
	 * @param borrow
	 * @param isMonth
	 * @param accountId
	 * @param loginUserId
	 * @return
	 */
	private BankCallBean borrowRegist(BorrowWithBLOBs borrow, boolean isMonth, String accountId, int loginUserId) {
		// 获取共同参数
		String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
		String instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
		String channel = BankCallConstant.CHANNEL_PC;
		String orderId = GetOrderIdUtils.getOrderId2(loginUserId);
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 调用开户接口
		BankCallBean debtRegistBean = new BankCallBean();
		debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);// 消息类型(用户开户)
		debtRegistBean.setInstCode(instCode);// 机构代码
		debtRegistBean.setBankCode(bankCode);
		debtRegistBean.setTxDate(txDate);
		debtRegistBean.setTxTime(txTime);
		debtRegistBean.setSeqNo(seqNo);
		debtRegistBean.setChannel(channel);
		debtRegistBean.setAccountId(accountId);// 借款人电子账号
		debtRegistBean.setProductId(borrow.getBorrowNid());// 标的表id
		debtRegistBean.setProductDesc(borrow.getName());// 标的名称
		debtRegistBean.setRaiseDate(borrow.getBankRaiseStartDate());// 募集日,标的保存时间
		debtRegistBean.setRaiseEndDate(borrow.getBankRaiseEndDate());// 募集结束日期
		if (isMonth) {
			debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);// 付息方式没有不确定日期
		} else {
			debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
		}
		debtRegistBean.setDuration(String.valueOf(borrow.getBankBorrowDays()));// (借款期限,天数）
		debtRegistBean.setTxAmount(String.valueOf(borrow.getAccount()));// 交易金额
		debtRegistBean.setRate(String.valueOf(borrow.getBorrowApr()));// 年华利率
		if (Validator.isNotNull(borrow.getRepayOrgUserId())) {
			BankOpenAccount account = this.getBankOpenAccount(borrow.getRepayOrgUserId());
			if (Validator.isNotNull(account)) {
				debtRegistBean.setBailAccountId(account.getAccount());
			}
		}
		debtRegistBean.setLogOrderId(orderId);
		debtRegistBean.setLogOrderDate(orderDate);
		debtRegistBean.setLogUserId(String.valueOf(loginUserId));
		debtRegistBean.setLogRemark("借款人标的登记");
		debtRegistBean.setLogClient(0);
		//备案接口(EntrustFlag和ReceiptAccountId要么都传，要么都不传)
		if(borrow.getEntrustedFlg()==1){
			STZHWhiteListExample sTZHWhiteListExample = new STZHWhiteListExample();
			STZHWhiteListExample.Criteria sTZHCra = sTZHWhiteListExample.createCriteria();
			sTZHCra.andStUserIdEqualTo(borrow.getEntrustedUserId());
			List<STZHWhiteList> sTZHWhiteList = this.sTZHWhiteListMapper.selectByExample(sTZHWhiteListExample);
			if (sTZHWhiteList != null && sTZHWhiteList.size() == 1) {
				STZHWhiteList stzf = sTZHWhiteList.get(0);
				debtRegistBean.setEntrustFlag(borrow.getEntrustedFlg().toString());
				debtRegistBean.setReceiptAccountId(stzf.getStAccountid());
			}		
		}
		try {
			BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
			return registResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询相应的标的备案状态
	 * 
	 * @param borrow
	 * @param isMonth
	 * @param accountId
	 * @param loginUserId
	 * @return
	 */
	private BankCallBean borrowRegistSearch(String borrowNid, String accountId, int loginUserId) {
		// 获取共同参数
		String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);
		String instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);
		String channel = BankCallConstant.CHANNEL_PC;
		String orderId = GetOrderIdUtils.getOrderId2(loginUserId);
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 调用开户接口
		BankCallBean debtRegistBean = new BankCallBean();
		debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_DETAILS_QUERY);// 消息类型(借款人标的信息查询)
		debtRegistBean.setInstCode(instCode);// 机构代码
		debtRegistBean.setBankCode(bankCode);
		debtRegistBean.setTxDate(txDate);
		debtRegistBean.setTxTime(txTime);
		debtRegistBean.setSeqNo(seqNo);
		debtRegistBean.setChannel(channel);
		debtRegistBean.setAccountId(accountId);// 借款人电子账号
		debtRegistBean.setProductId(borrowNid);// 标的表id
		debtRegistBean.setPageNum("1");
		debtRegistBean.setPageSize("10");
		debtRegistBean.setLogOrderId(orderId);
		debtRegistBean.setLogOrderDate(orderDate);
		debtRegistBean.setLogUserId(String.valueOf(loginUserId));
		debtRegistBean.setLogRemark("借款人标的登记");
		debtRegistBean.setLogClient(0);
		try {
			BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
			return registResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新相应的标的状态
	 * 
	 * @param borrow
	 * @param status
	 * @param registStatus
	 * @return
	 */
	private boolean updateBorrowRegist(BorrowWithBLOBs borrow, int status, int registStatus) {
		Date nowDate = new Date();
		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
		BorrowExample example = new BorrowExample();
		example.createCriteria().andIdEqualTo(borrow.getId()).andStatusEqualTo(borrow.getStatus()).andRegistStatusEqualTo(borrow.getRegistStatus());
		borrow.setRegistStatus(registStatus);
		borrow.setStatus(status);
		borrow.setRegistUserId(Integer.parseInt(adminSystem.getId()));
		borrow.setRegistUserName(adminSystem.getUsername());
		borrow.setRegistTime(nowDate);
		boolean flag = this.borrowMapper.updateByExampleSelective(borrow, example) > 0 ? true : false;
		return flag;
	}

	/**
	 * 获取资产项目类型
	 * 
	 * @return
	 */
	private HjhAssetBorrowType selectAssetBorrowType(String instCode,Integer assetType) {
		HjhAssetBorrowTypeExample example = new HjhAssetBorrowTypeExample();
		HjhAssetBorrowTypeExample.Criteria cra = example.createCriteria();
		cra.andInstCodeEqualTo(instCode);
		cra.andAssetTypeEqualTo(assetType);
		cra.andIsOpenEqualTo(1);

		List<HjhAssetBorrowType> list = this.hjhAssetBorrowTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
		
	}

	/**
	 * 跟标的找资产
	 * 
	 * @return
	 */
	private HjhPlanAsset selectAssetByBorrow(BorrowWithBLOBs borrow) {
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		HjhPlanAssetExample.Criteria cra = example.createCriteria();
		cra.andInstCodeEqualTo(borrow.getInstCode());
		cra.andBorrowNidEqualTo(borrow.getBorrowNid());

		List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
		
	}
	
	/**
	 * 备案成功看标的是否关联计划，如果关联则更新对应资产表
	 * @param borrow
	 * @return
	 */
	private boolean updateBorrowAsset(BorrowWithBLOBs borrow,int status){
		boolean borrowFlag = false;
		
		HjhPlanAsset hjhPlanAsset = this.selectAssetByBorrow(borrow);
		if(hjhPlanAsset != null){
			_log.info(borrow.getInstCode()+" 机构,资产类型  "+borrow.getAssetType()+" 更新资产表状态为初审中,标的号 "+borrow.getBorrowNid());
			// 更新资产表
			HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
			hjhPlanAssetnew.setId(hjhPlanAsset.getId());
			hjhPlanAssetnew.setStatus(status);//初审中
			//获取当前时间
			int nowTime = GetDate.getNowTime10();
			hjhPlanAssetnew.setUpdateTime(nowTime);
			hjhPlanAssetnew.setUpdateUserId(1);
			
		    borrowFlag = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew)>0?true:false;
	        
	        // 受托支付备案成功不推送
	        if(borrow.getEntrustedFlg()==1){
	        	;
	        }else{
			    // 加入到消息队列 
		        Map<String, String> params = new HashMap<String, String>();
		        params.put("mqMsgId", GetCode.getRandomCode(10));
		        params.put("assetId", hjhPlanAsset.getAssetId());
		        params.put("instCode", hjhPlanAsset.getInstCode());
		        
		        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_BORROW_PREAUDIT, JSONObject.toJSONString(params));
				_log.info(hjhPlanAsset.getAssetId()+" 资产 加入队列 ");
	        }
			
		}
		
		return borrowFlag;
	}
	
	private boolean updateEntrustedBorrowRegist(BorrowWithBLOBs borrow, int status, int registStatus) {
		Date nowDate = new Date();
		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
		BorrowExample example = new BorrowExample();
		example.createCriteria().andIdEqualTo(borrow.getId());
		borrow.setRegistStatus(registStatus);
		borrow.setStatus(status);
		borrow.setRegistUserId(Integer.parseInt(adminSystem.getId()));
		borrow.setRegistUserName(adminSystem.getUsername());
		borrow.setRegistTime(nowDate);
		boolean flag = this.borrowMapper.updateByExampleSelective(borrow, example) > 0 ? true : false;
		return flag;
	}
	
}
