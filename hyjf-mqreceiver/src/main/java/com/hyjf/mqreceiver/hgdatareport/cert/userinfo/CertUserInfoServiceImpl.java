package com.hyjf.mqreceiver.hgdatareport.cert.userinfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.mqreceiver.hgdatareport.cert.BaseHgCertReportServiceImpl;
import com.hyjf.mqreceiver.hgdatareport.common.CertCallConstant;
import com.hyjf.mqreceiver.hgdatareport.common.CertCallUtil;
import com.hyjf.mybatis.model.auto.*;
import com.hyjf.mybatis.model.customize.CertSendUser;
import com.hyjf.mybatis.model.customize.UserEvalationResultCustomize;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author sss
 */

@Service
public class CertUserInfoServiceImpl extends BaseHgCertReportServiceImpl implements CertUserInfoService {
    Logger logger = LoggerFactory.getLogger(CertUserInfoMessageHadnle.class);

    private String thisMessName = "用户数据推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    /**
     * 组装调用应急中心日志
     *
     * @param user
     * @param borrowNid
     * @return
     */
    @Override
    public JSONArray getSendData(CertSendUser user, String borrowNid,List<CertUser> users) throws Exception {
        JSONArray result = new JSONArray();
        Integer userId = user.getUserId();
        if(users!=null && users.size()>0){
            // users的长度大于0的话  证明是修改
            // 修改的
            for (CertUser certUser : users) {
                // 判断是否已经上送
                // 用户状态编码 1-新增／2-变更／3-失效 默认新增
                String userStatus = CertCallConstant.CERT_PARAM_USER_STATUS_UPD;
                if (certUser != null && certUser.getBorrowNid() != null) {
                    borrowNid = certUser.getBorrowNid();
                }
                Map<String, Object> param = getUserData(user,borrowNid,userStatus);
                param.remove("groupByDate");
                // 返回对象
                result.add(param);
            }
        }else{
            // 新增的
            // 用户状态编码 1-新增／2-变更／3-失效 默认新增
            String userStatus = CertCallConstant.CERT_PARAM_USER_STATUS_ADD;
            Map<String, Object> param = getUserData(user,borrowNid,userStatus);
            param.remove("groupByDate");
            // 组装上送用户表数据
            CertUser certUser = new CertUser();
            certUser.setBorrowNid(borrowNid);
            certUser.setUserId(userId);
            certUser.setUserIdCardHash((String)param.get("userIdcardHash"));
            certUser.setHashValue(user.getUserIdcardValue());
            users.add(certUser);

            CertUser hashUser = getCertUserByUserIdcardHash(certUser.getUserIdCardHash());
            // 如果已经上报过了  就上报修改
            if(hashUser!=null){
                // 用户状态编码  1-新增／2-变更／3-失效
                param.put("userStatus", CertCallConstant.CERT_PARAM_USER_STATUS_UPD);
            }

            result.add(param);
        }
        return result;
    }

    /**
     * 组装单个用户数据
     *
     * @param item
     * @return
     */
    @Override
    public Map<String, Object> getUserData(CertSendUser item,String borrowNid,String userStatus) throws Exception {
        Integer userId = item.getUserId();
        UsersInfo usersInfo = new UsersInfo();
        usersInfo.setRoleId(item.getUserAttr());
        usersInfo.setIdcard(item.getUserIdcard());
        usersInfo.setTruename(item.getTruename());

        Borrow borrow = null;
        if (borrowNid!=null &&!"".equals(borrowNid)) {
            // 借款人
            borrow = this.getBorrowByBorrowNid(borrowNid);
        }

        CorpOpenAccountRecord accountRecord = null;
        String userIdcard = "";
        String userLawperson = "-1";
        String userFund = "-1";
        String userProvince = "-1";
        String userAddress = "-1";
        String registerDate = "-1";
        String userAscription = "-1";
        String userAge = "-1";
        String userSex = "-1";
        String phoneAscription = "-1";
        // 出借人类型对应关系 保守型-A 稳健型-I 成长型-Q 进取型-Z
        String riskRating = "-1";
        String userPayAccount = CertCallUtil.convertUserPayAccount(item.getUserPayAccount());
        String userBank = "";
        String userBankAccount = "";
        String username = "";
        if (item != null) {
            userBank = item.getUserBank();
            userBankAccount = CertCallUtil.convertUserPayAccount(item.getUserBankAccount());
        }
        // 如果是企业用户
        if(item.getUserType().equals(2)){
            accountRecord = getCorpOpenAccountRecord(userId);
        }
        username = CertCallUtil.convertUserName(item.getUserType(), usersInfo == null ? "" : usersInfo.getTruename(), accountRecord == null ? "" : accountRecord.getBusiName());
        if (usersInfo.getRoleId().equals(1) && borrow==null) {
            // 投资人
            userIdcard = usersInfo.getIdcard();
            // 用户所属地
            userAscription = tool.getIdcardAscription(userIdcard);
            userAge = tool.getAge(userIdcard);
            userSex = tool.getSex(userIdcard);
            phoneAscription = tool.getPhoneAscription(item.getMobile());
            riskRating = CertCallUtil.convertRiskRating(item.getEvalType());
        }
        // 初始化为个人
        item.setUserType(0);
        if (borrow != null) {
            if ("1".equals(borrow.getCompanyOrPersonal())) {
                // 公司
                BorrowUsers borrowUsers = getBorrowUsers(borrow.getBorrowNid());
                // 统一社会信用代码
                userIdcard = borrowUsers.getSocialCreditCode();
                if (borrowUsers.getSocialCreditCode() == null || "".equals(borrowUsers.getSocialCreditCode())) {
                    // 注册号
                    userIdcard = borrowUsers.getRegistCode();
                }
                // 法人代表
                userLawperson = borrowUsers.getLegalPerson();
                userFund = CertCallUtil.convertUserFund(borrowUsers.getRegCaptial(), borrowUsers.getCurrencyName());
                try{
                    userProvince = tool.getCompanyAscription(userIdcard);
                }catch (Exception e){
                    userProvince = "-1";
                }
                userAddress = borrowUsers.getRegistrationAddress();
                if(userAddress==null || "".equals(userAddress)){
                    userAddress = borrowUsers.getCity();
                }
                // 非必填  旧数据怎么处理
                registerDate = borrowUsers.getComRegTime();
                username = borrowUsers.getUsername();
                // 设置为企业用户
                item.setUserType(1);
            } else {
                // 个人
                BorrowManinfo borrowManinfo = getBorrowManInfo(borrow.getBorrowNid());
                // 身份证号
                userIdcard = borrowManinfo.getCardNo();
                // 用户所属地
                userAscription = tool.getIdcardAscription(userIdcard);
                userAge = tool.getAge(userIdcard);
                userSex = tool.getSex(userIdcard);
                phoneAscription = tool.getPhoneAscription(item.getMobile());
                username = CertCallUtil.desUserName(borrowManinfo.getName());
                // 设置为个人用户
                item.setUserType(0);
            }
        }
        // 插入数据库用
        item.setUserIdcardValue(userIdcard);
        String userIdcardHash = "";
        try {
            userIdcardHash = tool.idCardHash(userIdcard);
        } catch (Exception e) {
            logger.error(logHeader + " 用户标示哈希出错！", e);
        }

        Map<String, Object> param = new LinkedHashMap<String, Object>();
        // 接口版本号
        param.put("version", CertCallConstant.CERT_CALL_VERSION);
        // 用户注册时间
        param.put("userCreateTime", GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(item.getRegTime()));
        // 平台编号
        param.put("sourceCode", CertCallConstant.CERT_SOURCE_CODE);
        // 用户状态编码  1-新增／2-变更／3-失效
        param.put("userStatus", userStatus);
        // 用户类型  1-自然人／2-企业
        param.put("userType", CertCallUtil.convertUserType(item.getUserType()));
        // 用户属性  1-投资／2-借贷／3-投资＋借贷  垫付机构不报送
        // 如果是借款  通通通传借款人
        if(borrow!=null){
            param.put("userAttr", "2");
        }else {
            param.put("userAttr", usersInfo.getRoleId()+"");
        }
        // 用户姓名/名称  投资人／借款人姓名／企业名称 说明：允许脱敏处理，姓氏不允许脱敏，名字可脱敏。企业名称不用脱敏处理
        param.put("userName", username);
        // 国别  用户类型（userType）=1必填 用户类型（userType）=2，表示企业国别 1-中国大陆；2-中国港澳台；3-国外 说明：无法区分国别的企业，缺省-1
        // 均报送1 如果企业类别为香港 风控单独标出
        param.put("countries", "1");
        // 证件类型  用户类型（userType）=1必填 用户类型（userType）=2填写-1 1-身份证；2-护照；3-军官证；4-台湾居民来往大陆通行证；5-港澳居民来往内地通行证；6-外国人永久居留身份证；0-其它
        param.put("cardType", CertCallUtil.convertCardType(item.getUserType()));
        // 用户标识编码  身份证号(个人) 工商注册号(企业) 三证合一号(企业)
        //说明：允许脱敏处理,但只能脱敏后4位
        // 个人报送身份证号加密 企业报送统一社会信用代码加密值  无社会信用代码 报送注册号加密值    借款人报送借款主体的证件编码
        param.put("userIdcard", CertCallUtil.desUserIdcard(userIdcard));
        // 用户标示哈希
        // 身份证号／工商注册号／三证合一号hash值（64位随机数字和字母）
        //使用工具包中idCardHash方法生成的hash值 此字段是散标接口、交易流水接口、还款计划、债权信息、转让项目、承接项目的关联字段
        param.put("userIdcardHash", userIdcardHash);

        // 手机号 说明：允许脱敏处理，只允许后4位脱敏
        param.put("userPhone", CertCallUtil.desUserPhone(item.getMobile()));
        // 手机号哈希 使用工具包中phoneHash方法生成的hash值（64位随机数字和字母）
        JSONObject phoneHash = null;
        if(item.getPhoneHash()!=null&&(item.getCertMobile()!=null && item.getCertMobile().equals(item.getMobile()))){
            // 直接取就行了
            param.put("userPhoneHash", item.getPhoneHash());
            // 用户手机盐值  使用工具包中phoneHash方法生成的salt值
            param.put("userUuid", item.getHashSalt());
        }else {
            // 重新加密
            try {
                phoneHash = CertCallUtil.phoneHash(item.getMobile());
            } catch (Exception e) {
                logger.error(logHeader + " 用户手机号哈希出错！", e);
            }
            param.put("userPhoneHash", phoneHash.getString("phone"));
            // 用户手机盐值  使用工具包中phoneHash方法生成的salt值
            param.put("userUuid", phoneHash.getString("salt"));
        }

        // 法人代表 企业属性 用户类型（userType）=2必填 用户类型（userType）=1填写-1
        param.put("userLawperson", userLawperson);
        // 注册资金(万元)  企业属性,单位：万元 用户类型（userType）=2必填 用户类型（userType）=1填写-1
        // 取注册资金字段值，注册资金为人民币的转换成万元，注册资金单位为美元的按照汇率6.9换算后转换成万元 小数点后保留4位
        param.put("userFund", userFund);
        // 注册省份 企业属性  用户类型（userType）=2必填 用户类型（userType）=1填写-1,描述：用户归属地的行政区号。
        // 报送注册地区字段值
        // 生成方法：【SDK工具列表】中【getCompanyAscription】方法取得
        param.put("userProvince", userProvince);
        // 注册地址 企业属性 用户类型（userType）=2必填 用户类型（userType）=1填写-1
        // 报送企业注册地字段值
        param.put("userAddress", userAddress);
        // 企业注册时间
        param.put("registerDate", registerDate);
        // 注册人邮箱 userMail  不报送
        //param.put("userMail", "-");
        // 用户所属地 自然人属性用户类型（userType）=1必填 使用SDK工具包中getIdcardAscription方法生成的行政区号，6位数字。如：110112表示北京东城区。用户类型（userType）=2填写-1
        param.put("userAscription", userAscription);
        // 用户年龄  自然人属性 用户类型（userType）=1必填 使用工具包中getAge方法生成的值。用户类型（userType）=2填写-1
        param.put("userAge", userAge);
        // 用户性别 自然人属性 用户类型（userType）=1必填 使用工具包中getSex方法生成的值，1：男；0：女。 用户类型（userType）=2,填写-1
        param.put("userSex", userSex);
        // 手机归属地 自然人属性 用户类型（userType）=1必填 使用工具包中getPhoneAscription方法生成6位的行政区号，如：110112表示北京东城区。 用户类型（userType）=2填写-1
        param.put("phoneAscription", phoneAscription);
        // 风险评级 用户属于投资人则必填 用于描述投资人(出借人)的风险承受能力。风险评级采用字符串的方式提交，使用大写字母A～Z来表示由低到高的风险评级。如果企业本身用数字或者其他方式表示风险评级，请转换成用字母表示的风险评级。如果机构没有评级，填写-1。
        // .借款人报送-1 出借人类型对应关系 保守型-A 稳健型-I 成长型-Q 进取型-Z
        param.put("riskRating", riskRating);

        List<Map<String, String>> tlist = new ArrayList<Map<String, String>>();
        Map<String, String> tmp1 = new LinkedHashMap<String, String>();
        if(StringUtils.isNotEmpty(userBank)&&StringUtils.isNotEmpty(userBankAccount)&&
            StringUtils.isNotEmpty(userPayAccount) ){
            //  用户的第三方支付平台名称／用户的存管银行名称
            tmp1.put("userPay", "江西银行");
            // 用户的第三方支付账号／用户的存管银行账号
            tmp1.put("userPayAccount", userPayAccount);
            tmp1.put("userBank", userBank);
            tmp1.put("userBankAccount", userBankAccount);
            tlist.add(tmp1);
        }
        param.put("userList", tlist);

        // groupByDate  旧数据上报排序 按月用
        param.put("groupByDate", item.getGroupByDate());
        if (borrow != null) {
            // groupByDate  旧数据上报排序 按月用yyyy-MM
            String groupByDate = "";
            try {
                if (borrow.getVerifyStatus().equals(3)) {
                    // 定时标
                    groupByDate = GetDate.times10toStrYYYYMM(borrow.getOntime()+"");
                } else {
                    groupByDate = GetDate.times10toStrYYYYMM(borrow.getVerifyTime());
                }
                if(null==groupByDate||"".equals(groupByDate)||"0".equals(groupByDate)){
                    groupByDate = GetDate.times10toStrYYYYMM(borrow.getAddtime());
                }

            }catch (Exception e){
                logger.error(logHeader+"日期格式化错误");
                groupByDate = GetDate.times10toStrYYYYMM(borrow.getAddtime());
            }
            param.put("groupByDate", groupByDate);
        }
        return param;
    }



    /**
     * 根据userId查询需要上报的用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public CertSendUser getCertSendUserByUserId(int userId) {
        return usersCustomizeMapper.getCertSendUserByUserId(userId);
    }

    /**
     * 插入国家互联网应急中心已上送用户表
     *
     * @param certUser
     */
    @Override
    public void insertCertUser(CertUser certUser) {
        certUserMapper.insertSelective(certUser);
    }

    /**
     * 修改国家互联网应急中心已上送用户表
     *
     * @param certUser
     */
    @Override
    public void updateCertUser(CertUser certUser) {
        certUserMapper.updateByPrimaryKeySelective(certUser);
    }

    /**
     * 批量插入上报记录
     *
     * @param certUsers
     */
    @Override
    public void insertCertUserByList(List<CertUser> certUsers) {
        usersCustomizeMapper.insertCertUserByList(certUsers);
    }

    /**
     * 根据borrowNid userId查询
     *
     * @param userId
     * @param borrowNid
     * @return
     */
    @Override
    public CertUser getCertUserByUserIdBorrowNid(int userId, String borrowNid) {
        CertUserExample example = new CertUserExample();
        CertUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andBorrowNidEqualTo(borrowNid);
        List<CertUser> list = certUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }




}
