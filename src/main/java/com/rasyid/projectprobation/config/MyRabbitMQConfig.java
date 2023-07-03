package com.rasyid.projectprobation.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitMQConfig {

    //Inventory Exchange
    public static final String STOCK_EXCHANGE = "STOCK_EXCHANGE";

    //Order Exchange
    public static final String ORDER_EXCHANGE = "ORDER_EXCHANGE";

    //Stock Queue
    public static final String STOCK_QUEUE = "STOCK_QUEUE";

    //Order Queue
    public static final String ORDER_QUEUE = "ORDER_QUEUE";

    //Stock Routing Key
    public static final String STOCK_ROUTING_KEY = "STOCK_ROUTING_KEY";

    //Order Routing Key
    public static final String ORDER_ROUTING_KEY = "ORDER_ROUTING_KEY";
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //Create Stock Exchange
    @Bean
    public Exchange getStockExchange() {
        return ExchangeBuilder.directExchange(STOCK_EXCHANGE).durable(true).build();
    }

    //Create Stock Queue
    @Bean
    public Queue getStockQueue() {
        return new Queue(STOCK_QUEUE);
    }

    //Bind Stock Exchange and Stock Queue
    @Bean
    public Binding bindStock() {
        return BindingBuilder.bind(getStockQueue()).to(getStockExchange()).with(STOCK_ROUTING_KEY).noargs();
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
