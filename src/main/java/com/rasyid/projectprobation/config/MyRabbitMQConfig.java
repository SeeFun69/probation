package com.rasyid.projectprobation.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitMQConfig {

    //Inventory Exchange
    public static final String STORY_EXCHANGE = "STORY_EXCHANGE";

    //Order Exchange
    public static final String ORDER_EXCHANGE = "ORDER_EXCHANGE";

    //Story Queue
    public static final String STORY_QUEUE = "STORY_QUEUE";

    //Order Queue
    public static final String ORDER_QUEUE = "ORDER_QUEUE";

    //Story Routing Key
    public static final String STORY_ROUTING_KEY = "STORY_ROUTING_KEY";

    //Order Routing Key
    public static final String ORDER_ROUTING_KEY = "ORDER_ROUTING_KEY";
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //Create Story Exchange
    @Bean
    public Exchange getStoryExchange() {
        return ExchangeBuilder.directExchange(STORY_EXCHANGE).durable(true).build();
    }

    //Create Story Queue
    @Bean
    public Queue getStoryQueue() {
        return new Queue(STORY_QUEUE);
    }

    //Bind Story Exchange and Story Queue
    @Bean
    public Binding bindStory() {
        return BindingBuilder.bind(getStoryQueue()).to(getStoryExchange()).with(STORY_ROUTING_KEY).noargs();
    }

    //Create Order Queue
    @Bean
    public Queue getOrderQueue() {
        return new Queue(ORDER_QUEUE);
    }

    //Create Order Exchange
    @Bean
    public Exchange getOrderExchange() {
        return ExchangeBuilder.directExchange(ORDER_EXCHANGE).durable(true).build();
    }

    //Bind Order Queue to Order Exchange.
    @Bean
    public Binding bindOrder() {
        return BindingBuilder.bind(getOrderQueue()).to(getOrderExchange()).with(ORDER_ROUTING_KEY).noargs();
    }
}
