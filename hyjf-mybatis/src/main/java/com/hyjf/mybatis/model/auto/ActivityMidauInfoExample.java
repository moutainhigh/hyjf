package com.hyjf.mybatis.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityMidauInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ActivityMidauInfoExample() {
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

        public Criteria andRewardNameIsNull() {
            addCriterion("reward_name is null");
            return (Criteria) this;
        }

        public Criteria andRewardNameIsNotNull() {
            addCriterion("reward_name is not null");
            return (Criteria) this;
        }

        public Criteria andRewardNameEqualTo(String value) {
            addCriterion("reward_name =", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameNotEqualTo(String value) {
            addCriterion("reward_name <>", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameGreaterThan(String value) {
            addCriterion("reward_name >", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameGreaterThanOrEqualTo(String value) {
            addCriterion("reward_name >=", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameLessThan(String value) {
            addCriterion("reward_name <", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameLessThanOrEqualTo(String value) {
            addCriterion("reward_name <=", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameLike(String value) {
            addCriterion("reward_name like", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameNotLike(String value) {
            addCriterion("reward_name not like", value, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameIn(List<String> values) {
            addCriterion("reward_name in", values, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameNotIn(List<String> values) {
            addCriterion("reward_name not in", values, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameBetween(String value1, String value2) {
            addCriterion("reward_name between", value1, value2, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardNameNotBetween(String value1, String value2) {
            addCriterion("reward_name not between", value1, value2, "rewardName");
            return (Criteria) this;
        }

        public Criteria andRewardIdIsNull() {
            addCriterion("reward_id is null");
            return (Criteria) this;
        }

        public Criteria andRewardIdIsNotNull() {
            addCriterion("reward_id is not null");
            return (Criteria) this;
        }

        public Criteria andRewardIdEqualTo(String value) {
            addCriterion("reward_id =", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotEqualTo(String value) {
            addCriterion("reward_id <>", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdGreaterThan(String value) {
            addCriterion("reward_id >", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdGreaterThanOrEqualTo(String value) {
            addCriterion("reward_id >=", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdLessThan(String value) {
            addCriterion("reward_id <", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdLessThanOrEqualTo(String value) {
            addCriterion("reward_id <=", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdLike(String value) {
            addCriterion("reward_id like", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotLike(String value) {
            addCriterion("reward_id not like", value, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdIn(List<String> values) {
            addCriterion("reward_id in", values, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotIn(List<String> values) {
            addCriterion("reward_id not in", values, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdBetween(String value1, String value2) {
            addCriterion("reward_id between", value1, value2, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardIdNotBetween(String value1, String value2) {
            addCriterion("reward_id not between", value1, value2, "rewardId");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIsNull() {
            addCriterion("reward_type is null");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIsNotNull() {
            addCriterion("reward_type is not null");
            return (Criteria) this;
        }

        public Criteria andRewardTypeEqualTo(String value) {
            addCriterion("reward_type =", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotEqualTo(String value) {
            addCriterion("reward_type <>", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeGreaterThan(String value) {
            addCriterion("reward_type >", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("reward_type >=", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLessThan(String value) {
            addCriterion("reward_type <", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLessThanOrEqualTo(String value) {
            addCriterion("reward_type <=", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeLike(String value) {
            addCriterion("reward_type like", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotLike(String value) {
            addCriterion("reward_type not like", value, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeIn(List<String> values) {
            addCriterion("reward_type in", values, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotIn(List<String> values) {
            addCriterion("reward_type not in", values, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeBetween(String value1, String value2) {
            addCriterion("reward_type between", value1, value2, "rewardType");
            return (Criteria) this;
        }

        public Criteria andRewardTypeNotBetween(String value1, String value2) {
            addCriterion("reward_type not between", value1, value2, "rewardType");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyIsNull() {
            addCriterion("invest_money is null");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyIsNotNull() {
            addCriterion("invest_money is not null");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyEqualTo(BigDecimal value) {
            addCriterion("invest_money =", value, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyNotEqualTo(BigDecimal value) {
            addCriterion("invest_money <>", value, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyGreaterThan(BigDecimal value) {
            addCriterion("invest_money >", value, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_money >=", value, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyLessThan(BigDecimal value) {
            addCriterion("invest_money <", value, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_money <=", value, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyIn(List<BigDecimal> values) {
            addCriterion("invest_money in", values, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyNotIn(List<BigDecimal> values) {
            addCriterion("invest_money not in", values, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_money between", value1, value2, "investMoney");
            return (Criteria) this;
        }

        public Criteria andInvestMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_money not between", value1, value2, "investMoney");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNull() {
            addCriterion("product_type is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNotNull() {
            addCriterion("product_type is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeEqualTo(String value) {
            addCriterion("product_type =", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotEqualTo(String value) {
            addCriterion("product_type <>", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThan(String value) {
            addCriterion("product_type >", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThanOrEqualTo(String value) {
            addCriterion("product_type >=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThan(String value) {
            addCriterion("product_type <", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThanOrEqualTo(String value) {
            addCriterion("product_type <=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLike(String value) {
            addCriterion("product_type like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotLike(String value) {
            addCriterion("product_type not like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeIn(List<String> values) {
            addCriterion("product_type in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotIn(List<String> values) {
            addCriterion("product_type not in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeBetween(String value1, String value2) {
            addCriterion("product_type between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotBetween(String value1, String value2) {
            addCriterion("product_type not between", value1, value2, "productType");
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

        public Criteria andProductStyleIsNull() {
            addCriterion("product_style is null");
            return (Criteria) this;
        }

        public Criteria andProductStyleIsNotNull() {
            addCriterion("product_style is not null");
            return (Criteria) this;
        }

        public Criteria andProductStyleEqualTo(String value) {
            addCriterion("product_style =", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleNotEqualTo(String value) {
            addCriterion("product_style <>", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleGreaterThan(String value) {
            addCriterion("product_style >", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleGreaterThanOrEqualTo(String value) {
            addCriterion("product_style >=", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleLessThan(String value) {
            addCriterion("product_style <", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleLessThanOrEqualTo(String value) {
            addCriterion("product_style <=", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleLike(String value) {
            addCriterion("product_style like", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleNotLike(String value) {
            addCriterion("product_style not like", value, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleIn(List<String> values) {
            addCriterion("product_style in", values, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleNotIn(List<String> values) {
            addCriterion("product_style not in", values, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleBetween(String value1, String value2) {
            addCriterion("product_style between", value1, value2, "productStyle");
            return (Criteria) this;
        }

        public Criteria andProductStyleNotBetween(String value1, String value2) {
            addCriterion("product_style not between", value1, value2, "productStyle");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusIsNull() {
            addCriterion("distribution_status is null");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusIsNotNull() {
            addCriterion("distribution_status is not null");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusEqualTo(Integer value) {
            addCriterion("distribution_status =", value, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusNotEqualTo(Integer value) {
            addCriterion("distribution_status <>", value, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusGreaterThan(Integer value) {
            addCriterion("distribution_status >", value, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("distribution_status >=", value, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusLessThan(Integer value) {
            addCriterion("distribution_status <", value, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusLessThanOrEqualTo(Integer value) {
            addCriterion("distribution_status <=", value, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusIn(List<Integer> values) {
            addCriterion("distribution_status in", values, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusNotIn(List<Integer> values) {
            addCriterion("distribution_status not in", values, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusBetween(Integer value1, Integer value2) {
            addCriterion("distribution_status between", value1, value2, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andDistributionStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("distribution_status not between", value1, value2, "distributionStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusIsNull() {
            addCriterion("reward_status is null");
            return (Criteria) this;
        }

        public Criteria andRewardStatusIsNotNull() {
            addCriterion("reward_status is not null");
            return (Criteria) this;
        }

        public Criteria andRewardStatusEqualTo(Integer value) {
            addCriterion("reward_status =", value, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusNotEqualTo(Integer value) {
            addCriterion("reward_status <>", value, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusGreaterThan(Integer value) {
            addCriterion("reward_status >", value, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_status >=", value, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusLessThan(Integer value) {
            addCriterion("reward_status <", value, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusLessThanOrEqualTo(Integer value) {
            addCriterion("reward_status <=", value, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusIn(List<Integer> values) {
            addCriterion("reward_status in", values, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusNotIn(List<Integer> values) {
            addCriterion("reward_status not in", values, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusBetween(Integer value1, Integer value2) {
            addCriterion("reward_status between", value1, value2, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andRewardStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_status not between", value1, value2, "rewardStatus");
            return (Criteria) this;
        }

        public Criteria andInvestTimeIsNull() {
            addCriterion("invest_time is null");
            return (Criteria) this;
        }

        public Criteria andInvestTimeIsNotNull() {
            addCriterion("invest_time is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTimeEqualTo(Date value) {
            addCriterion("invest_time =", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeNotEqualTo(Date value) {
            addCriterion("invest_time <>", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeGreaterThan(Date value) {
            addCriterion("invest_time >", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("invest_time >=", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeLessThan(Date value) {
            addCriterion("invest_time <", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeLessThanOrEqualTo(Date value) {
            addCriterion("invest_time <=", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeIn(List<Date> values) {
            addCriterion("invest_time in", values, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeNotIn(List<Date> values) {
            addCriterion("invest_time not in", values, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeBetween(Date value1, Date value2) {
            addCriterion("invest_time between", value1, value2, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeNotBetween(Date value1, Date value2) {
            addCriterion("invest_time not between", value1, value2, "investTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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