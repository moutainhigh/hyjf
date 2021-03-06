/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.mqreceiver.sensorsdata.register;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.bank.service.sensorsdata.SensorsDataBean;
import com.hyjf.bank.service.sensorsdata.register.SensorsDataRegisterService;
import com.hyjf.common.util.StringUtil;
import com.hyjf.mqreceiver.sensorsdata.recharge.SensorsDataRechargeMessageHandle;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 神策数据采集:注册相关
 *
 * @author liuyang
 * @version SensorsDataRegisterMessageHandle, v0.1 2018/7/16 16:36
 */
public class SensorsDataRegisterMessageHandle implements ChannelAwareMessageListener {


    Logger _log = LoggerFactory.getLogger(SensorsDataRegisterMessageHandle.class);

    @Autowired
    private SensorsDataRegisterService sensorsDataRegisterService;


    /**
     * 消息监听
     *
     * @param message
     * @param channel
     * @throws Exception
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        _log.info("神策数据采集:注册相关");
        if (message == null || message.getBody() == null) {
            _log.error("神策数据采集:注册相关接收到的消息为null");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        try {
            String msgBody = new String(message.getBody());
            SensorsDataBean sensorsDataBean;
            try {
                sensorsDataBean = JSONObject.parseObject(msgBody, SensorsDataBean.class);
            } catch (Exception e1) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                e1.printStackTrace();
                return;
            }

            // 预置属性
            Map<String, Object> presetProps = sensorsDataBean.getPresetProps();
            _log.info(presetProps.toString());
            // 匿名ID
            String distinctId = "";
            if (presetProps.get("_distinct_id") != null) {
                distinctId = (String) presetProps.get("_distinct_id");
            }
            //  设备ID
            String deviceId = "";
            if (presetProps.get("$device_id") != null) {
                deviceId = (String) presetProps.get("$device_id");
            }
            if (StringUtils.isBlank(distinctId) && StringUtils.isBlank(deviceId)) {
                _log.error("神策数据采集:注册相关,匿名ID,设备ID为空");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }

            // 用户ID
            Integer userId = sensorsDataBean.getUserId();
            if (userId == null || userId == 0) {
                _log.error("神策数据采集:注册相关,获取用户ID为空");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            // 神策数据埋点处理
            this.sensorsDataRegisterService.sendSensorsData(sensorsDataBean);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
