package com.g4pas.index.service;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;

import javax.annotation.PostConstruct;

/**
 * Builds the Integration Flow for the consumer
 **/

@Configuration
public class PublishFlowDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishFlowDefinition.class);

    public static final String PROCESS_CHANNEL = "processChannel";

    @Autowired
    private ApplicationConfig appConfig;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private InsertService insertService;

    @PostConstruct
    public void init() {
        LOGGER.warn("init([]) : Initialising complete(ish)...");
    }

    @Bean
    public MessageChannel processChannel() {
        return MessageChannels.direct(PROCESS_CHANNEL)
                              .get();
    }


    @Bean
    public IntegrationFlow processFlow() {

        return IntegrationFlows
                .from(processChannel())
                .handle(enrichmentService)
                .handle(insertService)
                .get();
    }


    /**
     * When the persistence to the Elastic Search service fails (primarily this is expected because of a Optimistic Lock exception)
     * The retry in 200ms, then again 2x that for 15 attempts capt at 10,000ms between attempts..
     * <p>
     * for example 200ms, 400ms, 800ms, 1600ms, 3200ms, 6400ms, 10000ms... for the rest
     *
     * @return
     */
    @Bean
    public Advice retryAdvice() {
        return RetryInterceptorBuilder.stateless()
                                      .backOffOptions(appConfig.getRetry()
                                                               .getInitialInterval(),
                                                      appConfig.getRetry()
                                                               .getMultiplier(),
                                                      appConfig.getRetry()
                                                               .getMaxInterval())
                                      .maxAttempts(appConfig.getRetry()
                                                            .getMaxAttempts())

                                      .build();


    }

}
