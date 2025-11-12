package com.example.trade.Config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
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

    // Topic for allocations
    @Bean
    public Topic tradeAllocationTopic() {
        return new ActiveMQTopic("TRADE_ALLOCATION_TOPIC");
    }

    // JMS Template for publishing messages
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true); // ✅ enable topic mode globally
        return jmsTemplate;
    }

    // Listener factory (for topics)
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true); // ✅ enable topic mode for listeners
        factory.setSessionTransacted(true);
        factory.setConcurrency("1-1");
        return factory;
    }
}
