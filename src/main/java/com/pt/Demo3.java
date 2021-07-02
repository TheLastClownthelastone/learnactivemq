package com.pt;

import org.junit.Test;

import javax.jms.*;

/**
 * @author nate-pt
 * @date 2021/7/2 9:10
 * @Since 1.8
 * @Description 监听器消费者监听对应的队列
 */
public class Demo3 {

    /**
     * 生产者
     */
    @Test
    public void test1() throws Exception {
        // 获取到session
        Session session = ConnUtil.getNonSession();

        // 通过session创建队列
        Queue queue = session.createQueue("test listener");

        // 创建生产者
        MessageProducer producer = session.createProducer(queue);

        // 生产消息
        for (int i = 0; i < 10; i++) {
            TextMessage textMessage = session.createTextMessage(String.valueOf(i));
            producer.send(textMessage);
        }

    }

    /**
     * 消费者
     */
    @Test
    public void test2() throws Exception {

        Session session = ConnUtil.getNonSession();

        // 通过session创建队列
        Queue queue = session.createQueue("test listener");

        // 通过session创建消费者
        MessageConsumer consumer = session.createConsumer(queue);

        // 给消费者设置队列
        consumer.setMessageListener(message -> {
            try {
                System.out.println("进入监听器。。。。，开始消息消费");
                // 先进行消息的确认，告诉mq服务器接受到了消息
                message.acknowledge();
                TextMessage message1 = (TextMessage) message;
                System.out.println(message1);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        // 进行代码的阻塞，不然代码执行完毕
        System.in.read();

    }

}
