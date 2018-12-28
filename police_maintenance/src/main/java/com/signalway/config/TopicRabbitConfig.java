package com.signalway.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZhangGang on 2018/6/5.
 * MQ配置中心
 * 遵循springboot规则
 */
@Configuration
public class TopicRabbitConfig {
    private  static String GetOBCFile = RoutingKey.PoliceMaintenanceGetOBCFile;
    private  static String SendCommand = RoutingKey.PoliceMaintenanceSendCommand;
    private  static String SendFile = RoutingKey.DeviceMaintenanceSendFile;

    @Bean
    public Queue queueGetOBCProperty() {
        return new Queue(TopicRabbitConfig.GetOBCFile);
    }

    @Bean
    public Queue queueGetImage() {
        return new Queue(TopicRabbitConfig.SendCommand);
    }

    @Bean
    public Queue queueSendFile() {
        return new Queue(TopicRabbitConfig.SendFile);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");

    }

    @Bean
    Binding bindingQueueGetOBCProperty(Queue queueGetOBCProperty, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetOBCProperty).to(exchange).with(RoutingKey.PoliceMaintenanceGetOBCFile);
    }

    @Bean
    Binding bindingQueueGetImage(Queue queueGetImage, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetImage).to(exchange).with(RoutingKey.PoliceMaintenanceSendCommand);
    }

    @Bean
    Binding bindingQueueSendOrderOrExe(Queue queueSendFile, TopicExchange exchange) {
        return BindingBuilder.bind(queueSendFile).to(exchange).with(RoutingKey.DeviceMaintenanceSendFile);
    }
    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        ExecutorService service= Executors.newFixedThreadPool(600);
        factory.setTaskExecutor(service);
        factory.setConcurrentConsumers(20);
        factory.setPrefetchCount(50);
        configurer.configure(factory,connectionFactory);
        return factory;
    }
  /*  @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }*/

}
