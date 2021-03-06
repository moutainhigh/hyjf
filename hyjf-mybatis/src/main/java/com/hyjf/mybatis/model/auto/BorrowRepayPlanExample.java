package com.hyjf.mybatis.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepayPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowRepayPlanExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNull() {
            addCriterion("borrow_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNotNull() {
            addCriterion("borrow_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidEqualTo(String value) {
            addCriterion("borrow_nid =", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotEqualTo(String value) {
            addCriterion("borrow_nid <>", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThan(String value) {
            addCriterion("borrow_nid >", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_nid >=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThan(String value) {
            addCriterion("borrow_nid <", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_nid <=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLike(String value) {
            addCriterion("borrow_nid like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotLike(String value) {
            addCriterion("borrow_nid not like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIn(List<String> values) {
            addCriterion("borrow_nid in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotIn(List<String> values) {
            addCriterion("borrow_nid not in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidBetween(String value1, String value2) {
            addCriterion("borrow_nid between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotBetween(String value1, String value2) {
            addCriterion("borrow_nid not between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andNidIsNull() {
            addCriterion("nid is null");
            return (Criteria) this;
        }

        public Criteria andNidIsNotNull() {
            addCriterion("nid is not null");
            return (Criteria) this;
        }

        public Criteria andNidEqualTo(String value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(String value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(String value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(String value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(String value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(String value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLike(String value) {
            addCriterion("nid like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotLike(String value) {
            addCriterion("nid not like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<String> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<String> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(String value1, String value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(String value1, String value2) {
            addCriterion("nid not between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNull() {
            addCriterion("repay_type is null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNotNull() {
            addCriterion("repay_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeEqualTo(String value) {
            addCriterion("repay_type =", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotEqualTo(String value) {
            addCriterion("repay_type <>", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThan(String value) {
            addCriterion("repay_type >", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_type >=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThan(String value) {
            addCriterion("repay_type <", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThanOrEqualTo(String value) {
            addCriterion("repay_type <=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLike(String value) {
            addCriterion("repay_type like", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotLike(String value) {
            addCriterion("repay_type not like", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIn(List<String> values) {
            addCriterion("repay_type in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotIn(List<String> values) {
            addCriterion("repay_type not in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeBetween(String value1, String value2) {
            addCriterion("repay_type between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotBetween(String value1, String value2) {
            addCriterion("repay_type not between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayFeeIsNull() {
            addCriterion("repay_fee is null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeIsNotNull() {
            addCriterion("repay_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeEqualTo(BigDecimal value) {
            addCriterion("repay_fee =", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee <>", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeGreaterThan(BigDecimal value) {
            addCriterion("repay_fee >", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee >=", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLessThan(BigDecimal value) {
            addCriterion("repay_fee <", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee <=", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeIn(List<BigDecimal> values) {
            addCriterion("repay_fee in", values, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee not in", values, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee between", value1, value2, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee not between", value1, value2, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayDaysIsNull() {
            addCriterion("repay_days is null");
            return (Criteria) this;
        }

        public Criteria andRepayDaysIsNotNull() {
            addCriterion("repay_days is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDaysEqualTo(String value) {
            addCriterion("repay_days =", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysNotEqualTo(String value) {
            addCriterion("repay_days <>", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysGreaterThan(String value) {
            addCriterion("repay_days >", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysGreaterThanOrEqualTo(String value) {
            addCriterion("repay_days >=", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysLessThan(String value) {
            addCriterion("repay_days <", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysLessThanOrEqualTo(String value) {
            addCriterion("repay_days <=", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysLike(String value) {
            addCriterion("repay_days like", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysNotLike(String value) {
            addCriterion("repay_days not like", value, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysIn(List<String> values) {
            addCriterion("repay_days in", values, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysNotIn(List<String> values) {
            addCriterion("repay_days not in", values, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysBetween(String value1, String value2) {
            addCriterion("repay_days between", value1, value2, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayDaysNotBetween(String value1, String value2) {
            addCriterion("repay_days not between", value1, value2, "repayDays");
            return (Criteria) this;
        }

        public Criteria andRepayStepIsNull() {
            addCriterion("repay_step is null");
            return (Criteria) this;
        }

        public Criteria andRepayStepIsNotNull() {
            addCriterion("repay_step is not null");
            return (Criteria) this;
        }

        public Criteria andRepayStepEqualTo(Integer value) {
            addCriterion("repay_step =", value, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepNotEqualTo(Integer value) {
            addCriterion("repay_step <>", value, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepGreaterThan(Integer value) {
            addCriterion("repay_step >", value, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_step >=", value, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepLessThan(Integer value) {
            addCriterion("repay_step <", value, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepLessThanOrEqualTo(Integer value) {
            addCriterion("repay_step <=", value, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepIn(List<Integer> values) {
            addCriterion("repay_step in", values, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepNotIn(List<Integer> values) {
            addCriterion("repay_step not in", values, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepBetween(Integer value1, Integer value2) {
            addCriterion("repay_step between", value1, value2, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayStepNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_step not between", value1, value2, "repayStep");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIsNull() {
            addCriterion("repay_action_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIsNotNull() {
            addCriterion("repay_action_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeEqualTo(String value) {
            addCriterion("repay_action_time =", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotEqualTo(String value) {
            addCriterion("repay_action_time <>", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeGreaterThan(String value) {
            addCriterion("repay_action_time >", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_action_time >=", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLessThan(String value) {
            addCriterion("repay_action_time <", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_action_time <=", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLike(String value) {
            addCriterion("repay_action_time like", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotLike(String value) {
            addCriterion("repay_action_time not like", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIn(List<String> values) {
            addCriterion("repay_action_time in", values, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotIn(List<String> values) {
            addCriterion("repay_action_time not in", values, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeBetween(String value1, String value2) {
            addCriterion("repay_action_time between", value1, value2, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotBetween(String value1, String value2) {
            addCriterion("repay_action_time not between", value1, value2, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayStatusIsNull() {
            addCriterion("repay_status is null");
            return (Criteria) this;
        }

        public Criteria andRepayStatusIsNotNull() {
            addCriterion("repay_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepayStatusEqualTo(Integer value) {
            addCriterion("repay_status =", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusNotEqualTo(Integer value) {
            addCriterion("repay_status <>", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusGreaterThan(Integer value) {
            addCriterion("repay_status >", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_status >=", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusLessThan(Integer value) {
            addCriterion("repay_status <", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("repay_status <=", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusIn(List<Integer> values) {
            addCriterion("repay_status in", values, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusNotIn(List<Integer> values) {
            addCriterion("repay_status not in", values, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusBetween(Integer value1, Integer value2) {
            addCriterion("repay_status between", value1, value2, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_status not between", value1, value2, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodIsNull() {
            addCriterion("repay_period is null");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodIsNotNull() {
            addCriterion("repay_period is not null");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodEqualTo(Integer value) {
            addCriterion("repay_period =", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodNotEqualTo(Integer value) {
            addCriterion("repay_period <>", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodGreaterThan(Integer value) {
            addCriterion("repay_period >", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_period >=", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodLessThan(Integer value) {
            addCriterion("repay_period <", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("repay_period <=", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodIn(List<Integer> values) {
            addCriterion("repay_period in", values, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodNotIn(List<Integer> values) {
            addCriterion("repay_period not in", values, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodBetween(Integer value1, Integer value2) {
            addCriterion("repay_period between", value1, value2, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_period not between", value1, value2, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNull() {
            addCriterion("repay_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNotNull() {
            addCriterion("repay_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeEqualTo(String value) {
            addCriterion("repay_time =", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotEqualTo(String value) {
            addCriterion("repay_time <>", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThan(String value) {
            addCriterion("repay_time >", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_time >=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThan(String value) {
            addCriterion("repay_time <", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_time <=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLike(String value) {
            addCriterion("repay_time like", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotLike(String value) {
            addCriterion("repay_time not like", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIn(List<String> values) {
            addCriterion("repay_time in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotIn(List<String> values) {
            addCriterion("repay_time not in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeBetween(String value1, String value2) {
            addCriterion("repay_time between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotBetween(String value1, String value2) {
            addCriterion("repay_time not between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeIsNull() {
            addCriterion("repay_yestime is null");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeIsNotNull() {
            addCriterion("repay_yestime is not null");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeEqualTo(String value) {
            addCriterion("repay_yestime =", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeNotEqualTo(String value) {
            addCriterion("repay_yestime <>", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeGreaterThan(String value) {
            addCriterion("repay_yestime >", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_yestime >=", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeLessThan(String value) {
            addCriterion("repay_yestime <", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeLessThanOrEqualTo(String value) {
            addCriterion("repay_yestime <=", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeLike(String value) {
            addCriterion("repay_yestime like", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeNotLike(String value) {
            addCriterion("repay_yestime not like", value, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeIn(List<String> values) {
            addCriterion("repay_yestime in", values, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeNotIn(List<String> values) {
            addCriterion("repay_yestime not in", values, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeBetween(String value1, String value2) {
            addCriterion("repay_yestime between", value1, value2, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayYestimeNotBetween(String value1, String value2) {
            addCriterion("repay_yestime not between", value1, value2, "repayYestime");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNull() {
            addCriterion("repay_account_all is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNotNull() {
            addCriterion("repay_account_all is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllEqualTo(BigDecimal value) {
            addCriterion("repay_account_all =", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <>", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThan(BigDecimal value) {
            addCriterion("repay_account_all >", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all >=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThan(BigDecimal value) {
            addCriterion("repay_account_all <", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIn(List<BigDecimal> values) {
            addCriterion("repay_account_all in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_all not in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all not between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIsNull() {
            addCriterion("repay_account is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIsNotNull() {
            addCriterion("repay_account is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountEqualTo(BigDecimal value) {
            addCriterion("repay_account =", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotEqualTo(BigDecimal value) {
            addCriterion("repay_account <>", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountGreaterThan(BigDecimal value) {
            addCriterion("repay_account >", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account >=", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountLessThan(BigDecimal value) {
            addCriterion("repay_account <", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account <=", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIn(List<BigDecimal> values) {
            addCriterion("repay_account in", values, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotIn(List<BigDecimal> values) {
            addCriterion("repay_account not in", values, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account between", value1, value2, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account not between", value1, value2, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNull() {
            addCriterion("repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNotNull() {
            addCriterion("repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestEqualTo(BigDecimal value) {
            addCriterion("repay_interest =", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest <>", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_interest >", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest >=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThan(BigDecimal value) {
            addCriterion("repay_interest <", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest <=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIn(List<BigDecimal> values) {
            addCriterion("repay_interest in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest not in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest not between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNull() {
            addCriterion("repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNotNull() {
            addCriterion("repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("repay_capital =", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital <>", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("repay_capital >", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital >=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThan(BigDecimal value) {
            addCriterion("repay_capital <", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital <=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("repay_capital in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital not in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital not between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIsNull() {
            addCriterion("repay_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIsNotNull() {
            addCriterion("repay_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes =", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes <>", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_yes >", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes >=", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesLessThan(BigDecimal value) {
            addCriterion("repay_account_yes <", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes <=", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_yes in", values, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_yes not in", values, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_yes between", value1, value2, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_yes not between", value1, value2, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysIsNull() {
            addCriterion("late_repay_days is null");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysIsNotNull() {
            addCriterion("late_repay_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysEqualTo(Integer value) {
            addCriterion("late_repay_days =", value, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysNotEqualTo(Integer value) {
            addCriterion("late_repay_days <>", value, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysGreaterThan(Integer value) {
            addCriterion("late_repay_days >", value, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_repay_days >=", value, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysLessThan(Integer value) {
            addCriterion("late_repay_days <", value, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_repay_days <=", value, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysIn(List<Integer> values) {
            addCriterion("late_repay_days in", values, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysNotIn(List<Integer> values) {
            addCriterion("late_repay_days not in", values, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_repay_days between", value1, value2, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andLateRepayDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_repay_days not between", value1, value2, "lateRepayDays");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIsNull() {
            addCriterion("repay_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIsNotNull() {
            addCriterion("repay_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes =", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes <>", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_interest_yes >", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes >=", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_interest_yes <", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes <=", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_interest_yes in", values, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest_yes not in", values, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_yes between", value1, value2, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_yes not between", value1, value2, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIsNull() {
            addCriterion("repay_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIsNotNull() {
            addCriterion("repay_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes =", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes <>", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("repay_capital_yes >", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes >=", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesLessThan(BigDecimal value) {
            addCriterion("repay_capital_yes <", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes <=", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIn(List<BigDecimal> values) {
            addCriterion("repay_capital_yes in", values, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital_yes not in", values, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_yes between", value1, value2, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_yes not between", value1, value2, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIsNull() {
            addCriterion("repay_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIsNotNull() {
            addCriterion("repay_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait =", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait <>", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_capital_wait >", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait >=", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitLessThan(BigDecimal value) {
            addCriterion("repay_capital_wait <", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait <=", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("repay_capital_wait in", values, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital_wait not in", values, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_wait between", value1, value2, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_wait not between", value1, value2, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIsNull() {
            addCriterion("repay_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIsNotNull() {
            addCriterion("repay_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait =", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait <>", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_interest_wait >", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait >=", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitLessThan(BigDecimal value) {
            addCriterion("repay_interest_wait <", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait <=", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIn(List<BigDecimal> values) {
            addCriterion("repay_interest_wait in", values, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest_wait not in", values, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_wait between", value1, value2, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_wait not between", value1, value2, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayWebIsNull() {
            addCriterion("repay_web is null");
            return (Criteria) this;
        }

        public Criteria andRepayWebIsNotNull() {
            addCriterion("repay_web is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWebEqualTo(Integer value) {
            addCriterion("repay_web =", value, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebNotEqualTo(Integer value) {
            addCriterion("repay_web <>", value, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebGreaterThan(Integer value) {
            addCriterion("repay_web >", value, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_web >=", value, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebLessThan(Integer value) {
            addCriterion("repay_web <", value, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebLessThanOrEqualTo(Integer value) {
            addCriterion("repay_web <=", value, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebIn(List<Integer> values) {
            addCriterion("repay_web in", values, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebNotIn(List<Integer> values) {
            addCriterion("repay_web not in", values, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebBetween(Integer value1, Integer value2) {
            addCriterion("repay_web between", value1, value2, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_web not between", value1, value2, "repayWeb");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeIsNull() {
            addCriterion("repay_web_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeIsNotNull() {
            addCriterion("repay_web_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeEqualTo(String value) {
            addCriterion("repay_web_time =", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeNotEqualTo(String value) {
            addCriterion("repay_web_time <>", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeGreaterThan(String value) {
            addCriterion("repay_web_time >", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_web_time >=", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeLessThan(String value) {
            addCriterion("repay_web_time <", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_web_time <=", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeLike(String value) {
            addCriterion("repay_web_time like", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeNotLike(String value) {
            addCriterion("repay_web_time not like", value, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeIn(List<String> values) {
            addCriterion("repay_web_time in", values, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeNotIn(List<String> values) {
            addCriterion("repay_web_time not in", values, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeBetween(String value1, String value2) {
            addCriterion("repay_web_time between", value1, value2, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebTimeNotBetween(String value1, String value2) {
            addCriterion("repay_web_time not between", value1, value2, "repayWebTime");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepIsNull() {
            addCriterion("repay_web_step is null");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepIsNotNull() {
            addCriterion("repay_web_step is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepEqualTo(Integer value) {
            addCriterion("repay_web_step =", value, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepNotEqualTo(Integer value) {
            addCriterion("repay_web_step <>", value, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepGreaterThan(Integer value) {
            addCriterion("repay_web_step >", value, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_web_step >=", value, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepLessThan(Integer value) {
            addCriterion("repay_web_step <", value, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepLessThanOrEqualTo(Integer value) {
            addCriterion("repay_web_step <=", value, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepIn(List<Integer> values) {
            addCriterion("repay_web_step in", values, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepNotIn(List<Integer> values) {
            addCriterion("repay_web_step not in", values, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepBetween(Integer value1, Integer value2) {
            addCriterion("repay_web_step between", value1, value2, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebStepNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_web_step not between", value1, value2, "repayWebStep");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountIsNull() {
            addCriterion("repay_web_account is null");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountIsNotNull() {
            addCriterion("repay_web_account is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountEqualTo(BigDecimal value) {
            addCriterion("repay_web_account =", value, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountNotEqualTo(BigDecimal value) {
            addCriterion("repay_web_account <>", value, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountGreaterThan(BigDecimal value) {
            addCriterion("repay_web_account >", value, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_web_account >=", value, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountLessThan(BigDecimal value) {
            addCriterion("repay_web_account <", value, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_web_account <=", value, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountIn(List<BigDecimal> values) {
            addCriterion("repay_web_account in", values, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountNotIn(List<BigDecimal> values) {
            addCriterion("repay_web_account not in", values, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_web_account between", value1, value2, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWebAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_web_account not between", value1, value2, "repayWebAccount");
            return (Criteria) this;
        }

        public Criteria andRepayVouchIsNull() {
            addCriterion("repay_vouch is null");
            return (Criteria) this;
        }

        public Criteria andRepayVouchIsNotNull() {
            addCriterion("repay_vouch is not null");
            return (Criteria) this;
        }

        public Criteria andRepayVouchEqualTo(Integer value) {
            addCriterion("repay_vouch =", value, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchNotEqualTo(Integer value) {
            addCriterion("repay_vouch <>", value, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchGreaterThan(Integer value) {
            addCriterion("repay_vouch >", value, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_vouch >=", value, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchLessThan(Integer value) {
            addCriterion("repay_vouch <", value, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchLessThanOrEqualTo(Integer value) {
            addCriterion("repay_vouch <=", value, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchIn(List<Integer> values) {
            addCriterion("repay_vouch in", values, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchNotIn(List<Integer> values) {
            addCriterion("repay_vouch not in", values, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchBetween(Integer value1, Integer value2) {
            addCriterion("repay_vouch between", value1, value2, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andRepayVouchNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_vouch not between", value1, value2, "repayVouch");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIsNull() {
            addCriterion("advance_status is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIsNotNull() {
            addCriterion("advance_status is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusEqualTo(Integer value) {
            addCriterion("advance_status =", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotEqualTo(Integer value) {
            addCriterion("advance_status <>", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusGreaterThan(Integer value) {
            addCriterion("advance_status >", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("advance_status >=", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusLessThan(Integer value) {
            addCriterion("advance_status <", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("advance_status <=", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIn(List<Integer> values) {
            addCriterion("advance_status in", values, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotIn(List<Integer> values) {
            addCriterion("advance_status not in", values, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusBetween(Integer value1, Integer value2) {
            addCriterion("advance_status between", value1, value2, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("advance_status not between", value1, value2, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIsNull() {
            addCriterion("charge_days is null");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIsNotNull() {
            addCriterion("charge_days is not null");
            return (Criteria) this;
        }

        public Criteria andChargeDaysEqualTo(Integer value) {
            addCriterion("charge_days =", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotEqualTo(Integer value) {
            addCriterion("charge_days <>", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysGreaterThan(Integer value) {
            addCriterion("charge_days >", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_days >=", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysLessThan(Integer value) {
            addCriterion("charge_days <", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysLessThanOrEqualTo(Integer value) {
            addCriterion("charge_days <=", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIn(List<Integer> values) {
            addCriterion("charge_days in", values, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotIn(List<Integer> values) {
            addCriterion("charge_days not in", values, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysBetween(Integer value1, Integer value2) {
            addCriterion("charge_days between", value1, value2, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_days not between", value1, value2, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIsNull() {
            addCriterion("charge_interest is null");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIsNotNull() {
            addCriterion("charge_interest is not null");
            return (Criteria) this;
        }

        public Criteria andChargeInterestEqualTo(BigDecimal value) {
            addCriterion("charge_interest =", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotEqualTo(BigDecimal value) {
            addCriterion("charge_interest <>", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestGreaterThan(BigDecimal value) {
            addCriterion("charge_interest >", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_interest >=", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestLessThan(BigDecimal value) {
            addCriterion("charge_interest <", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_interest <=", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIn(List<BigDecimal> values) {
            addCriterion("charge_interest in", values, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotIn(List<BigDecimal> values) {
            addCriterion("charge_interest not in", values, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_interest between", value1, value2, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_interest not between", value1, value2, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusIsNull() {
            addCriterion("late_repay_status is null");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusIsNotNull() {
            addCriterion("late_repay_status is not null");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusEqualTo(Integer value) {
            addCriterion("late_repay_status =", value, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusNotEqualTo(Integer value) {
            addCriterion("late_repay_status <>", value, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusGreaterThan(Integer value) {
            addCriterion("late_repay_status >", value, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_repay_status >=", value, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusLessThan(Integer value) {
            addCriterion("late_repay_status <", value, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("late_repay_status <=", value, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusIn(List<Integer> values) {
            addCriterion("late_repay_status in", values, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusNotIn(List<Integer> values) {
            addCriterion("late_repay_status not in", values, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusBetween(Integer value1, Integer value2) {
            addCriterion("late_repay_status between", value1, value2, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateRepayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("late_repay_status not between", value1, value2, "lateRepayStatus");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNull() {
            addCriterion("late_days is null");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNotNull() {
            addCriterion("late_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateDaysEqualTo(Integer value) {
            addCriterion("late_days =", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotEqualTo(Integer value) {
            addCriterion("late_days <>", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThan(Integer value) {
            addCriterion("late_days >", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_days >=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThan(Integer value) {
            addCriterion("late_days <", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_days <=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysIn(List<Integer> values) {
            addCriterion("late_days in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotIn(List<Integer> values) {
            addCriterion("late_days not in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_days between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_days not between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNull() {
            addCriterion("late_interest is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNotNull() {
            addCriterion("late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestEqualTo(BigDecimal value) {
            addCriterion("late_interest =", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("late_interest <>", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThan(BigDecimal value) {
            addCriterion("late_interest >", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest >=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThan(BigDecimal value) {
            addCriterion("late_interest <", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest <=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestIn(List<BigDecimal> values) {
            addCriterion("late_interest in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("late_interest not in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest not between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateForfeitIsNull() {
            addCriterion("late_forfeit is null");
            return (Criteria) this;
        }

        public Criteria andLateForfeitIsNotNull() {
            addCriterion("late_forfeit is not null");
            return (Criteria) this;
        }

        public Criteria andLateForfeitEqualTo(BigDecimal value) {
            addCriterion("late_forfeit =", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitNotEqualTo(BigDecimal value) {
            addCriterion("late_forfeit <>", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitGreaterThan(BigDecimal value) {
            addCriterion("late_forfeit >", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_forfeit >=", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitLessThan(BigDecimal value) {
            addCriterion("late_forfeit <", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_forfeit <=", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitIn(List<BigDecimal> values) {
            addCriterion("late_forfeit in", values, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitNotIn(List<BigDecimal> values) {
            addCriterion("late_forfeit not in", values, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_forfeit between", value1, value2, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_forfeit not between", value1, value2, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateReminderIsNull() {
            addCriterion("late_reminder is null");
            return (Criteria) this;
        }

        public Criteria andLateReminderIsNotNull() {
            addCriterion("late_reminder is not null");
            return (Criteria) this;
        }

        public Criteria andLateReminderEqualTo(BigDecimal value) {
            addCriterion("late_reminder =", value, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderNotEqualTo(BigDecimal value) {
            addCriterion("late_reminder <>", value, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderGreaterThan(BigDecimal value) {
            addCriterion("late_reminder >", value, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_reminder >=", value, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderLessThan(BigDecimal value) {
            addCriterion("late_reminder <", value, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_reminder <=", value, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderIn(List<BigDecimal> values) {
            addCriterion("late_reminder in", values, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderNotIn(List<BigDecimal> values) {
            addCriterion("late_reminder not in", values, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_reminder between", value1, value2, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andLateReminderNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_reminder not between", value1, value2, "lateReminder");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIsNull() {
            addCriterion("delay_days is null");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIsNotNull() {
            addCriterion("delay_days is not null");
            return (Criteria) this;
        }

        public Criteria andDelayDaysEqualTo(Integer value) {
            addCriterion("delay_days =", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotEqualTo(Integer value) {
            addCriterion("delay_days <>", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysGreaterThan(Integer value) {
            addCriterion("delay_days >", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("delay_days >=", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysLessThan(Integer value) {
            addCriterion("delay_days <", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysLessThanOrEqualTo(Integer value) {
            addCriterion("delay_days <=", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIn(List<Integer> values) {
            addCriterion("delay_days in", values, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotIn(List<Integer> values) {
            addCriterion("delay_days not in", values, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysBetween(Integer value1, Integer value2) {
            addCriterion("delay_days between", value1, value2, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("delay_days not between", value1, value2, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIsNull() {
            addCriterion("delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIsNotNull() {
            addCriterion("delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestEqualTo(BigDecimal value) {
            addCriterion("delay_interest =", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("delay_interest <>", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("delay_interest >", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest >=", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestLessThan(BigDecimal value) {
            addCriterion("delay_interest <", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest <=", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIn(List<BigDecimal> values) {
            addCriterion("delay_interest in", values, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("delay_interest not in", values, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest between", value1, value2, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest not between", value1, value2, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkIsNull() {
            addCriterion("delay_remark is null");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkIsNotNull() {
            addCriterion("delay_remark is not null");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkEqualTo(String value) {
            addCriterion("delay_remark =", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotEqualTo(String value) {
            addCriterion("delay_remark <>", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkGreaterThan(String value) {
            addCriterion("delay_remark >", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("delay_remark >=", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkLessThan(String value) {
            addCriterion("delay_remark <", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkLessThanOrEqualTo(String value) {
            addCriterion("delay_remark <=", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkLike(String value) {
            addCriterion("delay_remark like", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotLike(String value) {
            addCriterion("delay_remark not like", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkIn(List<String> values) {
            addCriterion("delay_remark in", values, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotIn(List<String> values) {
            addCriterion("delay_remark not in", values, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkBetween(String value1, String value2) {
            addCriterion("delay_remark between", value1, value2, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotBetween(String value1, String value2) {
            addCriterion("delay_remark not between", value1, value2, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNull() {
            addCriterion("addtime is null");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNotNull() {
            addCriterion("addtime is not null");
            return (Criteria) this;
        }

        public Criteria andAddtimeEqualTo(String value) {
            addCriterion("addtime =", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotEqualTo(String value) {
            addCriterion("addtime <>", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThan(String value) {
            addCriterion("addtime >", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThanOrEqualTo(String value) {
            addCriterion("addtime >=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThan(String value) {
            addCriterion("addtime <", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThanOrEqualTo(String value) {
            addCriterion("addtime <=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLike(String value) {
            addCriterion("addtime like", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotLike(String value) {
            addCriterion("addtime not like", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeIn(List<String> values) {
            addCriterion("addtime in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotIn(List<String> values) {
            addCriterion("addtime not in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeBetween(String value1, String value2) {
            addCriterion("addtime between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotBetween(String value1, String value2) {
            addCriterion("addtime not between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddipIsNull() {
            addCriterion("addip is null");
            return (Criteria) this;
        }

        public Criteria andAddipIsNotNull() {
            addCriterion("addip is not null");
            return (Criteria) this;
        }

        public Criteria andAddipEqualTo(String value) {
            addCriterion("addip =", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotEqualTo(String value) {
            addCriterion("addip <>", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipGreaterThan(String value) {
            addCriterion("addip >", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipGreaterThanOrEqualTo(String value) {
            addCriterion("addip >=", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipLessThan(String value) {
            addCriterion("addip <", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipLessThanOrEqualTo(String value) {
            addCriterion("addip <=", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipLike(String value) {
            addCriterion("addip like", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotLike(String value) {
            addCriterion("addip not like", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipIn(List<String> values) {
            addCriterion("addip in", values, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotIn(List<String> values) {
            addCriterion("addip not in", values, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipBetween(String value1, String value2) {
            addCriterion("addip between", value1, value2, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotBetween(String value1, String value2) {
            addCriterion("addip not between", value1, value2, "addip");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceIsNull() {
            addCriterion("repay_money_source is null");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceIsNotNull() {
            addCriterion("repay_money_source is not null");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceEqualTo(Integer value) {
            addCriterion("repay_money_source =", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceNotEqualTo(Integer value) {
            addCriterion("repay_money_source <>", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceGreaterThan(Integer value) {
            addCriterion("repay_money_source >", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_money_source >=", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceLessThan(Integer value) {
            addCriterion("repay_money_source <", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceLessThanOrEqualTo(Integer value) {
            addCriterion("repay_money_source <=", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceIn(List<Integer> values) {
            addCriterion("repay_money_source in", values, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceNotIn(List<Integer> values) {
            addCriterion("repay_money_source not in", values, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceBetween(Integer value1, Integer value2) {
            addCriterion("repay_money_source between", value1, value2, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_money_source not between", value1, value2, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdIsNull() {
            addCriterion("repay_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdIsNotNull() {
            addCriterion("repay_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdEqualTo(Integer value) {
            addCriterion("repay_user_id =", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdNotEqualTo(Integer value) {
            addCriterion("repay_user_id <>", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdGreaterThan(Integer value) {
            addCriterion("repay_user_id >", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_user_id >=", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdLessThan(Integer value) {
            addCriterion("repay_user_id <", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_user_id <=", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdIn(List<Integer> values) {
            addCriterion("repay_user_id in", values, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdNotIn(List<Integer> values) {
            addCriterion("repay_user_id not in", values, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_user_id between", value1, value2, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_user_id not between", value1, value2, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameIsNull() {
            addCriterion("repay_username is null");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameIsNotNull() {
            addCriterion("repay_username is not null");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameEqualTo(String value) {
            addCriterion("repay_username =", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotEqualTo(String value) {
            addCriterion("repay_username <>", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameGreaterThan(String value) {
            addCriterion("repay_username >", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("repay_username >=", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameLessThan(String value) {
            addCriterion("repay_username <", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameLessThanOrEqualTo(String value) {
            addCriterion("repay_username <=", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameLike(String value) {
            addCriterion("repay_username like", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotLike(String value) {
            addCriterion("repay_username not like", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameIn(List<String> values) {
            addCriterion("repay_username in", values, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotIn(List<String> values) {
            addCriterion("repay_username not in", values, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameBetween(String value1, String value2) {
            addCriterion("repay_username between", value1, value2, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotBetween(String value1, String value2) {
            addCriterion("repay_username not between", value1, value2, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderIsNull() {
            addCriterion("repay_sms_reminder is null");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderIsNotNull() {
            addCriterion("repay_sms_reminder is not null");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderEqualTo(Integer value) {
            addCriterion("repay_sms_reminder =", value, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderNotEqualTo(Integer value) {
            addCriterion("repay_sms_reminder <>", value, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderGreaterThan(Integer value) {
            addCriterion("repay_sms_reminder >", value, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_sms_reminder >=", value, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderLessThan(Integer value) {
            addCriterion("repay_sms_reminder <", value, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderLessThanOrEqualTo(Integer value) {
            addCriterion("repay_sms_reminder <=", value, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderIn(List<Integer> values) {
            addCriterion("repay_sms_reminder in", values, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderNotIn(List<Integer> values) {
            addCriterion("repay_sms_reminder not in", values, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderBetween(Integer value1, Integer value2) {
            addCriterion("repay_sms_reminder between", value1, value2, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andRepaySmsReminderNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_sms_reminder not between", value1, value2, "repaySmsReminder");
            return (Criteria) this;
        }

        public Criteria andAutoRepayIsNull() {
            addCriterion("auto_repay is null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayIsNotNull() {
            addCriterion("auto_repay is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEqualTo(Integer value) {
            addCriterion("auto_repay =", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayNotEqualTo(Integer value) {
            addCriterion("auto_repay <>", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayGreaterThan(Integer value) {
            addCriterion("auto_repay >", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_repay >=", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayLessThan(Integer value) {
            addCriterion("auto_repay <", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayLessThanOrEqualTo(Integer value) {
            addCriterion("auto_repay <=", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayIn(List<Integer> values) {
            addCriterion("auto_repay in", values, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayNotIn(List<Integer> values) {
            addCriterion("auto_repay not in", values, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay between", value1, value2, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay not between", value1, value2, "autoRepay");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}