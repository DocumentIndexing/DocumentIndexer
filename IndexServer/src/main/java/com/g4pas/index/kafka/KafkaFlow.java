package com.g4pas.index.kafka;

import com.g4pas.index.service.ErrorService;
import com.g4pas.index.service.NullReturningTerminatingService;
import com.g4pas.index.service.TransformService;
import org.aopalliance.aop.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.dsl.KafkaMessageDrivenChannelAdapterSpec;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.messaging.MessageChannel;

//import org.springframework.integration.dsl.channel.MessageChannels;

@Configuration
@Profile("kafka")
public class KafkaFlow {

    public static final String ERROR_CHANNEL = "exceptionChannel";

    @Autowired
    private KafkaConsumerConfig kafkaListenerConfig;

    @Autowired
    private KafkaConfig kafkaConfig;

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
    public IntegrationFlow fromKafka(AbstractMessageListenerContainer container,
                                     Advice retryAdvice,
                                     MessageChannel processChannel) {
        KafkaMessageDrivenChannelAdapterSpec messageConsumer = Kafka.messageDrivenChannelAdapter(container);
        messageConsumer.ackDiscarded(true);
        messageConsumer.errorChannel(customErrorChannel());

        return IntegrationFlows
                .from(messageConsumer)
                .handle(transformService)
                .gateway(processChannel,
                         e -> e.advice(retryAdvice))//Call Gateway flow and then retry if any exception
                .handle(terminatingService)// Fix the integration flow issue to return to the queue and pick up another, otherwise it just waits
                .get();
    }


    @Bean
    public DefaultKafkaConsumerFactory<?, ?> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<Integer, String>(this.kafkaListenerConfig.getConfig());
    }

    @Bean
    public ConcurrentMessageListenerContainer<?, ?> concurrentKafkaMessageListenerContainer(
            DefaultKafkaConsumerFactory factory) {
        ContainerProperties props = new ContainerProperties(kafkaConfig.getTopic());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(factory,
                                                                                              props);
        container.setConcurrency(kafkaConfig.getConsumers());
        return container;
    }
}
