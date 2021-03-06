package com.hyjf.mybatis.model.auto;

import java.io.Serializable;

public class Utm implements Serializable {
    private Integer utmId;

    private String utmSource;

    private Integer sourceId;

    private String utmMedium;

    private String utmTerm;

    private String utmContent;

    private String utmCampaign;

    private Integer utmReferrer;

    private String linkAddress;

    private Integer createTime;

    private String remark;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource == null ? null : utmSource.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium == null ? null : utmMedium.trim();
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm == null ? null : utmTerm.trim();
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent == null ? null : utmContent.trim();
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign == null ? null : utmCampaign.trim();
    }

    public Integer getUtmReferrer() {
        return utmReferrer;
    }

    public void setUtmReferrer(Integer utmReferrer) {
        this.utmReferrer = utmReferrer;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress == null ? null : linkAddress.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}