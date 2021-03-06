package com.hyjf.admin.manager.activity.namimarketing;

import com.hyjf.admin.BaseServiceImpl;
import com.hyjf.common.util.ActivityDateUtil;
import com.hyjf.mybatis.mapper.auto.ActivityListMapper;
import com.hyjf.mybatis.mapper.auto.PerformanceReturnDetailMapper;
import com.hyjf.mybatis.mapper.customize.NaMiMarketing.NaMiMarketingCustomizeMapper;
import com.hyjf.mybatis.model.auto.ActivityList;
import com.hyjf.mybatis.model.auto.PerformanceReturnDetail;
import com.hyjf.mybatis.model.auto.Users;
import com.hyjf.mybatis.model.customize.NaMiMarketing.NaMiMarketingCustomize;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2018/11/8.
 */
@Service
public class NaMiMarketingServiceImpl extends BaseServiceImpl implements NaMiMarketingService {

    @Autowired
    public NaMiMarketingCustomizeMapper naMiMarketingCustomizeMapper;
    @Autowired
    public ActivityListMapper activityListMapper;
    @Autowired
    public PerformanceReturnDetailMapper performanceReturnDetailMapper;

    /**
     * 查询活动是否开始
     * @param activityId
     * @return
     */
    public ActivityList checkActivityIfAvailable(String activityId) {
        if (activityId == null) {
            return null;
        }
        return activityListMapper.selectByPrimaryKey(new Integer(activityId));
    }
    /**
     * 查询邀请明细条数
     * @param paraMap
     * @return
     */
    @Override
    public List<Integer> selectNaMiMarketingCount(Map<String, Object> paraMap){
        //获取活动时间
        ActivityList activityList = checkActivityIfAvailable(ActivityDateUtil.RETURNCASH_ACTIVITY_ID);
        if(activityList==null){
            return null;
        }
        //查询活动期间的合伙人
        List<Users> id0 =naMiMarketingCustomizeMapper.selectId0List(activityList);
        if(CollectionUtils.isEmpty(id0)){
            return null;
        }
        paraMap.put("id0",id0);
        paraMap.put("activityList",activityList);
        //查询符合条件的用户id
        List<Integer> ids =new ArrayList<Integer>();
        String refferName= (String)paraMap.get("refferName");
        if(StringUtils.isNotEmpty(refferName)){
            //根据邀请人账户名，查询邀请人id
            int refferId = naMiMarketingCustomizeMapper.selectRefferIdByName(refferName);
             paraMap.put("refferId",refferId);
        }
        //查询活动期间的不是合伙人的好友
        return naMiMarketingCustomizeMapper.selectIdList(paraMap);
    }

    /**
     * 查询邀请明细列表
     * @param paraMap
     * @return
     */
    @Override
    public List<NaMiMarketingCustomize> selectNaMiMarketingList(Map<String, Object> paraMap){
        return naMiMarketingCustomizeMapper.selectNaMiMarketingList(paraMap);
    }

    /**
     * 查询业绩返现详情条数
     * @param paraMap
     * @return
     */
    @Override
    public int selectNaMiMarketingPerfanceCount(Map<String, Object> paraMap){
        return naMiMarketingCustomizeMapper.selectNaMiMarketingPerfanceCount(paraMap);
    }

    /**
     * 查询业绩返现详情列表
     * @param paraMap
     * @return
     */
    @Override
    public List<NaMiMarketingCustomize> selectNaMiMarketingPerfanceList(Map<String, Object> paraMap){
        return naMiMarketingCustomizeMapper.selectNaMiMarketingPerfanceList(paraMap);
    }

    /**
     * 查询业绩返现详情
     * @param form
     * @return
     */
    @Override
    public List<PerformanceReturnDetail> selectNaMiMarketingPerfanceInfo(NaMiMarketingBean form){
        List<PerformanceReturnDetail> returnDetails= new ArrayList<>();
        ActivityList activityList = checkActivityIfAvailable(ActivityDateUtil.RETURNCASH_ACTIVITY_ID);
        if(activityList==null){
            return null;
        }

        //查询所有合伙人
        List<Users> users =naMiMarketingCustomizeMapper.selectId0List(activityList);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        List<String> userName=new ArrayList<>();
        for(Users u:users){
            userName.add(u.getUsername());
        }
        //查询当前业绩
        PerformanceReturnDetail detail = performanceReturnDetailMapper.selectByPrimaryKey(form.getId());
        return selectReturnDetailList(returnDetails,detail, userName);
    }


    /**
     * 迭代查询 所有朋友
     */
    public  List<PerformanceReturnDetail> selectReturnDetailList(List<PerformanceReturnDetail> returnDetails,PerformanceReturnDetail detail,List<String> users){
        //判断是否是合伙人
        if(users.contains(detail.getUserName())){
            returnDetails.add(detail);
            return returnDetails;
        }
        returnDetails.add(detail);
        //根据推荐人，查询上级好友
        PerformanceReturnDetail details=naMiMarketingCustomizeMapper.selectReturnDetail(detail.getRefferName());
        if(details == null){
            return returnDetails;
        }
        return selectReturnDetailList(returnDetails, details,users);
    }
}
