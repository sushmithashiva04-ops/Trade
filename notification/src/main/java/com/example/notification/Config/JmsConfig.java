package com.example.notification.Config;



import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.Topic;
import org.apache.activemq.command.ActiveMQTopic;

@Configuration
public class JmsConfig {

    public static final String TRADE_ALLOCATION_TOPIC = "trade-allocation-topic";


    @Bean
    public Topic tradeAllocationTopic() {
        return new ActiveMQTopic(TRADE_ALLOCATION_TOPIC);
    }


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616"); // adjust if needed
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true); // use topic
        return jmsTemplate;
    }

    // JMS Listener Container Factory for durable topic subscriptions
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        SingleConnectionFactory listenerConnectionFactory = new SingleConnectionFactory();
        listenerConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
        listenerConnectionFactory.setClientId("notification-client-2"); // fixed ID

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(listenerConnectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        factory.setConcurrency("1-1");
        return factory;
    }

}
