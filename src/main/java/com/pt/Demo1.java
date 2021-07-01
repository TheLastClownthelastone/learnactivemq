package com.pt;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author nate-pt
 * @date 2021/7/1 15:47
 * @Since 1.8
 * @Description 点对点模式
 */
public class Demo1 {

    /**
     * 生产者
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        // 使用工厂创建链接
        Session session = ConnUtil.getSession();

        // 创建队列
        Destination  queue = session.createQueue("my-queue");

        // 设置生产者(制定队列名称)
        MessageProducer producer = session.createProducer(queue);
        // 设置消息不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // 发送消息
        for (int i = 0; i < 5; i++) {
            TextMessage textMessage = session.createTextMessage("Hello ActiveMq" + i);
            producer.send(textMessage);
        }
        session.commit();
        session.close();
    }

    /**
     * 消费者
     */
    @Test
    public void test2() throws Exception{
        // 使用工厂创建链接
        Session session = ConnUtil.getSession();

        // 创建队列
        Destination  queue = session.createQueue("my-queue");

        MessageConsumer consumer = session.createConsumer(queue);

        while (true){
            TextMessage receive = (TextMessage) consumer.receive();
            if (receive !=null) {
                System.out.println("收到消息为：" + receive.getText());
            }else {
                break;
            }
        }
        session.close();
    }


}
