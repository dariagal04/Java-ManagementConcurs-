// RabbitConsumer.java
package src.mpp2024.messaging;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitConsumer {
    private final static String QUEUE_NAME = "notifications";

    public static void startListening(NotificationHandler handler) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
                handler.onNotificationReceived(msg);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface NotificationHandler {
        void onNotificationReceived(String message);
    }
}
