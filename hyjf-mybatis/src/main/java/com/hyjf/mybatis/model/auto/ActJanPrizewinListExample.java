package com.hyjf.mybatis.model.auto;

import java.util.ArrayList;
import java.util.List;

public class ActJanPrizewinListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ActJanPrizewinListExample() {
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

        public Criteria andWechatNameIsNull() {
            addCriterion("wechat_name is null");
            return (Criteria) this;
        }

        public Criteria andWechatNameIsNotNull() {
            addCriterion("wechat_name is not null");
            return (Criteria) this;
        }

        public Criteria andWechatNameEqualTo(String value) {
            addCriterion("wechat_name =", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameNotEqualTo(String value) {
            addCriterion("wechat_name <>", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameGreaterThan(String value) {
            addCriterion("wechat_name >", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameGreaterThanOrEqualTo(String value) {
            addCriterion("wechat_name >=", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameLessThan(String value) {
            addCriterion("wechat_name <", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameLessThanOrEqualTo(String value) {
            addCriterion("wechat_name <=", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameLike(String value) {
            addCriterion("wechat_name like", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameNotLike(String value) {
            addCriterion("wechat_name not like", value, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameIn(List<String> values) {
            addCriterion("wechat_name in", values, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameNotIn(List<String> values) {
            addCriterion("wechat_name not in", values, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameBetween(String value1, String value2) {
            addCriterion("wechat_name between", value1, value2, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNameNotBetween(String value1, String value2) {
            addCriterion("wechat_name not between", value1, value2, "wechatName");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameIsNull() {
            addCriterion("wechat_nickname is null");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameIsNotNull() {
            addCriterion("wechat_nickname is not null");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameEqualTo(String value) {
            addCriterion("wechat_nickname =", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameNotEqualTo(String value) {
            addCriterion("wechat_nickname <>", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameGreaterThan(String value) {
            addCriterion("wechat_nickname >", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("wechat_nickname >=", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameLessThan(String value) {
            addCriterion("wechat_nickname <", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameLessThanOrEqualTo(String value) {
            addCriterion("wechat_nickname <=", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameLike(String value) {
            addCriterion("wechat_nickname like", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameNotLike(String value) {
            addCriterion("wechat_nickname not like", value, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameIn(List<String> values) {
            addCriterion("wechat_nickname in", values, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameNotIn(List<String> values) {
            addCriterion("wechat_nickname not in", values, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameBetween(String value1, String value2) {
            addCriterion("wechat_nickname between", value1, value2, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andWechatNicknameNotBetween(String value1, String value2) {
            addCriterion("wechat_nickname not between", value1, value2, "wechatNickname");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andPrizeIdIsNull() {
            addCriterion("prize_id is null");
            return (Criteria) this;
        }

        public Criteria andPrizeIdIsNotNull() {
            addCriterion("prize_id is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeIdEqualTo(Integer value) {
            addCriterion("prize_id =", value, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdNotEqualTo(Integer value) {
            addCriterion("prize_id <>", value, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdGreaterThan(Integer value) {
            addCriterion("prize_id >", value, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_id >=", value, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdLessThan(Integer value) {
            addCriterion("prize_id <", value, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdLessThanOrEqualTo(Integer value) {
            addCriterion("prize_id <=", value, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdIn(List<Integer> values) {
            addCriterion("prize_id in", values, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdNotIn(List<Integer> values) {
            addCriterion("prize_id not in", values, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdBetween(Integer value1, Integer value2) {
            addCriterion("prize_id between", value1, value2, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_id not between", value1, value2, "prizeId");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIsNull() {
            addCriterion("prize_name is null");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIsNotNull() {
            addCriterion("prize_name is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeNameEqualTo(String value) {
            addCriterion("prize_name =", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotEqualTo(String value) {
            addCriterion("prize_name <>", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameGreaterThan(String value) {
            addCriterion("prize_name >", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameGreaterThanOrEqualTo(String value) {
            addCriterion("prize_name >=", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLessThan(String value) {
            addCriterion("prize_name <", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLessThanOrEqualTo(String value) {
            addCriterion("prize_name <=", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLike(String value) {
            addCriterion("prize_name like", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotLike(String value) {
            addCriterion("prize_name not like", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIn(List<String> values) {
            addCriterion("prize_name in", values, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotIn(List<String> values) {
            addCriterion("prize_name not in", values, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameBetween(String value1, String value2) {
            addCriterion("prize_name between", value1, value2, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotBetween(String value1, String value2) {
            addCriterion("prize_name not between", value1, value2, "prizeName");
            return (Criteria) this;
        }

        public Criteria andBookingAddressIsNull() {
            addCriterion("booking_address is null");
            return (Criteria) this;
        }

        public Criteria andBookingAddressIsNotNull() {
            addCriterion("booking_address is not null");
            return (Criteria) this;
        }

        public Criteria andBookingAddressEqualTo(String value) {
            addCriterion("booking_address =", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressNotEqualTo(String value) {
            addCriterion("booking_address <>", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressGreaterThan(String value) {
            addCriterion("booking_address >", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("booking_address >=", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressLessThan(String value) {
            addCriterion("booking_address <", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressLessThanOrEqualTo(String value) {
            addCriterion("booking_address <=", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressLike(String value) {
            addCriterion("booking_address like", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressNotLike(String value) {
            addCriterion("booking_address not like", value, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressIn(List<String> values) {
            addCriterion("booking_address in", values, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressNotIn(List<String> values) {
            addCriterion("booking_address not in", values, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressBetween(String value1, String value2) {
            addCriterion("booking_address between", value1, value2, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingAddressNotBetween(String value1, String value2) {
            addCriterion("booking_address not between", value1, value2, "bookingAddress");
            return (Criteria) this;
        }

        public Criteria andBookingNameIsNull() {
            addCriterion("booking_name is null");
            return (Criteria) this;
        }

        public Criteria andBookingNameIsNotNull() {
            addCriterion("booking_name is not null");
            return (Criteria) this;
        }

        public Criteria andBookingNameEqualTo(String value) {
            addCriterion("booking_name =", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameNotEqualTo(String value) {
            addCriterion("booking_name <>", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameGreaterThan(String value) {
            addCriterion("booking_name >", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameGreaterThanOrEqualTo(String value) {
            addCriterion("booking_name >=", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameLessThan(String value) {
            addCriterion("booking_name <", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameLessThanOrEqualTo(String value) {
            addCriterion("booking_name <=", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameLike(String value) {
            addCriterion("booking_name like", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameNotLike(String value) {
            addCriterion("booking_name not like", value, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameIn(List<String> values) {
            addCriterion("booking_name in", values, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameNotIn(List<String> values) {
            addCriterion("booking_name not in", values, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameBetween(String value1, String value2) {
            addCriterion("booking_name between", value1, value2, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingNameNotBetween(String value1, String value2) {
            addCriterion("booking_name not between", value1, value2, "bookingName");
            return (Criteria) this;
        }

        public Criteria andBookingMobileIsNull() {
            addCriterion("booking_mobile is null");
            return (Criteria) this;
        }

        public Criteria andBookingMobileIsNotNull() {
            addCriterion("booking_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andBookingMobileEqualTo(String value) {
            addCriterion("booking_mobile =", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileNotEqualTo(String value) {
            addCriterion("booking_mobile <>", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileGreaterThan(String value) {
            addCriterion("booking_mobile >", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileGreaterThanOrEqualTo(String value) {
            addCriterion("booking_mobile >=", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileLessThan(String value) {
            addCriterion("booking_mobile <", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileLessThanOrEqualTo(String value) {
            addCriterion("booking_mobile <=", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileLike(String value) {
            addCriterion("booking_mobile like", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileNotLike(String value) {
            addCriterion("booking_mobile not like", value, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileIn(List<String> values) {
            addCriterion("booking_mobile in", values, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileNotIn(List<String> values) {
            addCriterion("booking_mobile not in", values, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileBetween(String value1, String value2) {
            addCriterion("booking_mobile between", value1, value2, "bookingMobile");
            return (Criteria) this;
        }

        public Criteria andBookingMobileNotBetween(String value1, String value2) {
            addCriterion("booking_mobile not between", value1, value2, "bookingMobile");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Integer value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Integer value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Integer value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Integer value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Integer> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Integer> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(Integer value) {
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(Integer value) {
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(Integer value) {
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(Integer value) {
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(Integer value) {
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<Integer> values) {
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<Integer> values) {
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(Integer value1, Integer value2) {
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flg not between", value1, value2, "delFlg");
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