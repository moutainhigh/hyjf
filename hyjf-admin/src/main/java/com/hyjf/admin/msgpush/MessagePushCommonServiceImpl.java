package com.hyjf.admin.msgpush;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hyjf.admin.BaseServiceImpl;
import com.hyjf.common.jpush.JPush;
import com.hyjf.common.jpush.JPushPro;
import com.hyjf.common.jpush.JPushYXB;
import com.hyjf.common.jpush.JPushZNB;
import com.hyjf.common.jpush.JPushZYB;
import com.hyjf.common.jpush.JPushZZB;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.HtmlUtil;
import com.hyjf.mybatis.model.auto.MessagePushMsgHistory;
import com.hyjf.mybatis.model.auto.MessagePushTag;
import com.hyjf.mybatis.model.auto.MessagePushTagExample;
import com.hyjf.mybatis.model.customize.MsgPushCommonCustomize;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Service
public class MessagePushCommonServiceImpl extends BaseServiceImpl implements MessagePushCommonService {


	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author Michael
	 */
		
	@Override
	public List<MessagePushTag> getTagList() {
        MessagePushTagExample example = new MessagePushTagExample();
        MessagePushTagExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(1);//启用状态
        example.setOrderByClause("sort asc");
        return this.messagePushTagMapper.selectByExample(example);
			
	}

	/**
	 * 推送极光消息
	 * @param MessagePushMsgHistory msg
	 * @author Michael
	 */
		
	@Override
	public void sendMessage(MessagePushMsgHistory msg) {
		String msgId = ""; // 极光返回id
		String msgProId = "";// 新极光返回id
		String msgZNBID = "";// 周年版返回id
        String msgYXBID = "";// 悦享版返回id
        String msgZZBID = "";// 至尊版返回id
        String msgZYBID = "";// 专业版返回id
		String errorMsg = "";// 错误消息
		Integer userId = null; // 用户id
		String pcode = "";// 包区分 39新极光 79老极光
		// 发送实体
		PushPayload payload = null;
		if (msg == null) {
			return;
		}
		// 判断客户端
		String clientStr = msg.getMsgTerminal();
		// 判断是否发送所有人 0发送所有人 1个人
		if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {

			if (CustomConstants.CLIENT_ANDROID.equals(clientStr)) {// 单独发送安卓客户端
				payload = JPush.buildPushObject_android_tag_alertWithTitle(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
			} else if (CustomConstants.CLIENT_IOS.equals(clientStr)) {// 单发ios客户端
				payload = JPush.buildPushObject_ios_tag_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
			} else {// 所有客户端
				payload = JPush.buildPushObject_android_and_ios(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
			}
			// 个人用户推送
		} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
			MsgPushCommonCustomize commonBean = msgPushCommonCustomizeMapper.getMobileCodeByNumber(msg.getMsgDestination());
			if (commonBean != null) {
				userId = commonBean.getUserId();
				pcode = commonBean.getPackageCode();
				if (StringUtils.isEmpty(commonBean.getMobileCode())) {
					errorMsg = "该用户设备不存在";
				} else {
					payload = JPush.buildPushObject_all_alias_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl(), commonBean.getMobileCode());
				}
			} else {
				errorMsg = "该用户设备不存在";
			}
		}
		// 调用结果
		PushResult result = null;
		try {
			/**
			 * 消息发送 1、发送所有人，判断是否为ios客户端，如果是两个极光发送 2、发送个人，判断该用户是否为 39 或 79
			 * 39为新极光推送
			 */
			if (payload != null) {
				if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
					if (clientStr.contains(CustomConstants.CLIENT_IOS)) {
					    if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
                            result = JPushPro.getClientInstance().sendPush(payload);
                            msgProId = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
                            result = JPushYXB.getClientInstance().sendPush(payload);
                            msgYXBID = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
                            result = JPushZNB.getClientInstance().sendPush(payload);
                            msgZNBID = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
                            result = JPushZYB.getClientInstance().sendPush(payload);
                            msgZYBID = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
                            result = JPushZZB.getClientInstance().sendPush(payload);
                            msgZZBID = String.valueOf(result.msg_id);
                        }
					}
					result = JPush.getClientInstance().sendPush(payload);
					msgId = String.valueOf(result.msg_id);
				} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
					if (StringUtils.isNotEmpty(pcode)) {
					    if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
                            result = JPushPro.getClientInstance().sendPush(payload);
                            msgProId = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
                            result = JPushYXB.getClientInstance().sendPush(payload);
                            msgYXBID = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
                            result = JPushZNB.getClientInstance().sendPush(payload);
                            msgZNBID = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
                            result = JPushZYB.getClientInstance().sendPush(payload);
                            msgZYBID = String.valueOf(result.msg_id);
                        }else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
                            result = JPushZZB.getClientInstance().sendPush(payload);
                            msgZZBID = String.valueOf(result.msg_id);
                        } else {
                            result = JPush.getClientInstance().sendPush(payload);
                            msgId = String.valueOf(result.msg_id);
                        }
					}
				}
			} else {
				errorMsg = "发送失败，用户不存在";
			}
		} catch (APIConnectionException e) {
			errorMsg = "调用极光接口异常,连接超时";
			e.printStackTrace();
		} catch (APIRequestException e) {
			errorMsg = "调用极光接口异常,用户不存在";
			e.printStackTrace();
		}
		// 成功
		if (StringUtils.isNotEmpty(msgId)) {
			msg.setSendTime(GetDate.getNowTime10());
			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_1);
			msg.setMsgJpushId(msgId);
			msg.setMsgJpushProId(msgProId);
            msg.setMsgJpushYxbId(msgYXBID);
            msg.setMsgJpushZnbId(msgZNBID);
            msg.setMsgJpushZybId(msgZYBID);
            msg.setMsgJpushZzbId(msgZZBID);
            System.out.println("发送消息成功：msgId: " + msgId);
            System.out.println("发送消息成功：msgProId: " + msgProId);
            System.out.println("发送消息成功：msgYXBID: " + msgYXBID);
            System.out.println("发送消息成功：msgZNBID: " + msgZNBID);
            System.out.println("发送消息成功：msgZYBID: " + msgZYBID);
            System.out.println("发送消息成功：msgZZBID: " + msgZZBID);
		} else {
			msg.setSendTime(GetDate.getNowTime10());
			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_2);
			msg.setMsgRemark(errorMsg);
		}
		// 更新userid
		if (userId != null) {
			msg.setMsgUserId(userId);
		}
		// 更新操作
		messagePushMsgHistoryMapper.updateByPrimaryKeySelective(msg);
	}


}
