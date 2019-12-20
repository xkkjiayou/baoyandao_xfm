package com.xkk.util;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;


@Configuration
public class ActiveMQEmail {
    @Bean
    public Queue emailqueue() {
        return new ActiveMQQueue("bbs.email.queue");
    }
}
