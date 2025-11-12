package com.example.distribution.Config;


import jakarta.jms.ConnectionFactory;
import jakarta.jms.Topic;

import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;

@Configuration
public class JmsConfig {

    public static final String TRADE_ALLOCATION_TOPIC = "trade-allocation-topic";

    @Bean
    public Topic tradeAllocationTopic() {
        return new ActiveMQTopic(TRADE_ALLOCATION_TOPIC);
    }
//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        
//        // Important for topics
//        factory.setPubSubDomain(true);  // topic mode
//        
//        // Durable subscription settings
//        factory.setSubscriptionDurable(true);
//        factory.setClientId("distributionServiceClient");  // unique per service
//        
//        factory.setConnectionFactory(connectionFactory);
//        return factory;
//    }
    // Topic to listen to
    @Bean
    public Topic tradeRequestTopic() {
        return new ActiveMQTopic("TRADE_REQUEST_TOPIC");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616"); // same broker as producer
        return factory;
    }

    // Listener factory for durable subscription
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        // Single connection required for durable subscription
        SingleConnectionFactory listenerConnectionFactory = new SingleConnectionFactory();
        listenerConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
        listenerConnectionFactory.setClientId("distribution-client-" + UUID.randomUUID());
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(listenerConnectionFactory);
        factory.setPubSubDomain(true);        // topic mode
        factory.setSubscriptionDurable(true); // durable subscription
        factory.setConcurrency("1-1");        // one listener thread
        return factory;
    }
}
