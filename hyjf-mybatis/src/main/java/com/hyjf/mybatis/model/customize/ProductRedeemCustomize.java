package com.hyjf.mybatis.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRedeemCustomize implements Serializable {
    private Integer id;//主键
    private Integer userId;
    private String orderId;
    private BigDecimal amount;
    private String redeemTime;
    private BigDecimal interest;
    private BigDecimal total;
    private Integer referee;
    private Integer area;
    private Integer company;
    private Integer department;
    private String remark;
    private Integer client;
    private String username;
    private String refername;
    private String mobile;
    private String turename;
    private Integer status;
	private String regionName;
	private String branceName;
	private String departmentName;
	protected int limitStart = -1;
    protected int limitEnd = -1;
    
    private String timeStartSrch;//查询用  时间
	private String timeEndSrch;//查询用  时间
    
	
    public String getTimeStartSrch() {
		return timeStartSrch;
	}

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}

	public String getTimeEndSrch() {
		return timeEndSrch;
	}

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

	
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRefername() {
		return refername;
	}

	public void setRefername(String refername) {
		this.refername = refername;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTurename() {
		return turename;
	}

	public void setTurename(String turename) {
		this.turename = turename;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRedeemTime() {
        return redeemTime;
    }

    public void setRedeemTime(String redeemTime) {
        this.redeemTime = redeemTime;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getReferee() {
        return referee;
    }

    public void setReferee(Integer referee) {
        this.referee = referee;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }
    

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getBranceName() {
		return branceName;
	}

	public void setBranceName(String branceName) {
		this.branceName = branceName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}