package com.hyjf.web.user.financialadvisor;
/**
 * Description:用户出借实现类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 郭勇
 * @version: 1.0
 *           Created at: 2015年12月4日 下午1:50:02
 *           Modification History:
 *           Modified by :
 */

import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.evalation.EvaluationService;
import com.hyjf.bank.service.mq.MqService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.RabbitMQConstants;
import com.hyjf.common.util.StringUtil;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.mybatis.model.customize.*;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import com.hyjf.web.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialAdvisorServiceImpl extends BaseServiceImpl implements FinancialAdvisorService {
    @Autowired
    private MqService mqService;
    @Autowired
    EvaluationService evaluationService;

    @Override
    public List<QuestionCustomize> getQuestionList() {
        List<QuestionCustomize> customizes = questionCustomizeMapper.getQuestionList();
        return customizes;
    }

    @Override
    public UserEvalationResultCustomize selectUserEvalationResultByUserId(Integer userId) {

        UserEvalationResultExampleCustomize example = new UserEvalationResultExampleCustomize();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserEvalationResultCustomize> userEvalationResultCustomize = userEvalationResultCustomizeMapper.selectByExample(example);
        if (userEvalationResultCustomize != null && userEvalationResultCustomize.size() > 0) {
            return userEvalationResultCustomize.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int countScore(List<String> answerList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", answerList);
        int countScore = questionCustomizeMapper.countScore(answerList);
        return countScore;
    }

    @Override
    public EvalationCustomize getEvalationByCountScore(int countScore) {
        EvalationExampleCustomize example = new EvalationExampleCustomize();
        example.createCriteria().andScoreUpLessThanOrEqualTo(countScore).andScoreDownGreaterThanOrEqualTo(countScore).andStatusEqualTo(0);
        List<EvalationCustomize> list=evalationCustomizeMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserEvalationResultCustomize insertUserEvalationResult(List<String> answerList, List<String> questionList,
                                                                  EvalationCustomize evalationCustomize, int countScore, Integer userId, UserEvalationResultCustomize oldUserEvalationResultCustomize) {
        UserEvalationResultCustomize userEvalationResultCustomize =new UserEvalationResultCustomize();
        userEvalationResultCustomize.setUserId(userId);
        userEvalationResultCustomize.setScoreCount(countScore);
        userEvalationResultCustomize.setType(evalationCustomize.getType());
        userEvalationResultCustomize.setSummary(evalationCustomize.getSummary());
        userEvalationResultCustomize.setCreatetime(new Date());
        if(oldUserEvalationResultCustomize !=null){
            userEvalationResultCustomize.setLasttime(oldUserEvalationResultCustomize.getCreatetime());
        }
        
        int i= userEvalationResultCustomizeMapper.insertSelective(userEvalationResultCustomize);
        if(i>0){
            // 更新用户信息
            Users user = this.getUsers(userId);
            if (user != null){
                user.setIsEvaluationFlag(1);// 已测评
                // 获取测评到期日期
                Date evaluationExpiredTime = evaluationService.selectEvaluationExpiredTime(new Date());
                user.setEvaluationExpiredTime(evaluationExpiredTime);
                // 更新用户是否测评标志位
                this.usersMapper.updateByPrimaryKey(user);
            }
            for (int j = 0; j < answerList.size(); j++) {
                UserEvalation userEvalation=new UserEvalation();
                userEvalation.setErId(userEvalationResultCustomize.getId());
                userEvalation.setQuestionId(new Integer(questionList.get(j)));
                userEvalation.setAnswerId(new Integer(answerList.get(j)));
                userEvalation.setSort(j);
                userEvalationMapper.insertSelective(userEvalation);
            }

            // add 合规数据上报 埋点 liubin 20181122 start
            // 推送数据到MQ 用户信息修改（风险测评）
            JSONObject params = new JSONObject();
            params.put("userId", userId);
            this.mqService.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.USERINFO_CHANGE_DELAY_KEY, JSONObject.toJSONString(params));
            // add 合规数据上报 埋点 liubin 20181122 end

        }
        return userEvalationResultCustomize;
    }

    @Override
    public List<String> getTypeList() {
        
        return questionCustomizeMapper.getTypeList();
    }

    @Override
    public void deleteUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultExampleCustomize userEvalationResultExampleCustomize =new UserEvalationResultExampleCustomize();
        userEvalationResultExampleCustomize.createCriteria().andUserIdEqualTo(userId);
        List<UserEvalationResultCustomize> userEvalationResultCustomizes = userEvalationResultCustomizeMapper.selectByExample(userEvalationResultExampleCustomize);
        UserEvalationResultCustomize userEvalationResultCustomize =null;
        if(userEvalationResultCustomizes !=null&& userEvalationResultCustomizes.size()!=0){
            userEvalationResultCustomize = userEvalationResultCustomizes.get(0);
        }else{
            return;
        }
        UserEvalationExample userEvalationExample=new UserEvalationExample();
        userEvalationExample.createCriteria().andErIdEqualTo(userEvalationResultCustomize.getId());
        userEvalationMapper.deleteByExample(userEvalationExample);
        userEvalationResultCustomizeMapper.deleteByPrimaryKey(userEvalationResultCustomize.getId());
        
        
    }
    
    
    @Override
    public UserEvalationBehavior insertUserEvalationBehavior(Integer userId,String behavior) {
        
        UserEvalationBehavior userEvalationBehavior=new UserEvalationBehavior();
        userEvalationBehavior.setUserId(userId);
        userEvalationBehavior.setStatrTime(new Date());
        userEvalationBehavior.setBehavior(behavior);
        userEvalationBehaviorMapper.insertSelective(userEvalationBehavior);
        return userEvalationBehavior;
    }
    
    
    @Override
    public void updateUserEvalationBehavior(UserEvalationBehavior userEvalationBehavior) {
        
        UserEvalationBehavior behavior=userEvalationBehaviorMapper.selectByPrimaryKey(userEvalationBehavior.getId());
        userEvalationBehavior.setBehavior(behavior.getBehavior()+","+userEvalationBehavior.getBehavior());
        userEvalationBehaviorMapper.updateByPrimaryKeySelective(userEvalationBehavior);
    }

    @Override
    public List<QuestionCustomize> getNewQuestionList() {
        List<QuestionCustomize> customizes = questionCustomizeMapper.getNewQuestionList();
        return customizes;
    }

    @Override
    public void getUserEvalationResultByUserId(Integer userId, JSONObject ret) {
        JSONObject jsonObject = CommonSoaUtils.getUserEvalationResultByUserId(userId+"");
        UserEvalationResultCustomize userEvalationResultCustomize =null;
        String userEvalationResultJson = (String) jsonObject.get("userEvalationResultJson");
        if (userEvalationResultJson != null && userEvalationResultJson.length() > 0) {
            userEvalationResultCustomize =JSONObject.toJavaObject(JSONObject.parseObject(userEvalationResultJson), UserEvalationResultCustomize.class);
        }
        ret.put("userEvalationResult", userEvalationResultCustomize);
        ret.put("userEvaluationResultFlag", jsonObject.get("userEvaluationResultFlag"));
    }

	/**
	 * 获取评分标准列表
	 * @return
	 * @author Michael
	 */
	@Override
	public List<EvalationCustomize> getEvalationRecord() {
		EvalationExampleCustomize example = new EvalationExampleCustomize();
	    example.createCriteria().andStatusEqualTo(0);
	    // 获取评分标准对应的上限金额并拼接
        List<EvalationCustomize> eval = evalationCustomizeMapper.selectByExample(example);
        for(EvalationCustomize evalStr : eval){
            switch (evalStr.getType()){
                case "保守型":
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(
                            Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE)).intValue() == 0.0D ? 0 :
                                    Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE)).intValue()));
                    break;
                case "稳健型":
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(
                            Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS)).intValue() == 0.0D ? 0:
                                    Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS)).intValue()));
                    break;
                case "成长型":
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(
                            Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_GROWTH)).intValue() == 0.0D ? 0:
                                    Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_GROWTH)).intValue()));
                    break;
                case "进取型":
                    evalStr.setRevaluationMoney(StringUtil.getTenThousandOfANumber(
                            Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE)).intValue() == 0.0D ? 0:
                                    Double.valueOf(RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE)).intValue()));
                    break;
                default:
                    evalStr.setRevaluationMoney("0");
            }
        }
	    return eval;
	}
}
