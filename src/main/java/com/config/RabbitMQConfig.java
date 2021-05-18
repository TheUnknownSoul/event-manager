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
     * custom settings for app A
     */
    @Value("${spring.rabbitmq.template.routing-key.appA}")
    private String routingKeyAppA;
    @Value("${spring.rabbitmq.template.queue.appA}")
    private String queueAppA;

    /**
     * custom settings for app B
     */
    @Value("${spring.rabbitmq.template.routing-key.appB}")
    private String routingKeyAppB;

    @Value("${spring.rabbitmq.template.queue.appB}")
    private String queueAppB;

    /**
     * custom settings for app C
     */
    @Value("${spring.rabbitmq.template.routing-key.appC}")
    private String routingKeyAppC;

    @Value("${spring.rabbitmq.template.queue.appC}")
    private String queueAppC;

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

    /**
     * bean for queues for application A
     *
     * @return queue for app A
     */
    @Bean
    Queue appAQueue() {
        return new Queue(queueAppA);
    }

    /**
     * binds queue and exchange for application A
     *
     * @param appAQueue     queue for application A
     * @param topicExchange default type of exchange is Topic
     * @return bind queue and exchange
     */
    @Bean
    Binding bindingForFirstApp(Queue appAQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(appAQueue).to(topicExchange).with(routingKeyAppA);
    }

    /**
     * bean for queue for app B
     *
     * @return queue for app B
     */
    @Bean
    Queue appBQueue() {
        return new Queue(queueAppB);
    }

    /**
     * bean for binding queue of app B with exchange
     *
     * @param appBQueue     queue of application B
     * @param topicExchange type of exchange
     * @return bind queue and exchange
     */
    @Bean
    Binding bindingForSecondApp(Queue appBQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(appBQueue).to(topicExchange).with(routingKeyAppB);
    }

    /**
     * bean for queue for app C
     *
     * @return queue for app C
     */
    @Bean
    Queue appCQueue() {
        return new Queue(queueAppC);
    }

    /**
     * bean for binding queue and exchange app C
     *
     * @param appCQueue     queue of app C
     * @param topicExchange exchange of app C
     * @return binds queue with exchange
     */
    @Bean
    Binding bindingForThird(Queue appCQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(appCQueue).to(topicExchange).with(routingKeyAppC);
    }
}
