package com.hyjf.mybatis.model.auto;

import java.io.Serializable;

public class ChinapnrSendlogWithBLOBs extends ChinapnrSendlog implements Serializable {
    private String msgdata;

    private String chkvalue;

    private String content;

    private static final long serialVersionUID = 1L;

    public String getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(String msgdata) {
        this.msgdata = msgdata == null ? null : msgdata.trim();
    }

    public String getChkvalue() {
        return chkvalue;
    }

    public void setChkvalue(String chkvalue) {
        this.chkvalue = chkvalue == null ? null : chkvalue.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}