package com.g4pas.index.rabbit;

import com.g4pas.index.service.ErrorService;
import com.g4pas.index.service.NullReturningTerminatingService;
import com.g4pas.index.service.TransformService;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.dsl.AmqpInboundChannelAdapterSMLCSpec;
import org.springframework.integration.amqp.dsl.SimpleMessageListenerContainerSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

//import org.springframework.integration.dsl.channel.MessageChannels;

@Configuration
@Profile("rabbitmq")
public class RabbitFlow {
    public static final String ERROR_CHANNEL = "exceptionChannel";
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitFlow.class);
    @Autowired
    Advice retryAdvice;
    @Autowired
    private RabbitConsumerConfig rabbitConsumerConfig;
    @Autowired
    private MessageChannel processChannel;
    @Autowired
    private ErrorService errorService;
    @Autowired
    private TransformService transformService;

    @Autowired
    private NullReturningTerminatingService terminatingService;

    @Bean
    public IntegrationFlow errorHandling() {
        return IntegrationFlows.from(customErrorChannel())
                               .handle(errorService)
                               .get();
    }

    @Bean
    public MessageChannel customErrorChannel() {
        return MessageChannels.direct(ERROR_CHANNEL)
                              .get();
    }


    @Bean
    public IntegrationFlow fromAmqp(ConnectionFactory connectionFactory, MessageChannel errorChannel) {
        final AmqpInboundChannelAdapterSMLCSpec messageProducerSpec = Amqp.inboundAdapter(connectionFactory,
                                                                                          rabbitConsumerConfig.getIndexQueueName());
        LOGGER.info("fromAmqp([connectionFactory]) : intiating Rabbit Flow frm queue '{}' ",
                    rabbitConsumerConfig.getIndexQueueName());
        messageProducerSpec.configureContainer(this::configureConsumer);

        return IntegrationFlows.from(messageProducerSpec)
                               .handle(transformService)
                               .gateway(processChannel,
                                        e -> e.advice(retryAdvice)
                                              .errorChannel(errorChannel))//Call Gateway flow and then retry if any exception
                               .handle(terminatingService)// Fix the integration flow issue to return to the queue and pick up another, otherwise it just waits
                               .get();
    }


    private void configureConsumer(SimpleMessageListenerContainerSpec c) {
        c.concurrentConsumers(rabbitConsumerConfig.getConcurrentConsumers());
        // Improve the thread name
        c.getObject()
         .setBeanName(rabbitConsumerConfig.getIndexQueueName() + "-consumer");
    }
}
