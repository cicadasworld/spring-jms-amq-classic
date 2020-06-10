package com.jin;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class RealTimeExample {

    public static void main(String[] args) {
        ConnectionFactory cf = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        try {
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("demo");

            JSONObject json = new JSONObject();
            json.put("from_date", "2019-1-01");
            json.put("to_date", "2019-12-31");
            json.put("email", "xyz@gmail.com");
            json.put("query", "select * from data");

            TextMessage textMessage = session.createTextMessage(json.toString());
            MessageProducer producer = session.createProducer(queue);
            producer.send(textMessage);

            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
