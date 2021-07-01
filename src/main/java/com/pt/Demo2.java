package com.pt;

import org.junit.Test;

import javax.jms.*;

/**
 * @author nate-pt
 * @date 2021/7/1 16:26
 * @Since 1.8
 * @Description 发布订阅模式
 */
public class Demo2 {

    public static final String TOPIC = "my-topic";


    /**
     * 生产者
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        Session session = ConnUtil.getNonSession();

        MessageProducer producer = session.createProducer(null);

        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 0; i < 5; i++) {
            TextMessage textMessage = session.createTextMessage("我是消息" + i);
            // 创建路由器
            Topic topic = session.createTopic(TOPIC);
            producer.send(topic, textMessage);
        }
    }

    /**
     * 消费者
     */
    @Test
    public void test2() throws Exception {
        Session session = ConnUtil.getNonSession();
        Topic topic = session.createTopic(TOPIC);
        MessageConsumer consumer = session.createConsumer(topic);

        while (true) {
            TextMessage receive = (TextMessage) consumer.receive();
            if (receive != null) {
                System.out.println("接受到的消息为：" + receive.getText());
            }else {
                break;
            }
        }
    }

}
