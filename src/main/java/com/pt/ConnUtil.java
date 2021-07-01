package com.pt;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

/**
 * @author nate-pt
 * @date 2021/7/1 16:09
 * @Since 1.8
 * @Description
 */
public class ConnUtil {

    public static Connection getConn() throws Exception{
        // 使用工厂创建链接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        return connection;
    }

    public static Session getSession() throws Exception{
        Connection conn = getConn();
        Session session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    public static Session getNonSession() throws Exception{
        Connection conn = getConn();
        Session session = conn.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        return session;
    }
}
