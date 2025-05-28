// RabbitPublisher.java
package src.mpp2024.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitPublisher {
    private final static String QUEUE_NAME = "notifications";

    public static void sendNotification(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // sau IP RabbitMQ server

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
