package com.rabbitmqdemo;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange exchange;

    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    private AtomicInteger count = new AtomicInteger(0);

    private AtomicInteger index = new AtomicInteger(0);

    @Scheduled(fixedDelay = 25, initialDelay = 50)
    public void send() {

        StringBuilder stringBuilder = new StringBuilder("hello_" + count.getAndIncrement());

        if (index.incrementAndGet() == keys.length) {
            index.set(0);
        }

        String key = keys[index.get()];

        stringBuilder.append("_" + key);

        String message = stringBuilder.toString();
        template.convertAndSend(exchange.getName(), key, message);
        System.out.println("Sent " + message);

    }

}
