package com.example.trade.Config;

import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Topic;

@Configuration
public class JmsConfig {

    // Topic for trade requests
    @Bean
    public Topic tradeRequestTopic() {
        return new ActiveMQTopic("TRADE_REQUEST_TOPIC");
    }

    @Bean
    public Topic tradeAllocationTopic() {
        return new ActiveMQTopic("TRADE_ALLOCATION_TOPIC");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616"); // adjust broker URL
        return factory;
    }

    // JMS Template for publishing messages
    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true); // topic mode
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        SingleConnectionFactory listenerConnectionFactory = new SingleConnectionFactory();
        listenerConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
//        listenerConnectionFactory.setClientId("distribution-client"); // only for listener
        listenerConnectionFactory.setClientId("distribution-client-" + UUID.randomUUID());

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(listenerConnectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        factory.setConcurrency("1-1");
        return factory;
    }
}
