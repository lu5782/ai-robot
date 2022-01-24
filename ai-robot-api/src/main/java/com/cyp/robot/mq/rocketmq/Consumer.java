package com.cyp.robot.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by luyijun on 2021/2/23 22:23.
 */
public class Consumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("PushTopic", "push");

            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            MessageListenerConcurrently messageListenerConcurrently = new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    list.forEach(msg -> {
                        System.out.println("msg.toString() = " + "");
                        System.out.println("topic = " + msg.getTopic());
                        System.out.println("body =  " + new String(msg.getBody()));
                        System.out.println("keys = " + msg.getKeys());
                        System.out.println("tags = " + msg.getTags());
                        System.out.println("-----------------------------------------------");
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            };
            consumer.registerMessageListener(messageListenerConcurrently);
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
