package com.hyjf.wechat.controller.find.contentarticle;

import java.util.List;

import com.hyjf.mybatis.model.customize.app.ContentArticleCustomize;
import com.hyjf.wechat.BaseResultBeanFrontEnd;

import cn.jpush.api.report.MessagesResult.Message;

public class WechatContentArticleResultVo extends BaseResultBeanFrontEnd {
	/**
	 * 此处为属性说明
	 */
	private static final long serialVersionUID = 5825234430507653546L;
	private Object details;
	private String topTitle;


	private Integer messageCount;

	private List<ContentArticleCustomize> messageList;
	// 网贷知识id
	private String messageId;
	// 网贷知识详情页url
	private String messageUrl;
	// 分享标题
	private String shareTitle;
	// 分享内容
	private String shareContent;
	// 分享图片url
	private String sharePicUrl;
	// 分享url
	private String shareUrl;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getSharePicUrl() {
		return sharePicUrl;
	}

	public void setSharePicUrl(String sharePicUrl) {
		this.sharePicUrl = sharePicUrl;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public String getTopTitle() {
		return topTitle;
	}

	public void setTopTitle(String topTitle) {
		this.topTitle = topTitle;
	}


	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public List<ContentArticleCustomize> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ContentArticleCustomize> messageList) {
		this.messageList = messageList;
	}

}
