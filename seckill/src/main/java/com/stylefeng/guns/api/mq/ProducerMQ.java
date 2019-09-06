package com.stylefeng.guns.api.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.api.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.api.common.persistence.model.MtimeStockLog;
import com.stylefeng.guns.api.seckill.SecKillService;
import com.stylefeng.guns.core.constant.StockLogStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.HashMap;


@Slf4j
@Component
public class ProducerMQ {

    @Value("${mq.nameserver.address}")
    private String namesrvAddr;

    @Value("${mq.topic}")
    private String topic;

    @Resource
    private SecKillService secKillService;

    @Resource
    private MtimeStockLogMapper stockLogMapper;

    TransactionMQProducer transactionMQProducer;

    //加载类时初始化producer
    @PostConstruct
    public void init() throws MQClientException {
        transactionMQProducer = new TransactionMQProducer("promo_transaction_producer");
        transactionMQProducer.setNamesrvAddr(namesrvAddr);
        transactionMQProducer.start();

        transactionMQProducer.setTransactionListener(new TransactionListener() {
            //发送事务消息成功后MQ回调该方法，执行本地事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object args) {
                //从消息中取出参数
                HashMap hashMap = (HashMap) args;
                Integer promoId = (Integer) hashMap.get("promoId");
                Integer amount = (Integer)hashMap.get("amount");
                Integer userId = (Integer)hashMap.get("userId");
                String stockLogId = (String) hashMap.get("stockLogId");
                //调用订单入库和修改redis缓存接口
                try {
                    secKillService.savePromoOrder(promoId, amount, userId, stockLogId);
                } catch (Exception e) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            //检查本地事务执行状态
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                byte[] body = messageExt.getBody();
                String bodyStr = new String(body);
                HashMap hashMap = JSON.parseObject(bodyStr, HashMap.class);
                String stockLogId = (String)hashMap.get("stockLogId");

                MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
                Integer status = mtimeStockLog.getStatus();
                if (StockLogStatus.FAIL.getIndex() == status) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else if (StockLogStatus.SUCCESS.getIndex() == status) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }
        });
    }
    //生成订单：封装数据并发送MQ事务消息
    public Boolean transactionSaveOrder(Integer promoId, Integer userId, Integer amount, String stockLogId) {
        //设置Message参数(需要转换为bytes)
        HashMap hashMap = new HashMap();
        hashMap.put("promoId",promoId);
        hashMap.put("amount",amount);
        hashMap.put("userId",userId);
        hashMap.put("stockLogId",stockLogId);
        //topic：stock
        Message message = new Message("stock", JSON.toJSONString(hashMap).getBytes(Charset.forName("utf-8")));
        //设置args参数
        HashMap args = new HashMap();
        args.put("promoId",promoId);
        args.put("amount",amount);
        args.put("userId",userId);
        args.put("stockLogId",stockLogId);
        //发送MQ事务消息
        try {
            TransactionSendResult transactionSendResult = transactionMQProducer.sendMessageInTransaction(message, args);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
