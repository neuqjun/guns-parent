package com.stylefeng.guns.api.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.api.common.persistence.dao.MtimePromoStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class ConsumerMQ {

    @Value("${mq.nameserver.address}")
    private String namesrvAddr;

    @Value("${mq.topic}")
    private String topic;

    DefaultMQPushConsumer defaultMQPullConsumer;

    @Resource
    MtimePromoStockMapper stockMapper;

    @PostConstruct
    public void init() throws MQClientException {
        defaultMQPullConsumer = new DefaultMQPushConsumer("stock_consumer");
        defaultMQPullConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPullConsumer.subscribe("stock", "*");
        defaultMQPullConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = msgs.get(0);
                byte[] body = messageExt.getBody();
                HashMap hashMap = JSON.parseObject(new String(body), HashMap.class);
                Integer promoId = (Integer) hashMap.get("promoId");
                Integer amount = (Integer) hashMap.get("amount");
                Integer affectRows = stockMapper.decreaseStock(promoId, amount);
                if (affectRows > 0) {
                    log.info("消息消费成功！promoId:{}, amount:{}",promoId,amount);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }else {
                    log.info("消息消费失败！promoId:{}, amount:{}",promoId,amount);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        defaultMQPullConsumer.start();
    }
}
