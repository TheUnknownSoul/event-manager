package com.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Getter
@Setter
public class RabbitMQConfig {

    /**
     * default settings for RabbitMQ broker
     */
    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String defaultQueueName;
    @Value("${spring.rabbitmq.template.exchange}")
    private String defaultExchange;
    @Value("${spring.rabbitmq.template.routing-key}")
    public String defaultRoutingKey;

    /**
     * bean for default settings queue, exchange and binding
     *
     * @return default queue
     */
    @Bean
    Queue queue() {
        return new Queue(defaultQueueName);
    }

    /**
     * bean for default exchange
     *
     * @return default exchange
     */
    @Bean
    @Scope("prototype")
    TopicExchange exchange() {
        return new TopicExchange(defaultExchange);
    }

    /**
     * bean for default binding
     *
     * @param queue         default queue from application properties
     * @param topicExchange default type of exchange in RabbitMQ broker
     * @return default exchange
     */
    @Bean
    Binding defaultBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(defaultRoutingKey);
    }

    /**
     * bean for default  message converter
     *
     * @return default message converter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * bean for spring amqp template
     *
     * @return spring amqp  template
     */
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
